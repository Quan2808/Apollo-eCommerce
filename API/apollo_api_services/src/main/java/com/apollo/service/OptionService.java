package com.apollo.service;

import com.apollo.dto.OptionTableDTO;

import java.util.List;

public interface OptionService {
    List<OptionTableDTO> createOption (List<OptionTableDTO> optionDto, Long product_id);
}
