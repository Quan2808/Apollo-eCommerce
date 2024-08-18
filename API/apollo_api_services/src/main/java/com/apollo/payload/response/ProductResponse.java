package com.apollo.payload.response;

import com.apollo.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private ProductDTO productDto;
    private List<ProductAttributeDTO> productAttributeDTOList;
    private StoreDTO storeDto;
    private List<OptionTableDTO> optionTableDto;
    private List<VariantDTO> variantDTOList;
    private VariantDTO variantDto;


}
