package com.apollo.converter.impl;

import com.apollo.converter.CartConverter;
import com.apollo.dto.CartDTO;
import com.apollo.entity.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CartConverterImpl implements CartConverter {
    @Override
    public Cart convertDtoToEntity(CartDTO cartDto) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDto, cart);
        return cart;
    }

    @Override
    public CartDTO convertEntityToDto(Cart cart) {
        CartDTO cartDto = new CartDTO();
        BeanUtils.copyProperties(cart, cartDto);
        return cartDto;
    }
}
