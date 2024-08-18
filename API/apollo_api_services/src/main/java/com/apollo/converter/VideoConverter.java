package com.apollo.converter;

import com.apollo.dto.VideoDTO;
import com.apollo.entity.Video;

import java.util.List;

public interface VideoConverter {
    List<VideoDTO> entitiesToDTOs(List<Video> element);
    VideoDTO entityToDTO(Video element);
    Video dtoToEntity(VideoDTO element);

}
