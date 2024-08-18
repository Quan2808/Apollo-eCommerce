package com.apollo.converter;

import com.apollo.dto.ProductDTO;
import com.apollo.entity.Product;

import java.util.List;

public interface ProductConverter {
    List<ProductDTO> entitiesToDTOs(List<Product> element);
    ProductDTO entityToDTO(Product element);
    Product dtoToEntity(ProductDTO element);
}
