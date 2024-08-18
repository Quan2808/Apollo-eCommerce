package com.apollo.converter;

import com.apollo.dto.StoreDTO;
import com.apollo.entity.Store;

import java.util.List;

public interface StoreConverter {
    List<StoreDTO> entitiesToDTOs(List<Store> element);
    StoreDTO entityToDTO(Store element);
    Store dtoToEntity(StoreDTO element);
}
