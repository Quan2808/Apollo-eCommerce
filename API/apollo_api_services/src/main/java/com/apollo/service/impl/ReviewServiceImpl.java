package com.apollo.service.impl;

import com.apollo.converter.UserConverter;
import com.apollo.converter.impl.OptionValueConverterImpl;
import com.apollo.converter.impl.ReviewConverterImpl;
import com.apollo.dto.*;
import com.apollo.entity.OptionValue;
import com.apollo.entity.Review;
import com.apollo.entity.User;
import com.apollo.entity.Variant;
import com.apollo.payload.request.ReviewRequest;
import com.apollo.repository.ReviewRepository;
import com.apollo.repository.UserRepository;
import com.apollo.repository.VariantRepository;
import com.apollo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewConverterImpl reviewConverter;
    private final VariantRepository variantRepository;
    private final OptionValueConverterImpl optionValueConverter;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewConverterImpl reviewConverter,
                             VariantRepository variantRepository,
                             OptionValueConverterImpl optionValueConverter,
                             UserConverter userConverter,
                             UserRepository userRepository) {

        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
        this.variantRepository = variantRepository;
        this.optionValueConverter = optionValueConverter;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        ReviewDTO reviewDto;
        Review review = reviewRepository.findById(id).orElse(new Review());
        reviewDto = reviewConverter.entityToDTO(review);
        return reviewDto;
    }

    @Override
    public List<ReviewDTO> getReviewsByVariantId(Long variantId) {
        List<Review> reviewList = reviewRepository.findByVariant_Id(variantId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for( Review element : reviewList){
            User user = element.getCustomer();
            UserDTO userDTO = userConverter.entityToDTO(user);
            ReviewDTO reviewDTO = reviewConverter.entityToDTO(element);
            reviewDTO.setUserDto(userDTO);
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId){
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        List<Variant> variants = variantRepository.findVariantsByProductId(productId);
        for(Variant variant : variants){
            List<OptionValue> optionValues = variant.getOptionValues();
            List<Review> reviews = variant.getReviews();
            List<OptionValueDTO> optionValueDTOS = optionValueConverter.entitiesToDTOs(optionValues);
            List<ReviewDTO> reviewDTOS = reviewConverter.entitiesToDTOs(reviews);
            if(reviewDTOS != null) {
                for (ReviewDTO reviewDto : reviewDTOS) {
                    reviewDto.setOptionValueDTOList(optionValueDTOS);
                }
            }
            assert reviewDTOS != null;
            reviewDTOList.addAll(reviewDTOS);
        }
        return reviewDTOList;
    }

    @Override
    public SummaryDTO getSummaryDtoByProductId(Long productId) {
        SummaryDTO summaryDto = new SummaryDTO();
        int total = getReviewsByProductId(productId).size();

        int count = 0;
        int countFiveStar = 0;
        int countFourStar = 0;
        int countThreeStar = 0;
        int countTwoStar = 0;
        int countOneStar = 0;
        List<Variant> variants = variantRepository.findVariantsByProductId(productId);
        for (Variant variant: variants) {
            List<Review> reviews = variant.getReviews();
            for (Review review: reviews) {
                if (review.getStar()==5) {
                    countFiveStar++;
                    count += 5;
                } else if (review.getStar()==4) {
                    countFourStar++;
                    count += 4;
                } else if (review.getStar()==3) {
                    countThreeStar++;
                    count += 3;
                } else if (review.getStar()==2) {
                    countTwoStar++;
                    count+=2;
                } else if (review.getStar()==1) {
                    countOneStar++;
                    count+=1;
                }
            }
        }
        if(total > 0){
            RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
            ratingBreakdownDTO.setFiveStar(new RatingDTO(((double)countFiveStar/total*100),countFiveStar));
            ratingBreakdownDTO.setFourStar(new RatingDTO(((double)countFourStar/total*100),countFourStar));
            ratingBreakdownDTO.setThreeStar(new RatingDTO(((double)countThreeStar/total*100),countThreeStar));
            ratingBreakdownDTO.setTwoStar(new RatingDTO(((double)countTwoStar/total*100),countTwoStar));
            ratingBreakdownDTO.setOneStar(new RatingDTO(((double)countOneStar/total*100),countOneStar));
            summaryDto.setRating((double) count /total);
            summaryDto.setReviewsTotal(total);
            summaryDto.setRatingBreakdown(ratingBreakdownDTO);
        }
        return summaryDto;
    }

    @Override
    public ReviewDTO save(ReviewRequest reviewRequest, Long variantId, Long userId) {
        Review review = Review.builder()
                .variant(variantRepository.findById(variantId)
                        .orElseThrow(() -> new EntityNotFoundException("variant not found")))
                .customer(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found")))
                .star(reviewRequest.getStar())
                .title(reviewRequest.getTitle())
                .content(reviewRequest.getContent())
                .create_at(new Date())
                .update_at(new Date())
                .verified_shop(true)
                .verified_admin(true)
                .build();
        Review review1 = reviewRepository.save(review);
        User user = review1.getCustomer();
        UserDTO userDTO = userConverter.entityToDTO(user);
        ReviewDTO reviewDTO = reviewConverter.entityToDTO(review1);
        reviewDTO.setUserDto(userDTO);
        return reviewDTO;
    }

}