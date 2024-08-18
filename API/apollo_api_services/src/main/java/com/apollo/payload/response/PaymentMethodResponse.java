package com.apollo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodResponse {

    private Long id;

    private int cartNumber;

    private String nameOnCard;

    private String expirationDate;

    private Boolean defaultPayment;
}
