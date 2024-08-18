package com.apollo.service;

import com.apollo.dto.UserRegisterDTO;
import com.apollo.entity.Cart;
import com.apollo.entity.User;


public interface CartService {
    Cart createCart (UserRegisterDTO userDTO);
    Cart findCartByUserId (User user);
    Cart findById(Long id);
}
