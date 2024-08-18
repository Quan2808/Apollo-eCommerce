package com.apollo.converter;

import com.apollo.dto.ReviewDTO;
import com.apollo.entity.Review;

import java.util.List;

public interface ReviewConverter {
    List<ReviewDTO> entitiesToDTOs(List<Review> element);
    ReviewDTO entityToDTO(Review element);
    Review dtoToEntity(ReviewDTO element);
}
