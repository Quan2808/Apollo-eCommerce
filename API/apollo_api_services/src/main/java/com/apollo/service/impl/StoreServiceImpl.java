package com.apollo.service.impl;

import com.apollo.converter.StoreCategoryConverter;
import com.apollo.converter.StoreConverter;
import com.apollo.dto.StoreCategoryDTO;
import com.apollo.dto.StoreDTO;
import com.apollo.entity.Admin;
import com.apollo.entity.Store;
import com.apollo.entity.StoreCategory;
import com.apollo.payload.request.AddStoreRequest;
import com.apollo.repository.AdminRepository;
import com.apollo.repository.StoreRepository;
import com.apollo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
public class StoreServiceImpl implements StoreService {

    @Value("${image.directory}")
    private String imageDirectory;

    private final StoreRepository repo;
    private final StoreConverter convert;
    private final AdminRepository adminRepository;
    private final StoreCategoryConverter storeCategoryConverter;

    @Autowired
    public StoreServiceImpl(StoreRepository repo, StoreConverter convert, AdminRepository adminRepository, StoreCategoryConverter storeCategoryConverter) {
        this.repo = repo;
        this.convert = convert;
        this.adminRepository = adminRepository;
        this.storeCategoryConverter = storeCategoryConverter;
    }

    @Override
    public List<StoreDTO> getAllStores() {
        List<Store> stores = repo.findAll();
        return convert.entitiesToDTOs(stores);
    }

    @Override
    public StoreDTO findStore(Long id) {
        Store store = repo.findById(id).orElse(null);
        if (store == null) {
            throw new EntityNotFoundException("Store not found");
        }
        List<StoreCategory> storeCategories = store.getStoreCategoryList();
        List<StoreCategoryDTO> storeCategoryDTOS = new ArrayList<>();
        for (StoreCategory storeCategory : storeCategories) {
            StoreCategoryDTO storeCategoryDTO = storeCategoryConverter.convertEntityToDTO(storeCategory);
            storeCategoryDTOS.add(storeCategoryDTO);
        }
        StoreDTO storeDto = convert.entityToDTO(store);
        storeDto.setStoreCategoryList(storeCategoryDTOS);
        return storeDto;
    }

    @Override
    public StoreDTO createStore(Long adminId, AddStoreRequest storeDto) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        // Kiểm tra nếu tên nhãn hàng đã tồn tại
        if (repo.existsByName(storeDto.getName())) {
            throw new DuplicateStoreNameException("Store name already exists: " + storeDto.getName());
        }

        Store store = new Store();
        store.setName(storeDto.getName());
        store.setDealsImage(saveImage(storeDto.getDealsImage()));
        store.setHomeImage(saveImage(storeDto.getHomeImage()));
        store.setDealsSquareImage(saveImage(storeDto.getDealsSquareImage()));
        store.setInteractiveImage(saveImage(storeDto.getInteractiveImage()));
        store.setLogo(saveImage(storeDto.getLogo()));
        store.setAdmin(admin);
        repo.save(store);
        return convert.entityToDTO(store);
    }

    // kiểm tra tên bị duplicated
    public static class DuplicateStoreNameException extends RuntimeException {
        public DuplicateStoreNameException(String message) {
            super(message);
        }
    }

    private String saveImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                Path imagePath = Paths.get(imageDirectory);
                if (!Files.exists(imagePath)) {
                    Files.createDirectories(imagePath);
                }
                String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                Path filePath = imagePath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return "http://localhost:9999/api/store/images/" + fileName;
            } catch (IOException e) {
                System.err.println("Error saving image: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to store image", e);
            }
        }
        return null;
    }

    @Override
    public StoreDTO deleteStore(Long id) {
        Store store = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Store not found"));
        repo.delete(store);
        return convert.entityToDTO(store);
    }
}
