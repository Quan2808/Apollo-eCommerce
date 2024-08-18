package com.apollo.payload.response;

import com.apollo.dto.ImageDTO;
import com.apollo.dto.OptionValueDTO;
import com.apollo.dto.VariantDTO;
import com.apollo.dto.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantDetailResponse {
    private List<ImageDTO> imageDTOS;
    private List<VideoDTO> videoDTOS;
    private List<OptionValueDTO> optionValueDTOS;
    private VariantDTO variantDto;
}
