package com.apollo.controller.sellingController;

import com.apollo.dto.ProductAttributeDTO;
import com.apollo.entity.ProductAttribute;
import com.apollo.payload.request.ProductAttributeRequest;
import com.apollo.payload.response.ProductAttributeResponse;
import com.apollo.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/attribute/{product_id}")
public class AttributeController {
    @Autowired
    private ProductAttributeService iProductAttributeService;
    @PostMapping
    public ResponseEntity<ProductAttributeResponse> createProductAttribute(@RequestBody ProductAttributeRequest productAttributeRequest, @PathVariable Long product_id){
        ProductAttributeResponse productAttributeResponse = new ProductAttributeResponse();
        List<ProductAttributeDTO> productAttributeDtoList = productAttributeRequest.getProductAttributeDtoList();
        List<ProductAttribute> productAttributeList = iProductAttributeService.createProductAttribute(productAttributeDtoList, product_id);
        if(productAttributeList.size() > 0){
            productAttributeResponse.setMessage("successfully");
            return new ResponseEntity<>(productAttributeResponse, HttpStatus.OK);
        } else {
            productAttributeResponse.setMessage("fail to create");
            return new ResponseEntity<>(productAttributeResponse, HttpStatus.BAD_REQUEST);

        }


    }
}
