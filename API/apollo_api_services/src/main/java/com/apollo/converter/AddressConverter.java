package com.apollo.converter;

import com.apollo.dto.AddressDto;
import com.apollo.dto.ShippingMethodDto;
import com.apollo.entity.Address;
import com.apollo.entity.ShippingMethod;
import com.apollo.payload.request.AddressRequest;
import com.apollo.payload.response.AddressResponse;

public interface AddressConverter {
    AddressResponse convertToDto(Address address);
    Address convertToEntity(AddressRequest addressRequest);
    AddressDto entityToDTO(Address element);
    Address dtoToEntity(AddressDto element);
}
