package com.apollo.controller;

import com.apollo.converter.impl.ProductConverterImpl;
import com.apollo.dto.*;
import com.apollo.entity.Product;
import com.apollo.payload.request.ProductRequest;
import com.apollo.payload.response.*;
import com.apollo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/product-detail")
public class ProductDetailController {
    private final ImageService imageServiceImpl;
    private final ProductService productServiceImpl;
    private final ProductAttributeService productAttributeServiceImpl;
    private final VariantService variantServiceImpl;
    private final VideoService videoServiceImpl;
    private final ProductConverterImpl productConverterImpl;

    private final OptionValueService optionValueServiceImpl;
    ProductDetailResponse productDetailResponse = new ProductDetailResponse();
    VariantDetailResponse variantDetailResponse = new VariantDetailResponse();
    @Autowired
    public ProductDetailController(ImageService imageServiceImpl, ProductService productServiceImpl, ProductAttributeService productAttributeServiceImpl, VariantService variantServiceImpl, VideoService videoServiceImpl, ProductConverterImpl productConverterImpl, OptionValueService optionValueServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
        this.productServiceImpl = productServiceImpl;
        this.productAttributeServiceImpl = productAttributeServiceImpl;
        this.variantServiceImpl = variantServiceImpl;
        this.videoServiceImpl = videoServiceImpl;
        this.productConverterImpl = productConverterImpl;
        this.optionValueServiceImpl = optionValueServiceImpl;
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable("product_id") Long productId) {
        productDetailResponse.setProductDTO(productServiceImpl.getProductById(productId));
        productDetailResponse.setStoreDto(productServiceImpl.getStoreByProductId(productId));
        productDetailResponse.setOptionTableDto(productServiceImpl.getOptionsByProductId(productId));
        productDetailResponse.setVariantDTOList(variantServiceImpl.getVariantByProductId(productId));
        productDetailResponse.setProductAttributeDTOList(productAttributeServiceImpl.getProductAttributeByProductId(productId));
        return ResponseEntity.ok(productDetailResponse);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<Map<String, Object>> getProductSummary(@PathVariable Long id) {
        ProductDTO product = productServiceImpl.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> summary = new HashMap<>();
        summary.put("id", product.getId());
        summary.put("title", product.getTitle());
        summary.put("description", product.getDescription());
        return ResponseEntity.ok(summary);
    }


    @GetMapping("/{product_id}/{variant_id}")
    public ResponseEntity<VariantDetailResponse> getVariant(@PathVariable("variant_id") Long variantId) {
        List<ImageDTO> images = imageServiceImpl.getImageByVariantId(variantId);
        List<VideoDTO> videos = videoServiceImpl.getVideosByVariantId(variantId);
        VariantDTO variantDto = variantServiceImpl.getVariantById(variantId);
        List<OptionValueDTO> optionValueDTOList = optionValueServiceImpl.getOptionValuesByVariantId(variantId);
        variantDetailResponse.setVariantDto(variantDto);
        variantDetailResponse.setImageDTOS(images);
        variantDetailResponse.setVideoDTOS(videos);
        variantDetailResponse.setOptionValueDTOS(optionValueDTOList);
        return ResponseEntity.ok(variantDetailResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "storeId", required = false) Long storeId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "storeCategoryId", required = false) Long storeCategoryId) {

        try {
            ProductDTO productDto = ProductDTO.builder()
                    .title(title)
                    .status("ACTIVE")
                    .createAt(Calendar.getInstance().getTime())
                    .updatedAt(Calendar.getInstance().getTime())
                    .description(description)
                    .build();

            // Chuyển đổi ProductDTO và tạo sản phẩm
            Product product = productServiceImpl.createProduct(storeId, categoryId, storeCategoryId, productDto, file);

            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("product_id") Long productId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            // Delegate product update to the service
            ProductResponse response = productServiceImpl.updateProduct(
                    productId, title, description, file);

            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Product not found
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product_id") Long productId) {
        ProductDTO productDto = productServiceImpl.getProductById(productId);

        if (productDto == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        try {
            productServiceImpl.deleteProduct(productId);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete product", HttpStatus.BAD_REQUEST);
        }
    }

}
