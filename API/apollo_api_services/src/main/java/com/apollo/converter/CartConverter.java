package com.apollo.converter;

import com.apollo.dto.CartDTO;
import com.apollo.entity.Cart;

public interface CartConverter {
    Cart convertDtoToEntity(CartDTO cartDto);
    CartDTO convertEntityToDto(Cart cart);
}
