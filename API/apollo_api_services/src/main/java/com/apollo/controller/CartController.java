package com.apollo.controller;

import com.apollo.dto.UserRegisterDTO;
import com.apollo.entity.Cart;
import com.apollo.entity.User;
import com.apollo.repository.UserRepository;
import com.apollo.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // Tạo giỏ hàng mới
    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody UserRegisterDTO userDTO) {
        Cart cart = cartService.createCart(userDTO);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = cartService.findCartByUserId(user);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }
}
