package com.apollo.service.impl;

import com.apollo.converter.*;
import com.apollo.converter.impl.ProductConverterImpl;
import com.apollo.dto.*;
import com.apollo.entity.*;
import com.apollo.payload.response.ProductResponse;
import com.apollo.repository.CategoryRepository;
import com.apollo.repository.ProductRepository;
import com.apollo.repository.StoreCategoryRepository;
import com.apollo.repository.StoreRepository;
import com.apollo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductConverterImpl productConverterImpl;
    @Autowired
    private VariantConverter variantConverter;
    @Autowired
    private StoreConverter storeConverter;
    @Autowired
    private OptionValueConverter optionValueConverter;
    @Autowired
    private OptionTableConverter optionTableConverter;
    @Autowired
    private BulletConverter bulletConverter;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreCategoryRepository storeCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTORY = "src/main/resources/img.product";

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDTO dto = productConverterImpl.entityToDTO(product);
                    dto.setBulletDTOList(bulletConverter.entitiesToDTOs(product.getBulletList()));
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public List<ProductDTO> getAllProductDtos() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
        List<StoreDTO> storeDTOS = new ArrayList<>();
        for (Product product : products) {
            Store store = product.getStore();
            StoreDTO storeDto = storeConverter.entityToDTO(store);
            storeDTOS.add(storeDto);
        }
        for (int i = 0; i < products.toArray().length; i++) {
            productDTOS.get(i).setStore(storeDTOS.get(i));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductDtosByStore(Long id) {
        Store store = storeRepository.findById(id).orElse(null);
        List<Product> products = productRepository.findAllByStore(store);
        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
        List<StoreDTO> storeDTOS = new ArrayList<>();
        for (Product product : products) {
            Store productStore = product.getStore();
            StoreDTO storeDto = storeConverter.entityToDTO(store);
            storeDTOS.add(storeDto);
        }
        for (int i = 0; i < products.toArray().length; i++) {
            productDTOS.get(i).setStore(storeDTOS.get(i));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductDtosByStoreCategory(String categoryName) {
        StoreCategory storeCategory = storeCategoryRepository.findByName(categoryName);
        List<StoreCategory> subCategories = storeCategoryRepository.findAllByParentStoreCategory(storeCategory);
        List<Product> products = new ArrayList<>();
        for (StoreCategory category : subCategories) {
            List<Product> categoryProducts = productRepository.findAllByStoreCategory(category);
            for (Product product : categoryProducts) {
                products.add(product);
            }
        }
        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductDtosByStoreSubCategory(String categoryName) {
        StoreCategory storeCategory = storeCategoryRepository.findByName(categoryName);
        List<Product> products = productRepository.findAllByStoreCategory(storeCategory);
        List<ProductDTO> productDTOS = productConverterImpl.entitiesToDTOs(products);
        return productDTOS;
    }

    @Override
    public List<VariantDTO> getVariantsByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElse(new Product());
        List<Variant> variantList = product.getVariants();
        return variantConverter.entitiesToDTOs(variantList);
    }

    @Override
    public StoreDTO getStoreByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElse(new Product());
        Store store = product.getStore();
        StoreDTO storeDto = storeConverter.entityToDTO(store);
        return storeDto;
    }

    @Override
    public List<OptionTableDTO> getOptionsByProductId(Long productId) {
        List<OptionTableDTO> optionTableDTOList = new ArrayList<>();
        Product product = productRepository.findById(productId).orElse(new Product());
        List<OptionTable> optionTableList = product.getOptionTables();
        for (OptionTable optionTable : optionTableList) {
            List<OptionValue> optionValueList = optionTable.getOptionValues();
            List<OptionValueDTO> optionValueDTOList = optionValueConverter.entitiesToDTOs(optionValueList);
            OptionTableDTO optionTableDto = optionTableConverter.entityToDTO(optionTable);
            optionTableDto.setOptionValueDTOList(optionValueDTOList);
            optionTableDTOList.add(optionTableDto);
        }
        return optionTableDTOList;
    }

    @Override
    public List<ProductDTO> getProductsByContaining(String text) {
        List<Product> products = productRepository.findByTitleContaining(text);
        return productConverterImpl.entitiesToDTOs(products);

    }

    @Override
    public List<ProductDTO> getProductsOfStoreByContaining(Long id, String text) {
        List<Product> products = productRepository.findByTitleContaining(text);
        return productConverterImpl.entitiesToDTOs(products);
    }

    @Override
    public Product createProduct(Long storeId, Long categoryId, Long storeCategoryId, ProductDTO productDto, MultipartFile file) throws IOException {
        // Lưu file và lấy URL
        String fileUrl = saveFile(file);

        // Set URL cho mainPicture
        productDto.setMainPicture(fileUrl);

        Store store = storeRepository.findById(storeId).orElse(new Store());
        Category category = categoryRepository.findById(categoryId).orElse(new Category());
        StoreCategory storeCategory = storeCategoryRepository.findById(storeCategoryId).orElse(new StoreCategory());

        Product product = productConverterImpl.dtoToEntity(productDto);
        product.setStore(store);
        product.setCategory(category);
        product.setStoreCategory(storeCategory);

        return productRepository.save(product);
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path path = Paths.get(IMAGE_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:9999/api/product/images/" + fileName;
        }
        return null;
    }

    // Implementing updateProduct with ProductDTO parameter
    @Override
    public Product updateProduct(ProductDTO productDto) {
        // Fetch existing product entity
        Product existingProduct = productRepository.findById(productDto.getId()).orElse(null);
        if (existingProduct == null) {
            return null; // Product not found
        }

        // Update product fields from DTO
        existingProduct.setTitle(productDto.getTitle());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setMainPicture(productDto.getMainPicture());
        existingProduct.setStatus(productDto.getStatus());
        existingProduct.setCreateAt(productDto.getCreateAt());
        existingProduct.setUpdatedAt(productDto.getUpdatedAt());

        // Save the updated product entity
        return productRepository.save(existingProduct);
    }

    // Implementing updateProduct with individual parameters
    @Override
    public ProductResponse updateProduct(Long productId, String title, String description, MultipartFile file) {
        try {
            // Fetch existing product entity
            Product existingProduct = productRepository.findById(productId).orElse(null);
            if (existingProduct == null) {
                return null; // Product not found
            }

            // Update product fields
            existingProduct.setTitle(title);
            existingProduct.setDescription(description);
            existingProduct.setUpdatedAt(Calendar.getInstance().getTime());

            // If there is a new file, save it and update the main picture URL
            if (file != null && !file.isEmpty()) {
                String fileUrl = saveFile(file);
                existingProduct.setMainPicture(fileUrl);
            }

            // Save the updated product entity
            Product updatedProduct = productRepository.save(existingProduct);

            // Convert updated entity to DTO and return in response
            ProductDTO updatedProductDTO = productConverterImpl.entityToDTO(updatedProduct);
            return ProductResponse.builder()
                    .productDto(updatedProductDTO)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to update product with ID " + productId, e);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // Kiểm tra xem optionId có thuộc productId không
    public boolean isOptionIdBelongsToProductId(Long optionId, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return false;
        }
        return product.getOptionTables().stream()
                .anyMatch(optionTable -> optionTable.getId().equals(optionId));
    }

}