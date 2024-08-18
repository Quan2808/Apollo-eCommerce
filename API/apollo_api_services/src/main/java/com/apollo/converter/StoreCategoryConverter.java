package com.apollo.converter;

import com.apollo.dto.StoreCategoryDTO;
import com.apollo.entity.StoreCategory;

import java.util.List;

public interface StoreCategoryConverter {
    List<StoreCategoryDTO> convertEntitiesToDTOs(List<StoreCategory> categories);
    StoreCategoryDTO convertEntityToDTO(StoreCategory element);
    StoreCategory dtoToEntity(StoreCategoryDTO element);

}
