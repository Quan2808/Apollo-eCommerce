package com.apollo.controller.back_office;

import com.apollo.converter.UserConverter;
import com.apollo.dto.UserDTO;
import com.apollo.entity.User;
import com.apollo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class CustomerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("user_id") Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDTO userDTO = userConverter.convertEntityToDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}
