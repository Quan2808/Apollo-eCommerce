package com.apollo.payload.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageCreateRequest {
    private Long variantId;
    private List<MultipartFile> files;

    // Getters and Setters
    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}

