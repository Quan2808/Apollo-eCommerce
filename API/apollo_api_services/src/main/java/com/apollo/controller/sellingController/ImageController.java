package com.apollo.controller.sellingController;

import com.apollo.dto.ImageDTO;
import com.apollo.entity.Image;
import com.apollo.payload.request.ImageCreateRequest;
import com.apollo.payload.request.ReviewRequest;
import com.apollo.payload.response.ImageCreateResponse;
import com.apollo.service.ReviewService;
import com.apollo.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/image")
public class ImageController {

    public final ImageServiceImpl imageService;
    public final ReviewService reviewService;

    @Autowired
    public ImageController(ImageServiceImpl imageService, ReviewService reviewService) {
        this.imageService = imageService;
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<ImageCreateResponse> createImage(@RequestParam("files") List<MultipartFile> files, @RequestParam("variantId") Long variantId) {
        ImageCreateResponse imageCreateResponse = new ImageCreateResponse();
        List<ImageDTO> imageDtoList = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>();  // Danh sách để lưu trữ các imageId

        try {
            for (MultipartFile file : files) {
                String imageUrl = imageService.saveFile(file);
                if (imageUrl != null) {
                    ImageDTO imageDto = ImageDTO.builder()
                            .imgPath(imageUrl)
                            .build();
                    imageDtoList.add(imageDto);
                }
            }

            List<Image> images = imageService.createImage(imageDtoList, variantId);

            if (images != null && !images.isEmpty()) {
                for (Image image : images) {
                    imageIds.add(image.getId());  // Thêm từng imageId vào danh sách
                }
                imageCreateResponse.setImageIds(imageIds);
                imageCreateResponse.setMessage("Images created successfully");
                return new ResponseEntity<>(imageCreateResponse, HttpStatus.CREATED);
            } else {
                imageCreateResponse.setMessage("Failed to create Images");
                return new ResponseEntity<>(imageCreateResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            imageCreateResponse.setMessage("Failed to save Images");
            return new ResponseEntity<>(imageCreateResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
