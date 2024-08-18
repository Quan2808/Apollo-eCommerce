package com.apollo.payload.response;

import com.apollo.entity.Cart;
import com.apollo.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartLineResponse {

    private Long id;

    private int quantity;

    private Cart cart;

    private Variant variant;
}
