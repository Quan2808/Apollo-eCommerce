package com.apollo.payload.response;

import com.apollo.dto.*;
import lombok.Data;

import java.util.List;
@Data
public class ProductDetailResponse {
    private ProductDTO productDTO;
    private int ratings;
    private double star;
    private StoreDTO storeDto;
    private List<OptionTableDTO> optionTableDto;
    private List<VariantDTO> variantDTOList;
    private List<ProductAttributeDTO> productAttributeDTOList;
    private VariantDTO variantDto;
    private List<ReviewDTO> reviewDTOList;
}
