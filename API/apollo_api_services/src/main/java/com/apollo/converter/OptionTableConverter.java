package com.apollo.converter;

import com.apollo.dto.OptionTableDTO;
import com.apollo.entity.OptionTable;

import java.util.List;

public interface OptionTableConverter {
    OptionTableDTO entityToDTO(OptionTable element);

    List<OptionTableDTO> entitiesToDTOs(List<OptionTable> element);

    OptionTable dtoToEntity(OptionTableDTO element);
     List<OptionTable> dtoToEntities(List<OptionTableDTO> element);
}
