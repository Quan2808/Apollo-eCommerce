package com.apollo.controller;

import com.apollo.dto.ProductDTO;
import com.apollo.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping
    public List<ProductDTO> productDtoList() {
        return productServiceImpl.getAllProductDtos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id) {
        ProductDTO productDto = productServiceImpl.getProductById(id);
        return productDto != null
                ? ResponseEntity.ok(productDto)
                : ResponseEntity.notFound().build();
    }
}
