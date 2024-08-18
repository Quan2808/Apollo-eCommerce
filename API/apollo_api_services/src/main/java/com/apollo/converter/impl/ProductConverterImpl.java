package com.apollo.converter.impl;

import com.apollo.converter.ProductConverter;
import com.apollo.dto.CategoryDTO;
import com.apollo.dto.ProductDTO;
import com.apollo.dto.StoreCategoryDTO;
import com.apollo.dto.StoreDTO;
import com.apollo.entity.Category;
import com.apollo.entity.Product;
import com.apollo.entity.Store;
import com.apollo.entity.StoreCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverterImpl implements ProductConverter {

    @Override
    public List<ProductDTO> entitiesToDTOs(List<Product> elements) {
        return elements.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO entityToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(entity, dto);

        // Sao chép các trường ID liên quan
        if (entity.getStore() != null) {
            dto.setStoreId(entity.getStore().getId());
            dto.setStore(entityToDTO(entity.getStore())); // Chuyển đổi Store entity sang StoreDTO
        }
        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
            dto.setCategory(entityToDTO(entity.getCategory())); // Chuyển đổi Category entity sang CategoryDTO
        }
        if (entity.getStoreCategory() != null) {
            dto.setStoreCategoryId(entity.getStoreCategory().getId());
            dto.setStoreCategory(entityToDTO(entity.getStoreCategory())); // Chuyển đổi StoreCategory entity sang StoreCategoryDTO
        }

        return dto;
    }

    @Override
    public Product dtoToEntity(ProductDTO dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        // Khôi phục các liên kết từ các ID nếu có
        if (dto.getStoreId() != null) {
            entity.setStore(dtoToEntity(dto.getStore())); // Chuyển đổi StoreDTO sang Store entity
        }
        if (dto.getCategoryId() != null) {
            entity.setCategory(dtoToEntity(dto.getCategory())); // Chuyển đổi CategoryDTO sang Category entity
        }
        if (dto.getStoreCategoryId() != null) {
            entity.setStoreCategory(dtoToEntity(dto.getStoreCategory())); // Chuyển đổi StoreCategoryDTO sang StoreCategory entity
        }

        return entity;
    }

    // Bổ sung các phương thức chuyển đổi riêng biệt cho Store, Category và StoreCategory

    public Store dtoToEntity(StoreDTO dto) {
        Store entity = new Store();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public Category dtoToEntity(CategoryDTO dto) {
        Category entity = new Category();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public StoreCategory dtoToEntity(StoreCategoryDTO dto) {
        StoreCategory entity = new StoreCategory();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public StoreDTO entityToDTO(Store entity) {
        StoreDTO dto = new StoreDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public CategoryDTO entityToDTO(Category entity) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public StoreCategoryDTO entityToDTO(StoreCategory entity) {
        StoreCategoryDTO dto = new StoreCategoryDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
