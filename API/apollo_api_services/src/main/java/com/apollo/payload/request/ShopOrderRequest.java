package com.apollo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrderRequest {
    Long userId;
    Long variantId;
    String orderDate;
    Long addressId;
    Long paymentMethodId;
    Long shippingMethodId;
    int quantity;
    Double orderTotal;
}
