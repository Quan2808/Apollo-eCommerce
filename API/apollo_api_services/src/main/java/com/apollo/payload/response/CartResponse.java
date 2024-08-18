package com.apollo.payload.response;

import com.apollo.dto.CartLineDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String message;
    private String statusCode;
    private List<CartLineDTO> data;
}
