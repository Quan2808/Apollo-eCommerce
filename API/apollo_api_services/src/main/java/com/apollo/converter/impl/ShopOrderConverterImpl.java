package com.apollo.converter.impl;

import com.apollo.converter.AddressConverter;
import com.apollo.converter.PaymentMethodConverter;
import com.apollo.converter.ShippingMethodConverter;
import com.apollo.converter.ShopOrderConverter;
import com.apollo.dto.CategoryDTO;
import com.apollo.dto.ShopOrderDto;
import com.apollo.entity.Category;
import com.apollo.entity.ShopOrder;
import com.apollo.payload.request.ShopOrderRequest;
import com.apollo.payload.response.ShopOrderResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ShopOrderConverterImpl implements ShopOrderConverter {
    @Autowired
    private AddressConverter addressConverter;

    @Autowired
    private PaymentMethodConverter paymentMethodConverter;

    @Autowired
    private ShippingMethodConverter shippingMethodConverter;

    @Override
    public List<ShopOrderDto> entitiesToDTOs(List<ShopOrder> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShopOrderDto entityToDTO(ShopOrder element) {
        ShopOrderDto result = new ShopOrderDto();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public ShopOrderResponse convertToDto(ShopOrder shopOrder) {
        return ShopOrderResponse
                .builder()
                .address(addressConverter.convertToDto(shopOrder.getAddress()))
                .paymentMethod(paymentMethodConverter.convertToDto(shopOrder.getPaymentMethod()))
                .shippingMethod(shippingMethodConverter.convertToDto(shopOrder.getShippingMethod()))
                .build();
    }

    @Override
    public ShopOrder convertToEntity(ShopOrderRequest shopOrderRequest) {
        ShopOrder shopOrder = new ShopOrder();
        BeanUtils.copyProperties(shopOrderRequest,shopOrder);
        return shopOrder;
    }
}
