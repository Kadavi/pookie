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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
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

        Customer customer = Customer.retrieve(user.stripeId);

        try {
            Card card = customer.getCards().retrieve(customer.getDefaultCard());
            response.put("cardLastFour", "(" + card.getType() + ") xx-" + card.getLast4() +
                    " " + (card.getExpMonth().toString().length() <= 1 ? "0" +
                    card.getExpMonth() : card.getExpMonth()) + "/" + card.getExpYear());
        } catch (Exception e) {
            response.put("cardLastFour", "Empty");
        }

        response.put("email", user.email);
        response.put("serviceStatus", customer.getSubscriptions().getCount().equals(0) ? "Deactivated" : "Active");

        return new ModelAndView("account", response);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String stripeToken = req.getParameter("stripeToken");

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("card", stripeToken);
        customerParams.put("description", "Customer for the basic plan.");

        boolean isEmailUnique = !(mango.exists(new Query(Criteria.where("email").is(email)), "user"));

        if (isEmailUnique) {
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

            return "redirect:account";
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ModelAndView status(HttpServletRequest req, HttpServletResponse resp,
                               @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email");
        String cardLastFour = req.getParameter("cardLastFour");
        String serviceStatus = req.getParameter("serviceStatus");

        Member user = mango.findOne(new Query(Criteria.where("email").is(email)
                .and("sessionToken").is(sessionToken)), Member.class);

        if (user == null || !sessionToken.equals(user.sessionToken)) {
            return new ModelAndView("login", null);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        Subscription subscription = null;
        Customer customer = Customer.retrieve(user.stripeId);

        Map<String, Object> subscriptionParams = new HashMap<String, Object>();
        subscriptionParams.put("plan", "basic");
        subscriptionParams.put("trial_end", cal.getTimeInMillis()/1000L);

        if (serviceStatus.equals("Active")) {
            subscription = customer.cancelSubscription();
        } else if (serviceStatus.equals("Deactivated") && customer.getSubscriptions().getCount().equals(0)) {
            subscription = customer.createSubscription(subscriptionParams);
        }

        response.put("email", email);
        response.put("cardLastFour", cardLastFour);
        response.put("message", subscription.getStatus().equals("canceled") ? "Your account has been deactivated." : "Your account is now active.");
        response.put("serviceStatus", subscription.getStatus().equals("canceled") ? "Deactivated" : "Active");

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
    public String loginWeb(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email"),
               password = req.getParameter("password");

        Member user = mango.findOne(new Query(Criteria.where("email").is(email).and("password").is(password)), Member.class);

        if (user == null) {
            return "redirect:login";
        } else {
            user.sessionToken = Member.randomSessionToken();
            mango.save(user);

            Cookie cookie = new Cookie("sessionToken", user.sessionToken);
            resp.addCookie(cookie);

            return "redirect:account";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String email = req.getParameter("email");

        mango.updateFirst(new Query(Criteria.where("email").is(email)),
                Update.update("sessionToken", null), Member.class);

        Cookie cookie = new Cookie("sessionToken", null);
        resp.addCookie(cookie);

        return "redirect:account";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
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
    public ModelAndView changePassword(HttpServletRequest req, HttpServletResponse resp,
                                       @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email");
        String cardLastFour = req.getParameter("cardLastFour");
        String serviceStatus = req.getParameter("serviceStatus");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        Member member = mango.findOne(new Query(Criteria.where("email").is(email).and("sessionToken").is(sessionToken).and("password").is(oldPassword)), Member.class);

        if (member != null && newPassword.equals(confirmPassword)) {
            member.password = confirmPassword;
            mango.save(member);

            response.put("message", "Your password has been changed.");
        } else {
            response.put("message", "Password change failed. Please try again.");
        }

        response.put("email", email);
        response.put("cardLastFour", cardLastFour);
        response.put("serviceStatus", serviceStatus);

        return new ModelAndView("account", response);
    }

    @RequestMapping(value = "/changecard", method = RequestMethod.POST)
    public ModelAndView changeCard(HttpServletRequest req,HttpServletResponse resp,
                                   @CookieValue(value = "sessionToken", defaultValue = "0") String sessionToken) throws Exception {

        Map<String, Object> response = new HashMap<String, Object>();

        String email = req.getParameter("email");
        String serviceStatus = req.getParameter("serviceStatus");
        String stripeToken = req.getParameter("stripeToken");

        Member member = mango.findOne(new Query(Criteria.where("email").is(email).and("sessionToken").is(sessionToken)), Member.class);

        Customer customer = Customer.retrieve(member.stripeId);
        customer.setDefaultCard(stripeToken);

        Card card = customer.getCards().retrieve(customer.getDefaultCard());
        response.put("cardLastFour", "(" + card.getType() + ") xx-" + card.getLast4() +
                " " + (card.getExpMonth().toString().length() <= 1 ? "0" +
                card.getExpMonth() : card.getExpMonth()) + "/" + card.getExpYear());

        response.put("email", email);
        response.put("serviceStatus", serviceStatus);

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