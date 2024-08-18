package com.apollo.service.impl;

import com.apollo.converter.CartLineConverter;
import com.apollo.dto.CartLineDTO;
import com.apollo.entity.Cart;
import com.apollo.entity.CartLine;
import com.apollo.entity.Variant;
import com.apollo.payload.request.CartLineRequest;
import com.apollo.repository.CartLineRepository;
import com.apollo.repository.CartRepository;
import com.apollo.repository.VariantRepository;
import com.apollo.service.CartLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartLineServiceImpl implements CartLineService {
    private final CartLineRepository cartLineRepository;
    private final CartLineConverter cartLineConverter;
    private final CartRepository cartRepository;
    private final VariantRepository variantRepository;

    @Autowired
    public CartLineServiceImpl(CartLineRepository cartLineRepository, CartLineConverter cartLineConverter, CartRepository cartRepository, VariantRepository variantRepository) {
        this.cartLineRepository = cartLineRepository;
        this.cartLineConverter = cartLineConverter;
        this.cartRepository = cartRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public void updateCartLine(CartLineDTO cartLineDto, Long id) throws Exception {
        CartLine cartLine = cartLineRepository.findById(id).orElse(null);
        cartLine.setQuantity(cartLineDto.getQuantity());
        cartLineRepository.save(cartLine);
    }

    @Override
    public void removeCartLine(Long id) {
       CartLine cartLine = cartLineRepository.findById(id).orElse(null);
        cartLineRepository.deleteCartLineById(cartLine.getId());
    }

    @Override
    public List<CartLineDTO> findCartLinesByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        List<CartLine> cartLines = cartLineRepository.findCartLineByCart(cart);
        List<CartLineDTO> cartLineDTOS = cartLineConverter.convertEntitiesToDtos(cartLines);
        return cartLineDTOS;
    }

    @Override
    public CartLineDTO saveCartLine(CartLineRequest cartLineRequest) {
        Variant variant = variantRepository.findById(cartLineRequest.getVariantId()).orElse(null);
        Cart cart = cartRepository.findById(cartLineRequest.getCartId()).orElse(null);
        CartLine cartLine = cartLineRepository.findCartLineByVariant(variant);
        if (cartLine != null) {
            int newQuantity = cartLine.getQuantity() + cartLineRequest.getQuantity();
            cartLine.setQuantity(newQuantity);
            cartLineRepository.save(cartLine);

            CartLineDTO cartLineDto = cartLineConverter.convertEntityToDto(cartLine);
            return cartLineDto;
        }
        int quantity = cartLineRequest.getQuantity();
        CartLine newCartLine = new CartLine();
        newCartLine.setCart(cart);
        newCartLine.setVariant(variant);
        newCartLine.setQuantity(quantity);
        cartLineRepository.save(newCartLine);

        CartLineDTO cartLineDto = cartLineConverter.convertEntityToDto(newCartLine);
        return cartLineDto;
    }

    @Override
    public void removeAllCartLines(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        cartLineRepository.deleteAllByCart(cart);
    }
}
