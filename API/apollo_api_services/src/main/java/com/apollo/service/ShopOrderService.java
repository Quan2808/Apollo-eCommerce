package com.apollo.service;

import com.apollo.dto.ShopOrderDto;
import com.apollo.dto.order_sumary.OrderSummaryDTO;
import com.apollo.entity.ShopOrder;
import com.apollo.payload.request.ShopOrderRequest;
import com.apollo.payload.response.ShopOrderResponse;

import java.util.List;

public interface ShopOrderService {
    List<OrderSummaryDTO> getShopOrders(Long userId);
    List<ShopOrderDto> getAllShopOrders();
    ShopOrderDto getShopOrder(Long shopOrderId);
    List<ShopOrderResponse> createShopOrder(List<ShopOrderRequest> shopOrderRequest);
    ShopOrderResponse acceptOrder(Long id);

}
