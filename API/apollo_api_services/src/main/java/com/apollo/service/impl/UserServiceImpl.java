package com.apollo.service.impl;

import com.apollo.repository.AdminRepository;
import com.apollo.repository.ShipperRepository;
import com.apollo.repository.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements com.apollo.service.UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ShipperRepository shipperRepository;

    public UserServiceImpl(UserRepository userRepository, AdminRepository adminRepository, ShipperRepository shipperRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.shipperRepository = shipperRepository;
    }

    public List<?> getAllUsers(String type) {
        switch (type.toLowerCase()) {
            case "customer":
                return userRepository.findAll();
            case "admin":
                return adminRepository.findAll();
            case "shipper":
                return shipperRepository.findAll();
            default:
                throw new IllegalArgumentException("Unknown person type: " + type);
        }
    }

    public Object getUserById(String type, Long id) {
        switch (type.toLowerCase()) {
            case "customer":
                return userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            case "admin":
                return adminRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
            case "shipper":
                return shipperRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Shipper not found with id: " + id));
            default:
                throw new IllegalArgumentException("Unknown person type: " + type);
        }
    }
}
