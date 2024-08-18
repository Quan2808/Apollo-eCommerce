package com.apollo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartLineDTO {
    @Null
    private Long id;
    private int quantity;
    private CartDTO cartDto;
    private VariantDTO variantDto;
}
