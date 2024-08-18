package com.apollo.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private String mainPicture;
    private String status;
    private Date createAt;
    private Date updatedAt;

    // Đối tượng đầy đủ
    private StoreDTO store;
    private CategoryDTO category;
    private StoreCategoryDTO storeCategory;

    // Các trường ID riêng lẻ
    private Long storeId;
    private Long categoryId;
    private Long storeCategoryId;

    private List<BulletDTO> bulletDTOList;
    private List<VariantDTO> variants;
    private List<OptionTableDTO> optionTables;
}
