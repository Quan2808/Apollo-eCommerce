package com.apollo.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddStoreRequest {
    private String name;
    private MultipartFile dealsImage;
    private MultipartFile homeImage;
    private MultipartFile dealsSquareImage;
    private MultipartFile interactiveImage;
    private MultipartFile logo;
}
