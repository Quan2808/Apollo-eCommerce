package com.apollo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.apollo.dto.ProductDTO;
import com.apollo.dto.StoreDTO;
import com.apollo.payload.request.AddStoreRequest;
import com.apollo.service.ProductService;
import com.apollo.service.StoreService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;
    private final ProductService productService;

    public StoreController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
    }

    @GetMapping()
    public List<StoreDTO> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{store_id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable("store_id") Long storeId) {
        StoreDTO storeDto = storeService.findStore(storeId);
        return new ResponseEntity<>(storeDto, HttpStatus.OK);
    }

    @GetMapping("/{store_id}/product")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam("name") String searchText, @PathVariable("store_id") Long storeId) {
        List<ProductDTO> productDTOList = productService.getProductsOfStoreByContaining(storeId, searchText);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{store_id}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByStore(@PathVariable("store_id") Long storeId) {
        List<ProductDTO> productDTOList = productService.getAllProductDtosByStore(storeId);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{store_id}/products/category")
    public ResponseEntity<List<ProductDTO>> getProductsByStoreCategory(@RequestParam("name") String categoryName) {
        List<ProductDTO> productDTOList = productService.getAllProductDtosByStoreCategory(categoryName);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{store_id}/products/sub_category")
    public ResponseEntity<List<ProductDTO>> getProductsByStoreSubCategory(@RequestParam("name") String categoryName) {
        List<ProductDTO> productDTOList = productService.getAllProductDtosByStoreSubCategory(categoryName);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @PostMapping("/create/{adminId}")
    @Transactional
    public ResponseEntity<StoreDTO> createStore(@PathVariable("adminId") Long adminId, @ModelAttribute AddStoreRequest storeDto) {
        StoreDTO storeDto1 = storeService.createStore(adminId, storeDto);
        return new ResponseEntity<>(storeDto1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StoreDTO> deleteCategory(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

}
