package com.apollo.dto;

import com.apollo.entity.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryDTO {
    private Long id;
    private String name;
    private String heroImage;
    private String squareImage;
    private StoreCategory parentStoreCategory;
    private Long storeId;
}
