package com.great.bench;

import com.mongodb.WriteResult;
import com.stripe.Stripe;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StaffController {

    @Autowired
    SpringMailSender mail;

    @Autowired
    private MongoTemplate mango;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest req, HttpServletResponse resp) {
        return "index";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ModelAndView account(HttpServletRequest req, HttpServletResponse resp, @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {

        Map<String, Object> response = new HashMap<String, Object>();

        if (sessionToken.length() < 3) {
            return new ModelAndView("login", response);
        }

        Member user = mango.findOne(new Query(Criteria.where("sessionToken").is(sessionToken)), Member.class);

        if (user == null || !sessionToken.equals(user.sessionToken)) {
            return new ModelAndView("login", response);
        }

        Customer cu = Customer.retrieve(user.stripeId);
        Card card = cu.getCards().retrieve(cu.getDefaultCard());

        response.put("email", user.email);
        response.put("cardLastFour", "(" + card.getType() + ") xx-" + card.getLast4() + " " + (card.getExpMonth().toString().length() <= 1 ? "0" + card.getExpMonth() : card.getExpMonth()) + "/" + card.getExpYear());

        return new ModelAndView("account", response);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String stripeToken = req.getParameter("stripeToken");

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);

        boolean isEmailUnique = !(mango.exists(new Query(Criteria.where("email").is(email)), "user"));

        if (isEmailUnique) {

            customerParams.put("card", stripeToken);
            customerParams.put("description", "Customer for the basic plan.");

            Customer newMember = Customer.create(customerParams);

            if (newMember.getEmail().equalsIgnoreCase(email)) {

                Map<String, Object> subscriptionParams = new HashMap<String, Object>();
                subscriptionParams.put("plan", "basic");

                newMember.createSubscription(subscriptionParams);

                String sessionToken = Member.randomSessionToken();

                mango.insert(new Member(email, password, newMember.getId(), "stripe", sessionToken, null));

                Cookie cookie = new Cookie("sessionToken", sessionToken);
                resp.addCookie(cookie);
            }

            customerParams.put("status", "success");

            return "redirect:account";

        } else {

            customerParams.put("status", "error");

            return "index";

        }
    }

    @RequestMapping(value = "/api/deactivate", method = RequestMethod.POST)
    public ModelAndView deactivate(HttpServletRequest req, HttpServletResponse resp, @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email");

        Member user = mango.findOne(new Query(Criteria.where("email").is(email)), Member.class);

        if (user == null || !sessionToken.equals(user.sessionToken)) {
            return new ModelAndView("login", response);
        }

        Subscription subscription = Customer.retrieve(user.stripeId).cancelSubscription();

        response.put("status", subscription.getStatus() == "canceled" ? "success" : "error");

        return new ModelAndView("account", response);
    }

    @RequestMapping(value = "/api/reactivate", method = RequestMethod.POST)
    public ModelAndView reactivate(HttpServletRequest req, HttpServletResponse resp, @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {

        String email = req.getParameter("email");

        Member member = mango.findOne(new Query(Criteria.where("email").is(email)), Member.class);

        Map<String, Object> subscriptionParams = new HashMap<String, Object>();
        subscriptionParams.put("plan", "basic");

        Subscription subscription = Customer.retrieve(member.stripeId).createSubscription(subscriptionParams);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", subscription.getStatus() == "active" ? "success" : "error");

        return new ModelAndView("account", response);

    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String loginClient(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Member user = mango.findOne(new Query(Criteria.where("email").is(email).and("password").is(password)), Member.class);

        if (user != null) {

            Cookie cookie = new Cookie("sessionToken", user.sessionToken);
            resp.addCookie(cookie);

            return "SessionToken: " + user.sessionToken;

        } else {

            return "error";

        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginWeb(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email"),
               password = req.getParameter("password");

        Member user = mango.findOne(new Query(Criteria.where("email").is(email).and("password").is(password)), Member.class);

        if (user == null) {

            return new ModelAndView("login", null);

        } else {

            user.sessionToken = Member.randomSessionToken();
            mango.save(user);

            Customer cu = Customer.retrieve(user.stripeId);
            Card card = cu.getCards().retrieve(cu.getDefaultCard());

            Cookie cookie = new Cookie("sessionToken", user.sessionToken);
            resp.addCookie(cookie);

            response.put("email", email);
            response.put("cardLastFour", "(" + card.getType() + ") xx-" + card.getLast4() + " " + (card.getExpMonth().toString().length() <= 1 ? "0" + card.getExpMonth() : card.getExpMonth()) + "/" + card.getExpYear());

            return new ModelAndView("redirect:account", response);

        }
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email");

        mango.updateFirst(new Query(Criteria.where("email").is(email)),
                Update.update("sessionToken", null), Member.class);

        Cookie cookie = new Cookie("sessionToken", null);
        resp.addCookie(cookie);

        return new ModelAndView("account", null);

    }

    @RequestMapping(value = "/api/forgot", method = RequestMethod.POST)
    public ModelAndView sendForgotPasswordEmail(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email");

        WriteResult mangoResult = mango.updateFirst(new Query(Criteria.where("email").is(email)),
                Update.update("resetCode", Member.randomResetCode()), Member.class);

        mail.sendMail(new String[]{email},
                "Welcome to the Banch",
                "Dear %s,<br><br>Confirm your membership. <a href=\"http://www.neopets.com/?email=kanvus@gmail.com&code=TREre53\">LOOK!</a><br><br>Thanks,<br/>%s",
                new String[]{"dahling", "Polite Stare"});

        Map<String, Object> response = new HashMap<String, Object>();

        response.put("status", "success");

        return new ModelAndView("account", response);

    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ModelAndView changePassword(HttpServletRequest req, HttpServletResponse resp, @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {

        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        Member member = mango.findOne(new Query(Criteria.where("email").is(email).and("sessionToken").is(sessionToken)), Member.class);

        try {

            if (member != null && (newPassword == confirmPassword)) {
                member.password = confirmPassword;
                mango.save(member);

                Customer cu = Customer.retrieve(member.stripeId);
                Card card = cu.getCards().retrieve(cu.getDefaultCard());

                Cookie cookie = new Cookie("sessionToken", sessionToken);
                resp.addCookie(cookie);

                response.put("email", email);
                response.put("cardLastFour", "(" + card.getType() + ") xx-" + card.getLast4() + " " + (card.getExpMonth().toString().length() <= 1 ? "0" + card.getExpMonth() : card.getExpMonth()) + "/" + card.getExpYear());
            }

        } catch (Exception e) {

            response.put("status", "error");
            e.printStackTrace();

        }

        return new ModelAndView("account", response);

    }

    @RequestMapping(value = "/changecard", method = RequestMethod.POST)
    public ModelAndView changeCard(HttpServletRequest req,HttpServletResponse resp,
                                   @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {

        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email");
        String stripeToken = req.getParameter("stripeToken");

        Member member = mango.findOne(new Query(Criteria.where("email").is(email).and("sessionToken").is(sessionToken)), Member.class);

        try {

            member.sessionToken = Member.randomSessionToken();
            mango.save(member);

            Customer cu = Customer.retrieve(member.stripeId);

            cu.setDefaultCard(stripeToken);

            Card card = cu.getCards().retrieve(cu.getDefaultCard());

            response.put("email", email);
            response.put("cardLastFour", "(" + card.getType() + ") xx-" + card.getLast4() + " " + (card.getExpMonth().toString().length() <= 1 ? "0" + card.getExpMonth() : card.getExpMonth()) + "/" + card.getExpYear());

        } catch (Exception e) {

            response.put("status", "error");
            e.printStackTrace();

        }

        return new ModelAndView("account", response);

    }

    static {

        Stripe.apiKey = "sk_live_SIo727c3WJOAsTsqM2byQJGM";

        /* Making sure a 'basic' plan exists on Stripe if not already. */
        try {

            Plan.retrieve("basic");

        } catch (Exception e) {

            Map<String, Object> planParams = new HashMap<String, Object>();
            planParams.put("amount", 420);
            planParams.put("interval", "month");
            planParams.put("name", "Basic");
            planParams.put("currency", "usd");
            planParams.put("id", "basic");

            try {

                Plan.create(planParams);

            } catch (Exception ignored) {

                ignored.printStackTrace();

            }

        }
    }

}