package com.great.bench;

import com.ning.http.client.*;
import com.stripe.Stripe;
import com.stripe.model.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/config-servlet.xml")
public class AppTests {

    @Test
    public void registerCustomer() throws Exception {

        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", "4242424242424242");
        cardParams.put("exp_month", 3);
        cardParams.put("exp_year", 2015);
        cardParams.put("cvc", "315");
        tokenParams.put("card", cardParams);

        String email = randomEmail();
        String password = "secrets";
        String card = Token.create(tokenParams).getId();

        mockMvc.perform(post("/api/register")
                .param("card", card)
                .param("email", email)
                .param("password", password))
                .andExpect(model().attribute("email", email))
                .andExpect(model().attribute("status", "success"));

    }

    @Test
    public void toggleActiveStatus() throws Exception {

        String email = "NpDh0F@email.com";

        mockMvc.perform(post("/api/deactivate")
                .param("email", email))
                .andExpect(model().attribute("status", "success"));

        mockMvc.perform(post("/api/reactivate")
                .param("email", email))
                .andExpect(model().attribute("status", "success"));

    }

    @Test()
    public void login() throws Exception {

        String email = "NpDh0F@email.com";
        String password = "secrets";

        mockMvc.perform(post("/api/login")
                .param("email", email)
                .param("password", password))
                .andExpect(model().attribute("status", "success"));

        mockMvc.perform(post("/api/login")
                .param("email", email)
                .param("password", "wrongpw"))
                .andExpect(model().attribute("status", "error"));

    }

    @Test()
    public void logout() throws Exception {

        String email = "NpDh0F@email.com";

        mockMvc.perform(post("/api/logout")
                .param("email", email))
                .andExpect(model().attribute("status", "success"));

    }

    @Test()
    public void sendForgotPassword() throws Exception {

        String email = "B5VGVk@email.com";

        mockMvc.perform(post("/api/forgot")
                .param("email", email))
                .andExpect(model().attribute("status", "success"));

    }

    @Test()
    public void changePassword() throws Exception {

        String email = "B5VGVk@email.com";
        String code = "TYXfwN5oBcug";
        String newPassword = "secretsyze";

        mockMvc.perform(post("/api/change")
                .param("email", email)
                .param("code", code)
                .param("newPassword", newPassword))
                .andExpect(model().attributeExists("sessionToken"))
                .andExpect(model().attribute("status", "success"));

    }

    @Test
    public void getProfile() throws Exception {

        String email = "NpDh0F@email.com";

        mockMvc.perform(post("/api/profile")
                .param("email", email))
                .andExpect(model().attribute("status", "success"));

    }

    @Test()
    public void editProfile() throws Exception {

        String email = "NpDh0F@email.com";

        mockMvc.perform(post("/api/edit")
                .param("email", email))
                .andExpect(model().attribute("status", "success"));

    }

    @Test()
    public void uploadImage() {

        Response result = null;

        File file = new File("psyduck.jpg");

        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

        AsyncHttpClient client = new AsyncHttpClient(new AsyncHttpClientConfig.Builder().build());
        AsyncHttpClient.BoundRequestBuilder builder = client.preparePost("http://localhost:8080/");

        builder.setHeader("Content-Type", "multipart/form-data");

        builder.addBodyPart(new FilePart("image", file, fileTypeMap.getContentType(file), "UTF-8"));

        try {

            result = builder.execute().get();

            System.out.println(result.getResponseBody());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Before
    public void setup() {

        System.load("C:/Users/Grant Dawson/Documents/opencv/build/java/x64/opencv_java246.dll");

        this.mockMvc = webAppContextSetup(this.wac).build();

        Stripe.apiKey = "sk_test_cAefVlVMmXfcSKMZOKLhielX";

    }

    // Generates random email string for MongoDB to store fake accounts for testing
    public String randomEmail() {

        final String LOTTO_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder bob = new StringBuilder();

        int length = 6;
        while (length-- != 0) {

            int character = (int)(Math.random() * LOTTO_POOL.length());
            bob.append(LOTTO_POOL.charAt(character));

        }

        return bob.toString() + "@email.com";

    }

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

}
