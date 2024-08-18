package com.apollo.repository;

import com.apollo.entity.Store;
import com.apollo.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {
    List<StoreCategory> findAllByParentStoreCategory(StoreCategory parentCategory);
    List<StoreCategory> findAllByStore(Store store);
    StoreCategory findByName(String name);

    List<StoreCategory> findByStoreId(Long storeId);
}
