package com.apollo.dto;

import com.apollo.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String imgPath;

//    public ImageDTO(Image image){
//        this.id = image.getId();
//        this.imgPath = image.getImgPath();
//    }
}
