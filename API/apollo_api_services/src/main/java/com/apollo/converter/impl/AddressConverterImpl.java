package com.apollo.converter.impl;

import com.apollo.converter.AddressConverter;
import com.apollo.dto.AddressDto;
import com.apollo.dto.ShippingMethodDto;
import com.apollo.entity.Address;
import com.apollo.entity.ShippingMethod;
import com.apollo.payload.request.AddressRequest;
import com.apollo.payload.response.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class AddressConverterImpl implements AddressConverter {
    @Override
    public AddressResponse convertToDto(Address addressed) {
        return AddressResponse
                .builder()
                .id(addressed.getId())
                .district(addressed.getDistrict())
                .ward(addressed.getWard())
                .city(addressed.getCity())
                .street(addressed.getStreet())
                .build();
    }

    @Override
    public Address convertToEntity(AddressRequest addressRequest) {
        Address address = new Address();
        BeanUtils.copyProperties(addressRequest,address);
        return address;
    }

    @Override
    public AddressDto entityToDTO(Address element) {
        AddressDto result = new AddressDto();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public Address dtoToEntity(AddressDto element) {
        Address result = new Address();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
