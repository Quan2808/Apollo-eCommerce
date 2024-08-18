package com.apollo.service;

import com.apollo.payload.request.AddressRequest;
import com.apollo.payload.response.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> findAddress(Long userId);
    AddressResponse createAddress(AddressRequest addressRequest);
    AddressResponse updateAddress(AddressRequest addressRequest);
}
