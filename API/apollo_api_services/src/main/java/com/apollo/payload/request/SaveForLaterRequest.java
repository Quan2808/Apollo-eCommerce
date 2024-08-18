package com.apollo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveForLaterRequest {
    @Null
    private Long id;
    private int quantity;
    private Long cartId;
    private Long variantId;
}
