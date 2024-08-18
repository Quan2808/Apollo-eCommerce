package com.apollo.service.impl;

import com.apollo.converter.impl.ProductAttributeConverterImpl;
import com.apollo.dto.ProductAttributeDTO;
import com.apollo.entity.Product;
import com.apollo.entity.ProductAttribute;
import com.apollo.repository.ProductAttributeRepository;
import com.apollo.repository.ProductRepository;
import com.apollo.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private ProductAttributeConverterImpl productAttributeConverterImpl;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductAttributeDTO> getProductAttributeByProductId(Long product_id) {
        List<ProductAttribute> productAttributeList = productAttributeRepository.findByProductId(product_id);
        return productAttributeConverterImpl.entitiesToDTOs(productAttributeList);
    }

    @Override
    public List<ProductAttribute> createProductAttribute(List<ProductAttributeDTO> productAttributeDtoList, Long product_Id) {
        Product product = productRepository.findById(product_Id)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        if(product != null){
            for(ProductAttributeDTO element : productAttributeDtoList){
                ProductAttribute productAttribute = new ProductAttribute();
                productAttribute.setName(element.getName());
                productAttribute.setValue(element.getValue());
                productAttribute.setProduct(product);
                productAttributeRepository.save(productAttribute);
            }
        }
        List<ProductAttribute> productAttributeList = product.getProductAttributes();
        return productAttributeList;
    }

}
