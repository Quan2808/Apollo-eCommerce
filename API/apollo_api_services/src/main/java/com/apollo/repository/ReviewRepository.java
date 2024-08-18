package com.apollo.repository;

import com.apollo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Review> findReviewsByVariantIds(Long variantId);

    List<Review> findByVariant_Id(Long id);

}
