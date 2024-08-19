package com.apollo.service.impl;

import com.apollo.entity.Shipper;
import com.apollo.entity.ShopOrder;
import com.apollo.entity.User;
import com.apollo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
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

    @Override
    public void sendDeliveryConfirmationEmail(User user, ShopOrder shopOrder) {
        String recipientAddress = user.getEmail();
        String subject = "Order Delivery Confirmation";

        // Giả sử bạn đã lưu thông tin variant.name và deliveryDate trong ShopOrder hoặc một đối tượng liên quan
        String variantName = shopOrder.getVariant() != null && shopOrder.getVariant().getName() != null
                && !shopOrder.getVariant().getName().isEmpty()
                ? shopOrder.getVariant().getName() : "Unknown Variant";

        String deliveryDate = shopOrder.getDeliveryDate() != null
                ? new SimpleDateFormat("dd/MM/yyyy").format(shopOrder.getDeliveryDate())
                : "Not Available";

        String message = "<html><body style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>"
                + "<div style='max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>"
                + "<h2 style='color: #007bff;'>Hello " + user.getClientName() + ",</h2>"
                + "<p>Your order has been successfully delivered.</p>"
                + "<p><strong>Delivery Date:</strong> " + deliveryDate + "</p>"
                + "<p><strong>Variant Name:</strong> " + variantName + "</p>"
                + "<p>Thank you for shopping with us!</p>"
                + "<p style='font-size: 0.9em; color: #666;'>Home page: <a href='http://localhost:3000' style='color: #007bff; text-decoration: none;'>Apollo Home</a></p>"
                + "</div>"
                + "</body></html>";

        sendEmailDeli(recipientAddress, subject, message);
    }

    private void sendEmailDeli(String recipientAddress, String subject, String message) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(message, true); // Set to true to indicate HTML
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
