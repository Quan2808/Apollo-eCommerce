package com.apollo.service.impl;

import com.apollo.configuration.JwtTokenUtil;
import com.apollo.constraint.Role;
import com.apollo.dto.AdminLoginDTO;
import com.apollo.dto.AdminRegisterDTO;
import com.apollo.dto.JwtResponse;
import com.apollo.entity.Admin;
import com.apollo.repository.AdminRepository;
import com.apollo.security.JwtUserDetailsService;
import com.apollo.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @Override
    public JwtResponse login(AdminLoginDTO adminLoginDto) {
        Admin admin = adminRepository.findByEmail(adminLoginDto.getEmail());
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(adminLoginDto.getPassword(), admin.getPassword())) {
            throw new AuthenticationServiceException("Wrong password");
        }

        String accessToken = jwtTokenUtil.generateAdminToken(admin);
        String refreshToken = jwtTokenUtil.generateAdminRefreshToken(admin);

        return new JwtResponse(accessToken, refreshToken);
    }


    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);


    @Override
    @Transactional
    public Admin register(AdminRegisterDTO adminRegisterDto) {
        logger.info("Attempting to register admin with email: " + adminRegisterDto.getEmail());

        if (adminRepository.findByEmail(adminRegisterDto.getEmail()) != null) {
            logger.warn("Admin with email " + adminRegisterDto.getEmail() + " already exists");
            throw new IllegalArgumentException("Email has already been registered");
        }

        String password = adminRegisterDto.getPassword();
        Admin admin = new Admin();
        admin.setAdminName(adminRegisterDto.getName());
        admin.setEmail(adminRegisterDto.getEmail());
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ROLE_" + Role.ADMIN.toString());

        adminRepository.save(admin);
        logger.info("Admin registered successfully with email: " + adminRegisterDto.getEmail());

        return admin;
    }

    @Override
    public Admin findById(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        return admin;
    }
}

