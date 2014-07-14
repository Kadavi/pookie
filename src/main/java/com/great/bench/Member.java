package com.great.bench;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user")
public class Member {

    final static String LOTTO_POOL = "ABCD0abcd1EFGH2efgh3IJKL4ijkl5MNOP6mnop7QRST8qrst9UVWX0uvwxYZyz";

    final static StringBuilder bob = new StringBuilder();

    public Member(String email, String password, String stripeId, String paymentVendor, String sessionToken, String resetCode) {

        this.createdDate = new Date();
        this.email = email;
        this.password = password;
        this.stripeId = stripeId;
        this.paymentVendor = paymentVendor; // "stripe" or "paypal"
        this.sessionToken = sessionToken;
        this.resetCode = resetCode;

    }

    @Id
    String _id;

    Date createdDate;

    String stripeId;

    String name;

    String email;

    String password;

    String paymentVendor;

    String sessionToken;

    String resetCode;

    public static String randomSessionToken() {

        bob.setLength(0);

        int length = 6;
        while (length-- != 0) {

            int character = (int)(Math.random() * LOTTO_POOL.length());
            bob.append(LOTTO_POOL.charAt(character));

        }

        return bob.toString();

    }

    public static String randomResetCode() {

        bob.setLength(0);

        int length = 12;
        while (length-- != 0) {

            int character = (int)(Math.random() * LOTTO_POOL.length());
            bob.append(LOTTO_POOL.charAt(character));

        }

        return bob.toString();

    }

}
