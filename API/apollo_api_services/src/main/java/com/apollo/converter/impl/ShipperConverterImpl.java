package com.apollo.converter.impl;

import com.apollo.converter.ShipperConverter;
import com.apollo.dto.ShipperDTO;
import com.apollo.dto.ShipperLoginDTO;
import com.apollo.entity.Shipper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShipperConverterImpl implements ShipperConverter {

    @Override
    public ShipperDTO convertEntityToDTO(Shipper shipper) {
        ShipperDTO result = new ShipperDTO();
        BeanUtils.copyProperties(shipper, result);
        return result;
    }

    @Override
    public List<ShipperLoginDTO> convertEntitiesToDTOs(List<Shipper> element) {
        return null;
    }

    @Override
    public List<ShipperDTO> entitiesToDTOs(List<Shipper> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShipperDTO entityToDTO(Shipper element) {
        ShipperDTO result = new ShipperDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

}
