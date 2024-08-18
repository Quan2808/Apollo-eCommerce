package com.apollo.service;

import com.apollo.dto.ImageDTO;
import com.apollo.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    public List<ImageDTO> getImageByVariantId(Long variant_id);

    public List<Image> createImage(List<ImageDTO> imageDtos, Long variant_id);
}