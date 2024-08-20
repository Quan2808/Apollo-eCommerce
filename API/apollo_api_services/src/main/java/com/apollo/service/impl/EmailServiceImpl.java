package com.apollo.service.impl;

import com.apollo.entity.*;
import com.apollo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;


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
        // Get the latest order from the user
        ShopOrder latestOrder = user.getShopOrders().stream()
                .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate())) // Sort by order date, newest first
                .findFirst()
                .orElse(null);

        if (latestOrder == null) {
            throw new IllegalArgumentException("No orders found for the user.");
        }

        // Format current date
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatCurrentDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatCurrentDate);

        // Create PDF in memory
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add content to PDF
        document.add(new Paragraph("Order Details"));
        document.add(new Paragraph("Order Date: " + formattedDate));
        document.add(new Paragraph("\n"));

        Table table = new Table(2);
        table.addCell("Order ID");
        table.addCell(String.valueOf(latestOrder.getId()));
        table.addCell("Product");
        table.addCell(latestOrder.getVariant().getName());
        table.addCell("Quantity");
        table.addCell(String.valueOf(latestOrder.getQuantity()));
        table.addCell("Price per Unit ($)");
        table.addCell(String.valueOf(latestOrder.getVariant().getPrice()));
        table.addCell("Delivery Date");
        table.addCell(String.valueOf(latestOrder.getDeliveryDate()));
        table.addCell("Status");
        table.addCell(latestOrder.getStatus());
        table.addCell("Shipping Address");
        table.addCell(formatAddress(latestOrder.getAddress()));
        table.addCell("Payment Method");
        table.addCell(formatPaymentMethod(latestOrder.getPaymentMethod()));
        table.addCell("Total Amount ($)");
        table.addCell(String.valueOf(latestOrder.getOrderTotal()));

        document.add(table);

        // Close the document
        document.close();

        // Prepare email content
        String to = user.getEmail();
        String subject = "Payment has been successful";
        String text = "Hello " + user.getClientName() + ",\n\n" +
                "Your order has been placed successfully on " + formattedDate + ".\n\n" +
                "Please find attached your invoice.\n\n" +
                "We wish you have a great experience shopping at Apollo!\n\n" +
                "Home page: http://localhost:3000";

        // Send email with attachment
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("noreply@apollo.com");
                messageHelper.setTo(to);
                messageHelper.setSubject(subject);
                messageHelper.setText(text);

                // Attach the PDF
                InputStreamSource attachment = new ByteArrayResource(byteArrayOutputStream.toByteArray());
                messageHelper.addAttachment("Invoice.pdf", attachment);
            }
        };
        emailSender.send(preparator);
    }
    private String formatAddress(Address address) {
        return address.getStreet() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getCity();
    }
    private String formatPaymentMethod(PaymentMethod paymentMethod) {
        return "Card ending in " + paymentMethod.getCartNumber();
    }

    private String formatOrderDetails(ShopOrder order) {
        return "Order ID: " + order.getId() + "\n" +
                "Product: " + order.getVariant().getName() + "\n" +
                "Quantity: " + order.getQuantity() + "\n" +
                "Price per Unit ($): " + order.getVariant().getPrice() + "\n" +
                "Delivery Date: " + order.getDeliveryDate() + "\n" +
                "Status: " + order.getStatus() + "\n" +
//                "Shipping Address: " + formatAddress(order.getAddress()) + "\n" +
                "Shipping Address: " + order.getAddress().getStreet() + "\n" +
                "Payment Method: " + formatPaymentMethod(order.getPaymentMethod());
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
