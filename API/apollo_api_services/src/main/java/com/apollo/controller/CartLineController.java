package com.apollo.controller;

import com.apollo.dto.CartLineDTO;
import com.apollo.entity.Cart;
import com.apollo.entity.CartLine;
import com.apollo.entity.User;
import com.apollo.entity.Variant;
import com.apollo.payload.request.CartLineRequest;
import com.apollo.repository.CartLineRepository;
import com.apollo.repository.CartRepository;
import com.apollo.repository.VariantRepository;
import com.apollo.service.CartLineService;
import com.apollo.service.CartService;
import com.apollo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/cart-lines")
@AllArgsConstructor
public class CartLineController {
    private CartLineService cartLineService;
    private CartService cartService;
    private AuthService authService;

    private final CartLineRepository cartLineRepository;
    private final CartRepository cartRepository;
    private final VariantRepository variantRepository;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartLine(@PathVariable("id")Long cartLineId){
        cartLineService.removeCartLine(cartLineId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<CartLineDTO> addCartLine(@RequestBody CartLineRequest cartLineRequest){
        CartLineDTO cartLineDto = cartLineService.saveCartLine(cartLineRequest);
        return new ResponseEntity<>(cartLineDto ,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllCartLines(@PathVariable("id") Long userId){
        User user = authService.findById(userId);
        Cart cart = cartService.findCartByUserId(user);
        List<CartLineDTO> cartLineDTOS = cartLineService.findCartLinesByCartId(cart.getId());
        return new ResponseEntity<>(cartLineDTOS,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateCartLine(@RequestBody CartLineDTO cartLineDto) throws Exception {
        cartLineService.updateCartLine(cartLineDto, cartLineDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-all/{id}")
    public ResponseEntity<?> deleteAllCartLine(@PathVariable("id") Long userId){
        User user = authService.findById(userId);
        Cart cart = cartService.findCartByUserId(user);
        cartLineService.removeAllCartLines(cart.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/manage-cart-line")
    public ResponseEntity<?> manageCartLine(@RequestBody CartLineDTO cartLineDto) throws Exception {
        if (cartLineDto.getId() != null) {
            CartLine cartLine = cartLineRepository.findById(cartLineDto.getId()).orElse(null);

            if (cartLine != null) {
                if (cartLineDto.getQuantity() == 0) {
                    cartLineRepository.deleteCartLineById(cartLine.getId());
                } else {
                    cartLine.setQuantity(cartLineDto.getQuantity());
                    cartLineRepository.save(cartLine);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        Cart cart = cartRepository.findById(cartLineDto.getCartDto().getId()).orElse(null);
        Variant variant = variantRepository.findById(cartLineDto.getVariantDto().getId()).orElse(null);

        CartLine newCartLine = new CartLine();
        newCartLine.setCart(cart);
        newCartLine.setVariant(variant);
        newCartLine.setQuantity(cartLineDto.getQuantity());

        cartLineRepository.save(newCartLine);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
