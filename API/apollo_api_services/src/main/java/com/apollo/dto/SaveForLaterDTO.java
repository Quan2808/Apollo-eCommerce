package com.apollo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveForLaterDTO {
    private Long id;
    private int quantity;
    private CartDTO cartDto;
    private VariantDTO variantDto;
}
