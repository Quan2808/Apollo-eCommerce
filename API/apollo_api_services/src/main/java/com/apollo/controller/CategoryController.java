package com.apollo.controller;

import com.apollo.dto.CategoryDTO;
import com.apollo.payload.response.SearchCategoryResponse;
import com.apollo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping()
    public List<CategoryDTO> getCategories() {
        return service.getCategories();
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = service.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchCategoryResponse> searchCategories(@RequestParam("query") String query) {
        SearchCategoryResponse searchCategoryResponse = new SearchCategoryResponse();
        List<CategoryDTO> categoryDtoList = service.findByText(query);
        searchCategoryResponse.setCategoryDtoList(categoryDtoList);
        return ResponseEntity.ok(searchCategoryResponse);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDTO> getStore(@PathVariable("category_id") Long category_id) {
        CategoryDTO CategoryDto = service.findCategory(category_id);
        return new ResponseEntity<>(CategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = service.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
}