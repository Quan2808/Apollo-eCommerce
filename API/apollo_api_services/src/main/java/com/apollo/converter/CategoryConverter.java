package com.apollo.converter;

import com.apollo.dto.CategoryDTO;
import com.apollo.entity.Category;

import java.util.List;

public interface CategoryConverter {
    List<CategoryDTO> entitiesToDTOs(List<Category> element);
    CategoryDTO entityToDTO(Category element);
    Category dtoToEntity(CategoryDTO element);
}
