package com.apollo.service;

import com.apollo.dto.StoreCategoryDTO;
import com.apollo.payload.request.AddStoreCateRequest;

import java.util.List;

public interface StoreCategoryService {
    List<StoreCategoryDTO> createStoreCategories(List<String> storeCateList, Long storeId);

    StoreCategoryDTO createStoreCategory(StoreCategoryDTO storeCategoryDTO, Long storeCategoryId);

    List<StoreCategoryDTO> getStoreCategories();

    List<StoreCategoryDTO> getStoreCategoriesByStoreId(Long storeId);

    StoreCategoryDTO createOrUpdateStoreCategory(AddStoreCateRequest addStoreCateRequest);

    void deleteStoreCategory(Long id);
}
