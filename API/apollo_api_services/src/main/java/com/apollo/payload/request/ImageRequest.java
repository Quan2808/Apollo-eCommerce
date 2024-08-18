package com.apollo.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class ImageRequest {
    private List<String> imagePath;

}
