package com.apollo.controller;

import com.apollo.dto.StoreCategoryDTO;
import com.apollo.entity.StoreCategory;
import com.apollo.payload.request.AddStoreCateRequest;
import com.apollo.service.StoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/store-category")
public class StoreCategoryController {

    @Autowired
    private StoreCategoryService service;

    @GetMapping
    public List<StoreCategoryDTO> getStoreCategories() {
        return service.getStoreCategories();
    }

    @PostMapping("/{storeId}")
    public List<StoreCategoryDTO> createListStoreCategory(@RequestBody List<String> storeCateList, @PathVariable Long storeId) {
        return service.createStoreCategories(storeCateList, storeId);
    }

    @GetMapping("/by-store/{storeId}")
    public List<StoreCategoryDTO> getStoreCategoriesByStoreId(@PathVariable Long storeId) {
        return service.getStoreCategoriesByStoreId(storeId);
    }

    @PostMapping("/add")
    public StoreCategoryDTO createStoreCategory(@ModelAttribute AddStoreCateRequest addStoreCateRequest) {
        return service.createOrUpdateStoreCategory(addStoreCateRequest);
    }

    @PutMapping("/{id}")
    public StoreCategoryDTO updateStoreCategory(@PathVariable Long id, @ModelAttribute AddStoreCateRequest addStoreCateRequest) {
        addStoreCateRequest.setId(id);
        return service.createOrUpdateStoreCategory(addStoreCateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteStoreCategory(@PathVariable Long id) {
        service.deleteStoreCategory(id);
    }
}
