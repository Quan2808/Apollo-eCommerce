package com.apollo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodRequest {
    Long id;
    Long userId;
    int cartNumber;
    String nameOnCard;
    String expirationDate;
    Boolean defaultPayment;
}
