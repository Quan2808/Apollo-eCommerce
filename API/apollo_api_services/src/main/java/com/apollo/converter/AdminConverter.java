package com.apollo.converter;


import com.apollo.dto.AdminDTO;
import com.apollo.entity.Admin;

import java.util.List;

public interface AdminConverter {
    List<AdminDTO> entitiesToDTOs(List<Admin> element);
    AdminDTO entityToDTO(Admin element);
    Admin dtoToEntity(AdminDTO element);
    AdminDTO convertEntityToDTO(Admin admin);
}
