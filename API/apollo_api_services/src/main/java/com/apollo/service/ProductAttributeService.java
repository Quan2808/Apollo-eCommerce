package com.apollo.service;

import com.apollo.dto.ProductAttributeDTO;
import com.apollo.entity.ProductAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeDTO> getProductAttributeByProductId(Long product_id);
    List<ProductAttribute> createProductAttribute(List<ProductAttributeDTO> productAttributeDtoList, Long product_Id);

    @SpringBootApplication(exclude = {FlywayAutoConfiguration.class})
    class ApolloApiServiceApplication {

        public static void main(String[] args) {
            SpringApplication.run(ApolloApiServiceApplication.class, args);
        }
    }
}
