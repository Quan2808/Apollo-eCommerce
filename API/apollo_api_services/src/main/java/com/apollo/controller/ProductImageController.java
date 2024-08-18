package com.apollo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/product")
public class ProductImageController {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageController.class);

    @Value("${image.product.directory}")
    private String imageProductDirectory;

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        logger.debug("Request to serve product image: {}", filename);
        try {
            // Tạo đường dẫn tới file
            Path file = Paths.get(imageProductDirectory).resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            // Kiểm tra sự tồn tại và khả năng đọc của file
            if (resource.exists() && resource.isReadable()) {
                // Lấy loại nội dung của file
                String contentType = Files.probeContentType(file);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                logger.debug("Serving product image: {}", filename);
                // Trả về file dưới dạng HTTP response
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.warn("Could not read product file: {}", filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Could not read product file: " + filename, e);
            return ResponseEntity.status(500).build();
        }
    }
}
