package com.apollo.converter;

import com.apollo.dto.VariantDTO;
import com.apollo.entity.Variant;

import java.util.List;

public interface VariantConverter {
    List<VariantDTO> entitiesToDTOs(List<Variant> element);
    VariantDTO entityToDTO(Variant element);
    Variant dtoToEntity(VariantDTO element);
}
