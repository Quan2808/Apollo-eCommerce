package com.apollo.service;

import com.apollo.dto.VariantDTO;
import com.apollo.entity.Product;
import com.apollo.entity.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface VariantService {
    VariantDTO getVariantById(Long id);

    List<VariantDTO> getVariantByProductId(Long product_id);

    Variant findById(Long id);

    VariantDTO getLowestPriceVariantByProductId(Long product_id);

    VariantDTO getVariantByProductPriceMin(Long product_id);

    Variant createVariant(VariantDTO variantDto, Long product_id);

    //VariantDTO updateVariant(Long variantId, VariantDTO variantDto);

    void deleteVariant(Long variantId);

    Page<VariantDTO> getVariantsByContaining(String text, Pageable pageable);

    Page<VariantDTO> getVariantsByNameContainingAndPriceBetween(String text, double minPrice, double maxPrice, Pageable pageable);

    Page<VariantDTO> getVariantsBySearchTextAndRating(String text, long star, Pageable pageable);

    VariantDTO createRawVariant(List<Long> valueIdList, Long productId);

    VariantDTO getVariantInfoById(Long id);

    Page<VariantDTO> getNewestVariantsByText(String text, Pageable pageable);

    List<VariantDTO> getVariantsByProduct(Long productId);

    VariantDTO createVariantForProduct(Long productId, VariantDTO createVariantDTO);
}
