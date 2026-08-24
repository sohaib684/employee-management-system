package com.sohaib.employeemanagement.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendWelcomeEmail(String to, String employeeName) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Welcome to Flipkart team");
        message.setText(
                "Hello " + employeeName + ",\n\n" +
                        "Welcome to the Flipkart Team as Software Engineer Role!\n\n" +
                        "We are delighted to have you join us from 20th September 2026\n\n" +
                        "Your employee account has been created successfully in the Employee Management System.\n\n" +
                        "We look forward to having you as part of our team and wish you great success in your journey with us.\n\n" +
                        "Regards,\n" +
                        "HR Team\n" +
                        "Flipkart"
        );

        mailSender.send(message);
    }
}