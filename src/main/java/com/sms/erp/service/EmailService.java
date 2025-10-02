package com.sms.erp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // Send Simple Text Email
    public void sendSimpleTextEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false);

        javaMailSender.send(message);
    }

    // Send Email with Attachments
    public void sendEmailWithAttachment(String to, String subject, String htmlBody,
                                        Map<String, InputStreamSource> attachments) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // true = multipart

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        // Add attachments
        if (attachments != null) {
            for (Map.Entry<String, InputStreamSource> entry : attachments.entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
        }

        javaMailSender.send(message);
    }
}
