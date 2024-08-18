package com.apollo.service;

import com.apollo.entity.Shipper;
import com.apollo.entity.User;

public interface EmailService {
    void sendConfirmEmailToUser(User user, String token);
    void sendConfirmEmailToShipper(Shipper shipper, String token);
    void sendPaymentEmail(User user);
}
