package com.apollo.service;

import com.apollo.dto.AdminLoginDTO;
import com.apollo.dto.AdminRegisterDTO;
import com.apollo.dto.JwtResponse;
import com.apollo.entity.Admin;

public interface AdminService {
    JwtResponse login(AdminLoginDTO adminLoginDTO);
    Admin register(AdminRegisterDTO adminRegisterDTO);
    Admin findById (Long id);
}
