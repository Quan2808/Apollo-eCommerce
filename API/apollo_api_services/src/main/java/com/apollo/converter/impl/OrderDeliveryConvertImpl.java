package com.apollo.converter.impl;

import com.apollo.converter.OrderDeliveryConverter;
import com.apollo.dto.OrderDeliveryDTO;
import com.apollo.dto.ShopOrderDto;
import com.apollo.entity.OrderDelivery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class OrderDeliveryConvertImpl implements OrderDeliveryConverter {
    @Override
    public List<OrderDeliveryDTO> entitiesToDTOs(List<OrderDelivery> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDeliveryDTO entityToDTO(OrderDelivery element) {
        OrderDeliveryDTO result = new OrderDeliveryDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
