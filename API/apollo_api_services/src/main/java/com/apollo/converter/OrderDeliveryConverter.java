package com.apollo.converter;

import com.apollo.dto.OrderDeliveryDTO;
import com.apollo.entity.OrderDelivery;

import java.util.List;

public interface OrderDeliveryConverter {
    List<OrderDeliveryDTO> entitiesToDTOs(List<OrderDelivery> element);
    OrderDeliveryDTO entityToDTO(OrderDelivery element);
}
