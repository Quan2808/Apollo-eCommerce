package com.apollo.converter;

import com.apollo.dto.OptionValueDTO;
import com.apollo.entity.OptionValue;

import java.util.List;

public interface OptionValueConverter {
    List<OptionValueDTO> entitiesToDTOs(List<OptionValue> element);
    OptionValueDTO entityToDTO(OptionValue element);
    OptionValue dtoToEntity(OptionValueDTO element);
    List<OptionValue> dtosToEntities(List<OptionValueDTO> element);
}
