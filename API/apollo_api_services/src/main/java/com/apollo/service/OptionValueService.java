package com.apollo.service;

import com.apollo.dto.OptionValueDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OptionValueService {
    List<OptionValueDTO> getOptionValuesByVariantId(Long variant_id);
    List<OptionValueDTO> createOptionValue(List<OptionValueDTO> optionValueDto, Long product_id);

}
