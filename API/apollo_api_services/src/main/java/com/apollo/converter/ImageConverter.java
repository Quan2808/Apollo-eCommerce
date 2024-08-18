package com.apollo.converter;

import com.apollo.dto.ImageDTO;
import com.apollo.entity.Image;

import java.util.List;

public interface ImageConverter {
    ImageDTO entityToDTO(Image element);

    List<ImageDTO> entitiesToDTOs(List<Image> element);

    Image dtoToEntity(ImageDTO element);
    List<Image> dtosToEntities(List<ImageDTO> element);

}
