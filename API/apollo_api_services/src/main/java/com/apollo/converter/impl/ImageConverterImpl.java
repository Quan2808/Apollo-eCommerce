package com.apollo.converter.impl;

import com.apollo.converter.ImageConverter;
import com.apollo.dto.ImageDTO;
import com.apollo.entity.Image;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ImageConverterImpl implements ImageConverter {
    @Override
    public List<ImageDTO> entitiesToDTOs(List<Image> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public ImageDTO entityToDTO(Image element) {
        ImageDTO result = new ImageDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }
    @Override
    public Image dtoToEntity(ImageDTO element) {
        Image result = new Image();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public List<Image> dtosToEntities(List<ImageDTO> element) {
        return element.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
