package com.apollo.service.impl;

import com.apollo.converter.ShippingMethodConverter;
import com.apollo.entity.ShippingMethod;
import com.apollo.payload.response.ShippingMethodResponse;
import com.apollo.repository.ShippingMethodRepository;
import com.apollo.service.ShippingMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShippingMethodServiceImpl implements ShippingMethodService {
    @Autowired
    private final ShippingMethodRepository shippingMethodRepository;

    @Qualifier("shippingMethodConverterImpl")
    @Autowired
    private final ShippingMethodConverter shippingMethodConverter;

    @Override
    public List<ShippingMethodResponse> findShippingMethod() {
        List<ShippingMethod> shippingMethodList = shippingMethodRepository.findAll();
        List<ShippingMethodResponse> shippingMethodResponseList = new ArrayList<>();
        for(ShippingMethod shippingMethod : shippingMethodList){
            ShippingMethodResponse shippingMethodResponse = shippingMethodConverter.convertToDto(shippingMethod);
            shippingMethodResponseList.add(shippingMethodResponse);
        }
        return shippingMethodResponseList;
    }
}
