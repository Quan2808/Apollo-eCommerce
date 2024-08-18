package com.apollo.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantRequest {
    private String name;
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private double price;
    private double salePrice;
    private String img;
    private int product_id;
}
