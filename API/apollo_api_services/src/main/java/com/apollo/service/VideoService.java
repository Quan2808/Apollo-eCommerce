package com.apollo.service;

import com.apollo.dto.VideoDTO;
import com.apollo.entity.Video;

import java.util.List;
public interface VideoService {
     List<VideoDTO> getVideosByVariantId(Long variant_id);
    Video createVideo (VideoDTO videoDto, Long variant_id);
}
