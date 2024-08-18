package com.apollo.converter;

import com.apollo.dto.ShippingMethodDto;
import com.apollo.dto.StoreDTO;
import com.apollo.dto.UserDTO;
import com.apollo.dto.VariantDTO;
import com.apollo.entity.ShippingMethod;
import com.apollo.entity.Store;
import com.apollo.entity.User;
import com.apollo.entity.Variant;
import com.apollo.payload.response.ShippingMethodResponse;

import java.util.List;

public interface ShippingMethodConverter {
    ShippingMethodResponse convertToDto(ShippingMethod shippingMethod);
    ShippingMethodDto entityToDTO(ShippingMethod element);
    ShippingMethod dtoToEntity(ShippingMethodDto element);
}
