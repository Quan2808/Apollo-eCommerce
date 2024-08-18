package com.apollo.service;

import com.apollo.payload.request.PaymentMethodRequest;
import com.apollo.payload.response.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodResponse> findPaymentMethod(Long userId);
    PaymentMethodResponse createPaymentMethod(PaymentMethodRequest paymentMethodRequest);
    PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest paymentMethodRequest);
}
