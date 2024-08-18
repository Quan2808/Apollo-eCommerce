package com.apollo.dto;

import com.apollo.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VideoDTO {
    private Long id;
    private String videoPath;

//    public VideoDTO(Video video) {
//        this.id = video.getId();
//        this.videoPath = video.getVideoPath();
//    }
}
