package com.apollo.configuration.initializer;

import com.apollo.entity.Admin;
import com.apollo.repository.AdminRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
public class AdminInitializer {

    private final AdminRepository repo;

    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.repo = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init() {
        if (repo.existsByEmail("admin@apollo.com")) {
            return;
        }

        Admin admin = new Admin();
        admin.setAdminName("Apollo Administrator");
        admin.setEmail("admin@apollo.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole("ROLE_ADMIN");
        repo.save(admin);
    }
}
