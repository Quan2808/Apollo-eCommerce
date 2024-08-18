package com.apollo.converter;

import com.apollo.dto.PaymentMethodDto;
import com.apollo.dto.ShippingMethodDto;
import com.apollo.entity.PaymentMethod;
import com.apollo.entity.ShippingMethod;
import com.apollo.payload.request.PaymentMethodRequest;
import com.apollo.payload.response.PaymentMethodResponse;


public interface PaymentMethodConverter {
    PaymentMethodResponse convertToDto(PaymentMethod paymentMethod);
    PaymentMethod convertToEntity(PaymentMethodRequest paymentMethodRequest);
    PaymentMethodDto entityToDTO(PaymentMethod element);
    PaymentMethod dtoToEntity(PaymentMethodDto element);
}
