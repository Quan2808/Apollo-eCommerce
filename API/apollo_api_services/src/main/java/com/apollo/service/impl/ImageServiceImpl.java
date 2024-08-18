package com.apollo.service.impl;

import com.apollo.converter.impl.ImageConverterImpl;
import com.apollo.dto.ImageDTO;
import com.apollo.entity.Image;
import com.apollo.entity.Variant;
import com.apollo.repository.ImageRepository;
import com.apollo.repository.VariantRepository;
import com.apollo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${image.VariantImageList.directory}")
    private String IMAGE_DIRECTORY;

    private final ImageRepository imageRepository;
    private final ImageConverterImpl imageConverterImpl;
    private final VariantRepository variantRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ImageConverterImpl imageConverterImpl, VariantRepository variantRepository) {
        this.imageRepository = imageRepository;
        this.imageConverterImpl = imageConverterImpl;
        this.variantRepository = variantRepository;
    }

    @Override
    public List<ImageDTO> getImageByVariantId(Long variant_id) {
        List<Image> images = imageRepository.findImagesByVariant_Id(variant_id);
        return imageConverterImpl.entitiesToDTOs(images);
    }

    @Override
    public List<Image> createImage(List<ImageDTO> imageDtoList, Long variantId) {
        Variant variant = variantRepository.findById(variantId).orElse(null);
        if (variant == null) {
            throw new IllegalArgumentException("Variant not found with ID: " + variantId);
        }
        List<Image> images = imageConverterImpl.dtosToEntities(imageDtoList);
        for (Image element : images) {
            element.setVariant(variant);
            imageRepository.save(element);
        }
        return variant.getImages();
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get(IMAGE_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:9999/api/variant/image-list/" + fileName;
        }
        return null;
    }
}
