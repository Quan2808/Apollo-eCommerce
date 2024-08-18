package com.apollo.dto;
import com.apollo.entity.OptionValue;
import com.apollo.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class VariantDTO {
    private Long id;
    private String name;
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private double price;
    private double salePrice;
    private String img;
    private Long productId;
    private Boolean isDeleted;
    private List<OptionValueDTO> optionValueDTOList = new ArrayList<>();
    private List<ImageDTO> imageDTOList = new ArrayList<>();
    private List<VideoDTO> videoDTOList = new ArrayList<>();
    private List<ReviewDTO> reviewDTOList = new ArrayList<>();
    private List<Long> optionValueIds;

    public VariantDTO() {
        // Khởi tạo các danh sách là mảng rỗng để tránh null
        this.optionValueDTOList = new ArrayList<>();
        this.imageDTOList = new ArrayList<>();
        this.reviewDTOList = new ArrayList<>();
    }

    public VariantDTO(Variant variant) {
        this.id = variant.getId();
        this.name = variant.getName();
        this.skuCode = variant.getSkuCode();
        this.stockQuantity = variant.getStockQuantity();
        this.weight = variant.getWeight();
        this.price = variant.getPrice();
        this.salePrice = variant.getSalePrice();
        this.img = variant.getImg();
        this.isDeleted = variant.getIsDeleted();
        this.productId = variant.getProduct().getId();
//        this.optionValueIds = variant.getOptionValues().stream()
//                .map(OptionValue::getId)
//                .collect(Collectors.toList());
//
//        // Ánh xạ các danh sách nếu có dữ liệu
//        if (variant.getOptionValues() != null) {
//            this.optionValueDTOList = variant.getOptionValues().stream()
//                    .map(OptionValueDTO::new)
//                    .collect(Collectors.toList());
//        } else {
//            this.optionValueDTOList = new ArrayList<>();
//        }
//
//        if (variant.getImages() != null) {
//            this.imageDTOList = variant.getImages().stream()
//                    .map(ImageDTO::new)
//                    .collect(Collectors.toList());
//        } else {
//            this.imageDTOList = new ArrayList<>();
//        }
//
//        if (variant.getReviews() != null) {
//            this.reviewDTOList = variant.getReviews().stream()
//                    .map(ReviewDTO::new)
//                    .collect(Collectors.toList());
//        } else {
//            this.reviewDTOList = new ArrayList<>();
//        }
    }
}
