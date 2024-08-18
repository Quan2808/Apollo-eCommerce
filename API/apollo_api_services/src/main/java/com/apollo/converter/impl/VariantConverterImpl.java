package com.apollo.converter.impl;

import com.apollo.converter.VariantConverter;
import com.apollo.dto.VariantDTO;
import com.apollo.entity.Variant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class VariantConverterImpl implements VariantConverter {
    @Override
    public List<VariantDTO> entitiesToDTOs(List<Variant> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VariantDTO entityToDTO(Variant element) {
        if (element == null) {
            return null;
        }
        VariantDTO result = new VariantDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public Variant dtoToEntity(VariantDTO element) {
        if (element == null) {
            return null;
        }
        Variant result = new Variant();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}