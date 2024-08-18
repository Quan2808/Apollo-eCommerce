package com.apollo.converter;

import com.apollo.dto.CategoryDTO;
import com.apollo.dto.ShopOrderDto;
import com.apollo.entity.Category;
import com.apollo.entity.ShopOrder;
import com.apollo.payload.request.ShopOrderRequest;
import com.apollo.payload.response.ShopOrderResponse;

import java.util.List;

public interface ShopOrderConverter {
    List<ShopOrderDto> entitiesToDTOs(List<ShopOrder> element);
    ShopOrderDto entityToDTO(ShopOrder element);
    ShopOrderResponse convertToDto(ShopOrder shopOrder);
    ShopOrder convertToEntity(ShopOrderRequest shopOrderRequest);
}
