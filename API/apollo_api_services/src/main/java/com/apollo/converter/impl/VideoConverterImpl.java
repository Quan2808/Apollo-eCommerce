package com.apollo.converter.impl;

import com.apollo.converter.VideoConverter;
import com.apollo.dto.VideoDTO;
import com.apollo.entity.Video;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class VideoConverterImpl implements VideoConverter {
    @Override
    public List<VideoDTO> entitiesToDTOs(List<Video> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VideoDTO entityToDTO(Video element) {
        VideoDTO result = new VideoDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public Video dtoToEntity(VideoDTO element) {
        Video result = new Video();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
