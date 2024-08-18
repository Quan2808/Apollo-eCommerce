package com.apollo.controller.sellingController;

import com.apollo.converter.VariantConverter;
import com.apollo.dto.VariantDTO;
import com.apollo.entity.Variant;
import com.apollo.payload.request.CreateVariantRequest;
import com.apollo.payload.request.VariantRequest;
import com.apollo.payload.request.VariantUpdateRequest;
import com.apollo.payload.response.VariantCreateResponse;
import com.apollo.payload.response.VariantRawResponse;
import com.apollo.repository.VariantRepository;
import com.apollo.service.impl.VariantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/variant")
public class VariantController {
    private final VariantServiceImpl variantService;
    private final VariantRepository variantRepository;
    private final VariantConverter variantConverterImpl;

    public VariantController(VariantServiceImpl variantService, VariantRepository variantRepository, VariantConverter variantConverterImpl) {
        this.variantService = variantService;
        this.variantRepository = variantRepository;
        this.variantConverterImpl = variantConverterImpl;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<VariantDTO>> getVariantsByProductId(@PathVariable Long productId) {
        List<VariantDTO> variants = variantService.getVariantsByProduct(productId);
        return ResponseEntity.ok(variants);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<VariantDTO> createVariantForProduct(@PathVariable Long productId, @RequestBody VariantDTO v
    ) {
        try {
            VariantDTO createdVariant = variantService.createVariantForProduct(productId, v);
            return new ResponseEntity<>(createdVariant, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //createVariantByProductId, and ListValue
    @PostMapping("/create")
    public ResponseEntity<VariantRawResponse> createVariantWithListValue(
            @RequestBody CreateVariantRequest request) {

        VariantRawResponse variantRawResponse = new VariantRawResponse();
        VariantDTO variantDto = variantService.createRawVariant(request.getValueIdList(), request.getProduct_id());

        if (variantDto != null) {
            variantRawResponse.setMessage("OptionValue created successfully");
            variantRawResponse.setVariantDto(variantDto);
            return new ResponseEntity<>(variantRawResponse, HttpStatus.CREATED);
        } else {
            variantRawResponse.setMessage("Failed to create Variant");
            return new ResponseEntity<>(variantRawResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{product_id}/create1")
    public ResponseEntity<VariantCreateResponse> createVariant(@RequestBody VariantRequest variantRequest, @PathVariable("product_id") Long product_id) {
        VariantCreateResponse variantCreateResponse = new VariantCreateResponse();
        VariantDTO variantDto = VariantDTO.builder()
                .name(variantRequest.getName())
                .skuCode(variantRequest.getSkuCode())
                .stockQuantity(variantRequest.getStockQuantity())
                .weight(variantRequest.getWeight())
                .price(variantRequest.getPrice())
                .salePrice(variantRequest.getSalePrice())
                .img(variantRequest.getImg())
                .build();

        Variant variant = variantService.createVariant(variantDto, product_id);

        if (variant != null) {
            variantCreateResponse.setMessage("Variant created successfully");
            variantCreateResponse.setVariant_id(variant.getId());
            return new ResponseEntity<>(variantCreateResponse, HttpStatus.CREATED);
        } else {
            variantCreateResponse.setMessage("Failed to create Variant");
            return new ResponseEntity<>(variantCreateResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<VariantDTO> updateVariant(
            @ModelAttribute VariantUpdateRequest variantUpdateRequest,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        VariantDTO updatedVariant = variantService.updateVariant(variantUpdateRequest, imageFile);
        if (updatedVariant != null) {
            return new ResponseEntity<>(updatedVariant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{variant_id}")
    public ResponseEntity<Long> deleteVariant(@PathVariable("variant_id") Long variant_Id) {
        VariantDTO variantDto = variantService.getVariantById(variant_Id);
        if (variantDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            variantService.deleteVariant(variant_Id);
            return new ResponseEntity<>(variant_Id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{variantId}")
    public ResponseEntity<VariantDTO> getVariantByVariantId(@PathVariable Long variantId) {
        VariantDTO variantDTO = variantService.getVariantById(variantId);
        return new ResponseEntity<>(variantDTO, HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<VariantDTO> getVariantById(@PathVariable("productId")Long productId){
        VariantDTO variantDTO = variantService.getLowestPriceVariantByProductId(productId);
        return new ResponseEntity<>(variantDTO, HttpStatus.OK);
    }
}
