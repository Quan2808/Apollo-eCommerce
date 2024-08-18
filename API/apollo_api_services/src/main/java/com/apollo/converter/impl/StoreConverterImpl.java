package com.apollo.converter.impl;

import com.apollo.converter.StoreConverter;
import com.apollo.dto.StoreDTO;
import com.apollo.entity.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreConverterImpl implements StoreConverter {
    @Override
    public List<StoreDTO> entitiesToDTOs(List<Store> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StoreDTO entityToDTO(Store element) {
        StoreDTO result = new StoreDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public Store dtoToEntity(StoreDTO element) {
        Store result = new Store();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
