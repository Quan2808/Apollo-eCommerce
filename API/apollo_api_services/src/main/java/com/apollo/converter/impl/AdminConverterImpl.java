package com.apollo.converter.impl;

import com.apollo.converter.AdminConverter;
import com.apollo.dto.AdminDTO;
import com.apollo.entity.Admin;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AdminConverterImpl implements AdminConverter {
    @Override
    public List<AdminDTO> entitiesToDTOs(List<Admin> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO entityToDTO(Admin element) {
        AdminDTO result = new AdminDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public Admin dtoToEntity(AdminDTO element) {
        Admin result = new Admin();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public AdminDTO convertEntityToDTO(Admin admin) {
        AdminDTO result = new AdminDTO();
        BeanUtils.copyProperties(admin, result);
        return result;
    }
}
