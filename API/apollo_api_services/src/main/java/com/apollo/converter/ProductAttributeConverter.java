package com.apollo.converter;

import com.apollo.dto.ProductAttributeDTO;
import com.apollo.entity.ProductAttribute;

import java.util.List;

public interface ProductAttributeConverter {
    ProductAttribute dtoToEntity(ProductAttributeDTO element);
    List<ProductAttributeDTO> entitiesToDTOs(List<ProductAttribute> element);
    ProductAttributeDTO entityToDTO(ProductAttribute element);
}
