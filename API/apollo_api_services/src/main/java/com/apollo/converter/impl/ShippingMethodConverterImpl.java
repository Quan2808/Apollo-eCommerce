package com.apollo.converter.impl;

import com.apollo.converter.ShippingMethodConverter;
import com.apollo.dto.ShippingMethodDto;
import com.apollo.dto.StoreDTO;
import com.apollo.dto.UserDTO;
import com.apollo.entity.ShippingMethod;
import com.apollo.entity.Store;
import com.apollo.entity.User;
import com.apollo.payload.response.ShippingMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class ShippingMethodConverterImpl implements ShippingMethodConverter {
    @Override
    public ShippingMethodResponse convertToDto(ShippingMethod shippingMethod) {
        return ShippingMethodResponse
                .builder()
                .id(shippingMethod.getId())
                .name(shippingMethod.getName())
                .price(shippingMethod.getPrice())
                .build();
    }


    @Override
    public ShippingMethodDto entityToDTO(ShippingMethod element) {
        ShippingMethodDto result = new ShippingMethodDto();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public ShippingMethod dtoToEntity(ShippingMethodDto element) {
        ShippingMethod result = new ShippingMethod();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
