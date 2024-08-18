package com.apollo.converter.impl;

import com.apollo.converter.PaymentMethodConverter;
import com.apollo.dto.PaymentMethodDto;
import com.apollo.dto.ShippingMethodDto;
import com.apollo.entity.PaymentMethod;
import com.apollo.entity.ShippingMethod;
import com.apollo.payload.request.PaymentMethodRequest;
import com.apollo.payload.response.PaymentMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class PaymentMethodConverterImpl implements PaymentMethodConverter {
    @Override
    public PaymentMethodResponse convertToDto(PaymentMethod paymentMethod) {
        return PaymentMethodResponse
                .builder()
                .id(paymentMethod.getId())
                .cartNumber(paymentMethod.getCartNumber())
                .nameOnCard(paymentMethod.getNameOnCard())
                .expirationDate(paymentMethod.getExpirationDate())
                .defaultPayment(paymentMethod.getDefaultPayment())
                .build();
    }

    @Override
    public PaymentMethod convertToEntity(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = new PaymentMethod();
        BeanUtils.copyProperties(paymentMethodRequest,paymentMethod);
        return paymentMethod;
    }

    @Override
    public PaymentMethodDto entityToDTO(PaymentMethod element) {
        PaymentMethodDto result = new PaymentMethodDto();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public PaymentMethod dtoToEntity(PaymentMethodDto element) {
        PaymentMethod result = new PaymentMethod();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
