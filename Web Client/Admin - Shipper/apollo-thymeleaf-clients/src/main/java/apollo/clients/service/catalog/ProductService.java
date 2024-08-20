package apollo.clients.service.catalog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import apollo.clients.dto.catalog.OptionTableDTO;
import apollo.clients.dto.catalog.OptionValueDTO;
import apollo.clients.dto.catalog.ProductDTO;
import apollo.clients.dto.catalog.ReviewFormDTO;
import apollo.clients.dto.catalog.VariantDTO;
import apollo.clients.dto.catalog.VariantUpdateRequest;
import apollo.clients.request.ImageCreateResponse;
import apollo.clients.request.OptionCreateResponse;
import apollo.clients.request.OptionValueCreateResponse;
import apollo.clients.request.ReviewResponse;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final String PRODUCT_DETAIL_API_URL = "http://localhost:9999/api/product-detail";
    private final String PRODUCTS_API_URL = "http://localhost:9999/api/products";
    private final String OPTION_API_URL = "http://localhost:9999/api/option";
    private final String OPTION_VALUE_API_URL = "http://localhost:9999/api/option-value";
    private final String VARIANT_API_URL = "http://localhost:9999/api/variant";
    private final String REVIEWS_API_URL = "http://localhost:9999/api/reviews";
    private final String IMAGE_API_URL = "http://localhost:9999/api/image";
    private final String VARIANT_BY_ID_API_URL = "http://localhost:9999/api/variant/id";
    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch all products
    public List<Map<String, Object>> getProducts() {
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(PRODUCTS_API_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>() {
            });
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching products", e);
            throw new RuntimeException("Failed to fetch products");
        }
    }

    // Fetch a product by ID
    public ProductDTO getProductById(int id) {
        try {
            return restTemplate.getForObject(PRODUCTS_API_URL + "/{id}", ProductDTO.class, id);
        } catch (RestClientException e) {
            logger.error("Error fetching product with ID " + id, e);
            throw new RuntimeException("Failed to fetch product");
        }
    }

    // Method to create a new product with an image upload
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile mainPicturePatch) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        if (mainPicturePatch != null && !mainPicturePatch.isEmpty()) {
            body.add("file", mainPicturePatch.getResource());
        }
        body.add("title", productDTO.getTitle());
        body.add("description", productDTO.getDescription());
        if (productDTO.getStore() != null) {
            body.add("storeId", productDTO.getStore().getId());
        }
        if (productDTO.getCategory() != null) {
            body.add("categoryId", productDTO.getCategory().getId());
        }
        if (productDTO.getStoreCategory() != null) {
            body.add("storeCategoryId", productDTO.getStoreCategory().getId());
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<ProductDTO> response = restTemplate.postForEntity(PRODUCT_DETAIL_API_URL + "/create", requestEntity, ProductDTO.class);
            if (response.getStatusCode() != HttpStatus.CREATED) {
                logger.error("Failed to create product: Status code " + response.getStatusCode());
                throw new RuntimeException("Failed to create product");
            }
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error creating product", e);
            throw new RuntimeException("Failed to create product");
        }
    }

    // Method to update an existing product with an image
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile mainPicturePatch) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        if (mainPicturePatch != null && !mainPicturePatch.isEmpty()) {
            body.add("file", mainPicturePatch.getResource());
        }
        body.add("title", productDTO.getTitle());
        body.add("description", productDTO.getDescription());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<ProductDTO> response = restTemplate.exchange(PRODUCT_DETAIL_API_URL + "/update/{id}", HttpMethod.PUT, requestEntity, ProductDTO.class, id);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to update product: Status code " + response.getStatusCode());
            }
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error updating product with ID " + id, e);
        }
    }

    // Overloaded method to update an existing product without an image
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(productDTO, headers);
        try {
            ResponseEntity<ProductDTO> response = restTemplate.exchange(PRODUCT_DETAIL_API_URL + "/update/{id}", HttpMethod.PUT, requestEntity, ProductDTO.class, id);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to update product: Status code " + response.getStatusCode());
            }
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Error updating product with ID " + id, e);
        }
    }

    // Method to fetch options by product ID
    public List<OptionTableDTO> getOptionsByProductId(Long productId) {
        try {
            ResponseEntity<List<OptionTableDTO>> response = restTemplate.exchange(OPTION_API_URL + "/" + productId, HttpMethod.GET, null, new ParameterizedTypeReference<List<OptionTableDTO>>() {
            });
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching options for product with ID " + productId, e);
            throw new RuntimeException("Failed to fetch options for product");
        }
    }

    // Fetch Option Values by Product ID
    public List<OptionValueDTO> getOptionValuesByProductId(Long productId) {
        try {
            ResponseEntity<List<OptionValueDTO>> response = restTemplate.exchange(OPTION_VALUE_API_URL + "/product/" + productId, HttpMethod.GET, null, new ParameterizedTypeReference<List<OptionValueDTO>>() {
            });
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching option values for product ID " + productId, e);
            throw new RuntimeException("Failed to fetch option values for product");
        }
    }

    public List<OptionTableDTO> createOptions(String optionNames, Long productId) {
        String url = OPTION_API_URL + "/" + productId + "/create";
        List<String> optionNameList = Arrays.asList(optionNames.split(","));
        OptionCreateResponse response = restTemplate.postForObject(url, optionNameList, OptionCreateResponse.class);
        return response.getOptionTableDtoList();
    }

    public List<OptionValueDTO> createOptionValue(List<String> valueRequest, Long optionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(valueRequest, headers);
        try {
            ResponseEntity<OptionValueCreateResponse> response = restTemplate.exchange(OPTION_VALUE_API_URL + "/" + optionId + "/create", HttpMethod.POST, requestEntity, OptionValueCreateResponse.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody().getOptionValueDtoList();
            } else {
                logger.error("Failed to create OptionValue: Status code " + response.getStatusCode());
                throw new RuntimeException("Failed to create OptionValue");
            }
        } catch (RestClientException e) {
            logger.error("Error creating OptionValue", e);
            throw new RuntimeException("Failed to create OptionValue");
        }
    }

    // Fetch Option Values by Option ID
    public List<OptionValueDTO> getOptionValuesByOptionId(Long optionId) {
        try {
            ResponseEntity<List<OptionValueDTO>> response = restTemplate.exchange(OPTION_VALUE_API_URL + "/option/" + optionId, HttpMethod.GET, null, new ParameterizedTypeReference<List<OptionValueDTO>>() {
            });
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching option values for option ID " + optionId, e);
            throw new RuntimeException("Failed to fetch option values");
        }
    }

    // Tạo variant mới dựa trên danh sách option value và product id
    public VariantDTO createRawVariant(List<Long> valueIdList, Long productId) {
        String url = VARIANT_API_URL + "/create";
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("valueIdList", valueIdList);
            requestBody.put("product_id", productId);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody);
            ResponseEntity<VariantDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, VariantDTO.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to create variant", e);
        }
    }

    // Phương thức để chuyển đổi MultipartFile sang ByteArrayResource
    public ByteArrayResource convertToByteArrayResource(MultipartFile file) throws IOException {
        return new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
    }

    // Phương thức cập nhật Variant
    public VariantDTO updateVariant(VariantUpdateRequest updateRequest, MultipartFile imageFile) throws IOException {
        String url = VARIANT_API_URL + "/update";
        try {
            // Sử dụng ByteArrayResource cho file ảnh
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("variantId", updateRequest.getVariantId());
            body.add("name", updateRequest.getName());
            body.add("skuCode", updateRequest.getSkuCode());
            body.add("stockQuantity", updateRequest.getStockQuantity());
            body.add("weight", updateRequest.getWeight());
            body.add("price", updateRequest.getPrice());
            body.add("salePrice", updateRequest.getSalePrice());

            if (imageFile != null && !imageFile.isEmpty()) {
                ByteArrayResource imageResource = convertToByteArrayResource(imageFile);
                body.add("imageFile", imageResource);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<VariantDTO> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, VariantDTO.class);

            return response.getBody();
        } catch (RestClientException | IOException e) {
            logger.error("Failed to update variant with ID: " + updateRequest.getVariantId(), e);
            throw new RuntimeException("Failed to update variant", e);
        }
    }


    // Phương thức lấy Variant bằng ID
    public VariantDTO getVariantByVariantId(Long variantId) {
        try {
            String url = VARIANT_BY_ID_API_URL + "/" + variantId;
            return restTemplate.getForObject(url, VariantDTO.class);
        } catch (RestClientException e) {
            logger.error("Error fetching variant with ID: " + variantId, e);
            throw new RuntimeException("Failed to fetch variant", e);
        }
    }

    // Lấy danh sách variant dựa trên product id
    public List<VariantDTO> getVariantsByProductId(Long productId) {
        try {
            ResponseEntity<List<VariantDTO>> response = restTemplate.exchange(VARIANT_API_URL + "/product/" + productId, HttpMethod.GET, null, new ParameterizedTypeReference<List<VariantDTO>>() {
            });
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching variants for productId: " + productId, e);
            throw new RuntimeException("Failed to fetch variants");
        }
    }

    // phương thức save review
    public void submitReviewToApi(ReviewFormDTO reviewFormDTO, Long variantId, Long userId) {
        String apiUrl = REVIEWS_API_URL + "/" + userId + "/" + variantId;
        restTemplate.postForObject(apiUrl, reviewFormDTO, Void.class);
    }

    // phương thức hiển thị review của product
    public ReviewResponse getReviewsByProductId(Long id) {
        try {
            String url = REVIEWS_API_URL + "/product/" + id;
            return restTemplate.getForObject(url, ReviewResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch reviews", e);
        }
    }


    public ImageCreateResponse uploadImages(MultipartFile[] files, Long variantId) throws IOException {
        String apiUrl = "http://localhost:9999/api/image/create";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (MultipartFile file : files) {
            body.add("files", new FileSystemResource(convert(file)));
        }
        body.add("variantId", variantId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ImageCreateResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                ImageCreateResponse.class
        );

        return response.getBody();
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

