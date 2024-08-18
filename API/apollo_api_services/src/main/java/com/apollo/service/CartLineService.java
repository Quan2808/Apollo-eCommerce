package com.apollo.service;

import com.apollo.dto.CartLineDTO;
import com.apollo.payload.request.CartLineRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartLineService {
    CartLineDTO saveCartLine(CartLineRequest cartLineRequest);

    void updateCartLine(CartLineDTO cartLineDto, Long id) throws Exception;

    void removeCartLine(Long id);

    List<CartLineDTO> findCartLinesByCartId(Long cartId);

    void removeAllCartLines(Long cartId);
}
