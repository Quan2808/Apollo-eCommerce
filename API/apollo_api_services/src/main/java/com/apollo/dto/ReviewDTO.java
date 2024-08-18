package com.apollo.dto;

import com.apollo.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private int star;
    private String title;
    private String content;
    private String image01;
    private String image02;
    private String image03;
    private String image04;
    private String image05;
    private String video;
    private boolean verified_admin;
    private Date update_at;
    private UserDTO userDto;
    private List<OptionValueDTO> optionValueDTOList;
    private List<CommentDTO> commentDTOList;

//    public ReviewDTO(Review review) {
//        this.id = review.getId();
//        this.star = review.getStar();
//        this.title = review.getTitle();
//        this.content = review.getContent();
//        this.image01 = review.getImage01();
//        this.image02 = review.getImage02();
//        this.image03 = review.getImage03();
//        this.image04 = review.getImage04();
//        this.image05 = review.getImage05();
//        this.video = review.getVideo();
//        this.verified_admin = review.isVerified_admin();
//        this.update_at = review.getUpdate_at();
//    }

}
