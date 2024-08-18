package com.apollo.dto;

import com.apollo.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopOrderDto {
    private Long id;
    private String status;
    private String orderDate;
    private Date deliveryDate;
    private int quantity;
    private Double orderTotal;
    private User user;
    private Variant variant;
    private Address address;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
}
