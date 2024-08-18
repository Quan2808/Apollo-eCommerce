package com.apollo.payload.request;

import com.apollo.entity.StoreCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddStoreCateRequest {
    private Long id;
    private String name;
    private MultipartFile heroImage;
    private MultipartFile squareImage;
    private StoreCategory parentStoreCategory;
    private Long storeId;
}

