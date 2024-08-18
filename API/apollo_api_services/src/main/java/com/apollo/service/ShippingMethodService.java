package com.apollo.service;

import com.apollo.payload.response.ShippingMethodResponse;

import java.util.List;

public interface ShippingMethodService {
    List<ShippingMethodResponse> findShippingMethod();
}
