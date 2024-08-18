package com.apollo.controller.back_office;

import com.apollo.converter.AdminConverter;
import com.apollo.dto.AdminDTO;
import com.apollo.entity.Admin;
import com.apollo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminConverter adminConverter;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{admin_id}")
    public ResponseEntity<AdminDTO> INgetUser(@PathVariable("admin_id") Long adminId){
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        AdminDTO adminDTO = adminConverter.convertEntityToDTO(admin);
        return ResponseEntity.ok(adminDTO);
    }
}
