package com.great.bench;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SpringMailSender {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public void sendMail(String[] to, String subject, String bodyTemplate, String[] data) {

        String fromEmail = "coedry@gmail.com";

        String[] toEmail = to;

        String emailSubject = subject;

        String emailBody = String.format(bodyTemplate, data);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(emailSubject);
            helper.setText(emailBody, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {

            e.printStackTrace();

        }

    }

}