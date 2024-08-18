package com.apollo.service.impl;

import com.apollo.entity.Shipper;
import com.apollo.entity.User;
import com.apollo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendConfirmEmailToUser(User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String message = "Thank you for registering to Apollo.\n"
                + "Please click on this link to verify your account and start shopping:\n"
                + "http://localhost:9999/api/register/confirmation?token=" + token;
        sendEmail(recipientAddress, subject, message);
    }

    @Override
    public void sendConfirmEmailToShipper(Shipper shipper, String token) {
        String recipientAddress = shipper.getEmail();
        String subject = "Shipper Registration Confirmation";
        String message = "Thank you for working with Apollo.\n"
                + "Please click on this link to verify your account and start earning a lot of money with us:\n"
                + "http://localhost:9999/api/register/confirmation?token=" + token;
        sendEmail(recipientAddress, subject, message);
    }

    // Helper method
    private void sendEmail(String recipientAddress, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        emailSender.send(email);
    }

    @Override
    public void sendPaymentEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatCurrentDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatCurrentDate);
        String to  = user.getEmail();
        String subject = "Payment has been successful";
        String text = "Hello " + user.getClientName() + "\n" + "\n" +
                "Your order has been placed successfully at " + formattedDate + "\n" +
                "Wishing you always have great experiences when shopping at Apollo" + "\n" + "\n" +
                "Home page: http://localhost:3000";
        message.setFrom("noreply@apollo.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
