package com.apollo.converter;

import com.apollo.dto.SaveForLaterDTO;
import com.apollo.entity.SaveForLater;

import java.util.List;

public interface SaveForLaterConverter {
    SaveForLater convertDtoToEntity(SaveForLaterDTO saveForLaterDto);
    SaveForLaterDTO convertEntityToDto(SaveForLater saveForLater);
    List<SaveForLaterDTO> convertEntitiesToDtos(List<SaveForLater> saveForLaters);
    List<SaveForLater> convertDtoToEntities(List<SaveForLaterDTO> cartLineDtos);
}
