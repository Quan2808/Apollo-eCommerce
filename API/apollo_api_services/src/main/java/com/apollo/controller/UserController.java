package com.apollo.controller;

import com.apollo.service.UserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<Object> getAccountTypeById(@PathVariable String type, @PathVariable Long id) {
        try {
            Object accounts = userService.getUserById(type, id);
            return ResponseEntity.ok(accounts);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the person");
        }
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<?>> getAllAccountsType(@PathVariable String type) {
        List<?> accounts = userService.getAllUsers(type);
        return ResponseEntity.ok(accounts);
    }

}
