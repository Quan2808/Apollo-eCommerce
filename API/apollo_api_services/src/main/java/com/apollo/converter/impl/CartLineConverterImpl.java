package com.apollo.converter.impl;

import com.apollo.converter.CartConverter;
import com.apollo.converter.CartLineConverter;
import com.apollo.converter.VariantConverter;
import com.apollo.dto.CartDTO;
import com.apollo.dto.CartLineDTO;
import com.apollo.dto.VariantDTO;
import com.apollo.entity.Cart;
import com.apollo.entity.CartLine;
import com.apollo.entity.Variant;
import com.apollo.service.CartService;
import com.apollo.service.VariantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartLineConverterImpl implements CartLineConverter {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private VariantConverter variantConverter;

    @Autowired
    private VariantService variantService;

    @Override
    public CartLine convertDtoToEntity(CartLineDTO cartLineDto) {
        CartLine cartLine = new CartLine();
        BeanUtils.copyProperties(cartLineDto, cartLine);
        Cart cart = cartService.findById(cartLineDto.getCartDto().getId());
        Variant variant = variantService.findById(cartLineDto.getVariantDto().getId());
        cartLine.setCart(cart);
        cartLine.setVariant(variant);
        return cartLine;
    }

    @Override
    public CartLineDTO convertEntityToDto(CartLine cartLine) {
        CartLineDTO cartLineDto = new CartLineDTO();
        BeanUtils.copyProperties(cartLine, cartLineDto);
        CartDTO cartDto = cartConverter.convertEntityToDto(cartLine.getCart());
        VariantDTO variantDto = variantConverter.entityToDTO(cartLine.getVariant());
        cartLineDto.setCartDto(cartDto);
        cartLineDto.setVariantDto(variantDto);
        return cartLineDto;
    }

    @Override
    public List<CartLineDTO> convertEntitiesToDtos(List<CartLine> cartLines) {
        return cartLines.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override

    public List<CartLine> convertDtoToEntities(List<CartLineDTO> cartLineDTOS) {
        return null;
    }
}
