package com.apollo.dto.order_sumary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
    private Long id;
    private String orderDate;
    private String status;
    private Double orderTotal;

    private String variantName;
    private int quantity;
    private Double price;
}
