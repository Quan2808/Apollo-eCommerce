package com.apollo.controller.sellingController;

import com.apollo.dto.OptionValueDTO;
import com.apollo.payload.response.OptionValueCreateResponse;
import com.apollo.service.impl.OptionValueServiceImpl;
import com.apollo.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/option-value")
public class OptionValueController {
    private final OptionValueServiceImpl optionValueService;
    private final ProductServiceImpl productService;

    @Autowired
    public OptionValueController(OptionValueServiceImpl optionValueService, ProductServiceImpl productService) {
        this.optionValueService = optionValueService;
        this.productService = productService;
    }

    @PostMapping("/{option_id}/create")
    public ResponseEntity<OptionValueCreateResponse> createOptionValue(@RequestBody List<String> valueRequest, @PathVariable("option_id") Long option_id) {
        OptionValueCreateResponse optionValueCreateResponse = new OptionValueCreateResponse();
        List<OptionValueDTO> optionValueDtoList = new ArrayList<>();
        for (String ele : valueRequest) {
            OptionValueDTO optionValueDto = OptionValueDTO.builder()
                    .value(ele)
                    .build();
            optionValueDtoList.add(optionValueDto);
        }
        try {
            List<OptionValueDTO> optionValue = optionValueService.createOptionValue(optionValueDtoList, option_id);
            optionValueCreateResponse.setMessage("OptionValue created successfully");
            optionValueCreateResponse.setOptionValueDtoList(optionValue);
            return new ResponseEntity<>(optionValueCreateResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            optionValueCreateResponse.setMessage("Failed to create OptionValue: " + e.getMessage());
            return new ResponseEntity<>(optionValueCreateResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/variant/{variant_id}")
    public ResponseEntity<List<OptionValueDTO>> getOptionValuesByVariantId(@PathVariable("variant_id") Long variant_id) {
        List<OptionValueDTO> optionValues = optionValueService.getOptionValuesByVariantId(variant_id);
        if (optionValues != null && !optionValues.isEmpty()) {
            return new ResponseEntity<>(optionValues, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/option/{option_id}")
    public ResponseEntity<List<OptionValueDTO>> getOptionValuesByOptionId(@PathVariable("option_id") Long option_id) {
        List<OptionValueDTO> optionValues = optionValueService.getOptionValuesByOptionId(option_id);
        if (optionValues != null && !optionValues.isEmpty()) {
            return new ResponseEntity<>(optionValues, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Phương thức tạo OptionValue dựa trên productId và optionId được gửi từ form
    @PostMapping("/create-option-value")
    public ResponseEntity<OptionValueCreateResponse> createOptionValue(
            @RequestParam("productId") Long productId,
            @RequestParam("selectedOptionId") Long optionId,
            @RequestParam("values") List<String> valueRequest) {
        OptionValueCreateResponse optionValueCreateResponse = new OptionValueCreateResponse();
        List<OptionValueDTO> optionValueDtoList = new ArrayList<>();
        for (String ele : valueRequest) {
            OptionValueDTO optionValueDto = OptionValueDTO.builder()
                    .value(ele)
                    .build();
            optionValueDtoList.add(optionValueDto);
        }
        try {
            // Kiểm tra xem optionId có thuộc productId không
            if (!productService.isOptionIdBelongsToProductId(optionId, productId)) {
                optionValueCreateResponse.setMessage("Failed to create OptionValue: OptionID không thuộc ProductID");
                return new ResponseEntity<>(optionValueCreateResponse, HttpStatus.BAD_REQUEST);
            }
            List<OptionValueDTO> optionValue = optionValueService.createOptionValue(optionValueDtoList, optionId);
            optionValueCreateResponse.setMessage("OptionValue created successfully");
            optionValueCreateResponse.setOptionValueDtoList(optionValue);
            return new ResponseEntity<>(optionValueCreateResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            optionValueCreateResponse.setMessage("Failed to create OptionValue: " + e.getMessage());
            return new ResponseEntity<>(optionValueCreateResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OptionValueDTO>> getOptionValuesByProductId(@PathVariable("productId") Long productId) {
        List<OptionValueDTO> optionValues = optionValueService.getOptionValuesByProductId(productId);
        if (optionValues != null && !optionValues.isEmpty()) {
            return new ResponseEntity<>(optionValues, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
