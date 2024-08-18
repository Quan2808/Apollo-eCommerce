package com.apollo.repository;

import com.apollo.entity.Product;
import com.apollo.entity.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VariantRepository  extends JpaRepository<Variant,Long > {
    List<Variant> findByProduct_Id(Long id);

    List<Variant> findVariantsByProductId(Long product_id);

    Variant findTopByProductIdOrderByPriceAsc(Long product_id);
    
    Page<Variant> findByNameContaining(String text, Pageable pageable);

    Page<Variant>findVariantsByNameContainingAndPriceBetween(String text,double minPrice, double maxPrice, Pageable pageable );
    
    @Query("SELECT AVG(r.star) FROM Review r WHERE r.variant = :variant")
    Double findAverageStarByReview(@Param("variant") Variant variant);

    @Query("SELECT v.product.createAt FROM Variant v WHERE v.id = :variantId")
    Date findCreatedAtByVariantId(@Param("variantId") Long variantId);

    List<Variant> findByProductId(Long productId);
}
