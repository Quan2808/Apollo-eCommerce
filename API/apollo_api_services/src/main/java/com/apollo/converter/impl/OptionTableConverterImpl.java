package com.apollo.converter.impl;

import com.apollo.converter.OptionTableConverter;
import com.apollo.dto.OptionTableDTO;
import com.apollo.entity.OptionTable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OptionTableConverterImpl implements OptionTableConverter {
    @Override
    public List<OptionTableDTO> entitiesToDTOs(List<OptionTable> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public OptionTableDTO entityToDTO(OptionTable element) {
        OptionTableDTO result = new OptionTableDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }
    @Override
    public OptionTable dtoToEntity(OptionTableDTO element) {
        OptionTable result = new OptionTable();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public  List<OptionTable> dtoToEntities(List<OptionTableDTO> element) {
        return element.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }

}
