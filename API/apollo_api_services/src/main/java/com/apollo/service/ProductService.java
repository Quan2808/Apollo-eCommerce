package com.apollo.service;

import com.apollo.dto.OptionTableDTO;
import com.apollo.dto.ProductDTO;
import com.apollo.dto.StoreDTO;
import com.apollo.dto.VariantDTO;
import com.apollo.entity.Product;
import com.apollo.payload.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public List<ProductDTO> getAllProductDtosByStore(Long id);
    List<ProductDTO> getAllProductDtosByStoreCategory(String categoryName);
    List<ProductDTO> getAllProductDtosByStoreSubCategory(String categoryName);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProductDtos();
    List<VariantDTO> getVariantsByProductId(Long productId);
    StoreDTO getStoreByProductId(Long productId);
    List<OptionTableDTO> getOptionsByProductId(Long productId);
    List<ProductDTO> getProductsByContaining(String text);
    Product createProduct(Long storeId, Long categoryId, Long storeCategoryId, ProductDTO productDto, MultipartFile file) throws IOException;

    List<ProductDTO> getProductsOfStoreByContaining(Long id, String text);
    Product updateProduct(ProductDTO productDto);
    void    deleteProduct(Long productId);

    ProductResponse updateProduct(Long productId, String title, String description, MultipartFile file);
}
