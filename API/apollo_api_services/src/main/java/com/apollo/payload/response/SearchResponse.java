package com.apollo.payload.response;

import com.apollo.dto.VariantDTO;
import lombok.Data;
import org.springframework.data.domain.Page;
@Data
public class SearchResponse {
    private Page<VariantDTO> variantDtoPage;


}
