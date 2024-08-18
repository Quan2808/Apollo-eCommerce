package com.apollo.service;

import com.apollo.dto.*;
import com.apollo.entity.OrderDelivery;
import com.apollo.entity.Shipper;
import com.apollo.entity.ShopOrder;

import java.util.List;

public interface OrderDeliveryService {
    OrderDeliveryDTO saveOrderDelivery(ShopOrder shopOrder, Shipper shipper);
    OrderDelivery changeStatus(Long orderId, String newStatus, String inducement);
    List<OrderDeliveryDTO> findAllOrderDelivery();
    OrderDeliveryDTO findOrderDeliveryById(Long orderId);
}
