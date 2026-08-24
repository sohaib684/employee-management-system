package com.sohaib.employeemanagement.service;

public interface EmailService {

    void sendWelcomeEmail(
            String to,
            String employeeName
    );
}