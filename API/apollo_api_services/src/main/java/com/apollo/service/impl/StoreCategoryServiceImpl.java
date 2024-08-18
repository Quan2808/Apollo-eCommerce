package com.apollo.service.impl;

import com.apollo.converter.StoreCategoryConverter;
import com.apollo.dto.StoreCategoryDTO;
import com.apollo.entity.Store;
import com.apollo.entity.StoreCategory;
import com.apollo.payload.request.AddStoreCateRequest;
import com.apollo.repository.StoreCategoryRepository;
import com.apollo.repository.StoreRepository;
import com.apollo.service.StoreCategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StoreCategoryServiceImpl implements StoreCategoryService {

    @Value("${image.Store.Category.directory}")
    private String imageStoreCategoryDirectory;

    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreRepository storeRepository;
    private final StoreCategoryConverter storeCategoryConverter;

    public StoreCategoryServiceImpl(StoreCategoryRepository storeCategoryRepository, StoreRepository storeRepository, StoreCategoryConverter storeCategoryConverter) {
        this.storeCategoryRepository = storeCategoryRepository;
        this.storeRepository = storeRepository;
        this.storeCategoryConverter = storeCategoryConverter;
    }

    @Override
    public List<StoreCategoryDTO> getStoreCategories() {
        List<StoreCategory> storeCategories = storeCategoryRepository.findAll();
        return storeCategoryConverter.convertEntitiesToDTOs(storeCategories);
    }

    @Override
    public List<StoreCategoryDTO> getStoreCategoriesByStoreId(Long storeId) {
        List<StoreCategory> storeCategories = storeCategoryRepository.findByStoreId(storeId);
        return storeCategoryConverter.convertEntitiesToDTOs(storeCategories);
    }

    @Override
    public StoreCategoryDTO createOrUpdateStoreCategory(AddStoreCateRequest addStoreCateRequest) {
        Store store = storeRepository.findById(addStoreCateRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        StoreCategory storeCategory = new StoreCategory();
        if (addStoreCateRequest.getId() != null) {
            storeCategory = storeCategoryRepository.findById(addStoreCateRequest.getId())
                    .orElseThrow(() -> new RuntimeException("StoreCategory not found"));
        }

        storeCategory.setName(addStoreCateRequest.getName());
        storeCategory.setStore(store);
        storeCategory.setParentStoreCategory(addStoreCateRequest.getParentStoreCategory());

        try {
            if (addStoreCateRequest.getHeroImage() != null && !addStoreCateRequest.getHeroImage().isEmpty()) {
                String heroImageUrl = saveFile(addStoreCateRequest.getHeroImage());
                storeCategory.setHeroImage(heroImageUrl);
            }
            if (addStoreCateRequest.getSquareImage() != null && !addStoreCateRequest.getSquareImage().isEmpty()) {
                String squareImageUrl = saveFile(addStoreCateRequest.getSquareImage());
                storeCategory.setSquareImage(squareImageUrl);
            }
        } catch (IOException e) {
            System.err.println("Error while saving images: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error while saving images", e);
        }

        StoreCategory savedStoreCategory = storeCategoryRepository.save(storeCategory);
        return storeCategoryConverter.convertEntityToDTO(savedStoreCategory);
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get(imageStoreCategoryDirectory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:9999/api/store-category/images/" + fileName;
        }
        return null;
    }


    public void deleteStoreCategory(Long id) {
        storeCategoryRepository.deleteById(id);
    }

    @Override
    public List<StoreCategoryDTO> createStoreCategories(List<String> storeCateList, Long storeId) {
        List<StoreCategoryDTO> storeCategoryDTOS = new ArrayList<>();
        List<StoreCategory> storeCategories = new ArrayList<>();
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found"));
        for (String element : storeCateList) {
            StoreCategoryDTO storeCategoryDTO = getStoreCategoryDTO(element);
            StoreCategory storeCategory = storeCategoryConverter.dtoToEntity(storeCategoryDTO);
            storeCategory.setStore(store);
            storeCategories.add(storeCategoryRepository.save(storeCategory));
        }
        return storeCategoryConverter.convertEntitiesToDTOs(storeCategories);
    }

    private static StoreCategoryDTO getStoreCategoryDTO(String element) {
        StoreCategoryDTO storeCategoryDTO = new StoreCategoryDTO();
        storeCategoryDTO.setHeroImage("https://m.media-amazon.com/images/S/aplus-media-library-service-media/721c2b2d-4528-481e-b722-6b5b44937046.__CR0,0,300,225_PT0_SX300_V1___.jpg");
        storeCategoryDTO.setSquareImage("https://m.media-amazon.com/images/S/stores-image-uploads-na-prod/e/AmazonStores/ATVPDKIKX0DER/946799e337c1bb687ede3fabb0c39d27.w784.h816._CR72%2C206%2C624%2C400_SX200_.png");
        storeCategoryDTO.setName(element);
        return storeCategoryDTO;
    }

    @Override
    public StoreCategoryDTO createStoreCategory(StoreCategoryDTO storeCategoryDTO, Long storeCategoryId) {
        return null;
    }
}
