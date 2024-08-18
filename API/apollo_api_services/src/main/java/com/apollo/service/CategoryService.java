package com.apollo.service;

import com.apollo.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getCategories();

    List<CategoryDTO> findByText(String text);

    CategoryDTO findCategory(Long categoryId);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

}