package com.apollo.service.impl;

import com.apollo.converter.CategoryConverter;
import com.apollo.dto.CategoryDTO;
import com.apollo.entity.Category;
import com.apollo.repository.CategoryRepository;
import com.apollo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter convert;

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = convert.dtoToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convert.entityToDTO(savedCategory);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDtoList = convert.entitiesToDTOs(categories);
        return categoryDtoList;
    }

    @Override
    public List<CategoryDTO> findByText(String text) {
        List<Category> categories = categoryRepository.findByAttributeContaining(text);
        List<CategoryDTO> categoryDtoList = convert.entitiesToDTOs(categories);
        return categoryDtoList;
    }

    @Override
    public CategoryDTO findCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("category not found"));
        CategoryDTO categoryDto = convert.entityToDTO(category);
        return categoryDto;
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        categoryRepository.delete(category);

        return convert.entityToDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        existingCategory.setAttribute(categoryDTO.getAttribute());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return convert.entityToDTO(updatedCategory);
    }

}
