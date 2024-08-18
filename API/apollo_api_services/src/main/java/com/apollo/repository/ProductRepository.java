package com.apollo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apollo.entity.Product;
import com.apollo.entity.Store;
import com.apollo.entity.StoreCategory;

@Repository
@SuppressWarnings("null")
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAll();
    List<Product> findByTitleContaining(String text);

    List<Product> findAllByStore(Store store);
    List<Product> findAllByStoreCategory(StoreCategory storeCategory);
}

