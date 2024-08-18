package apollo.clients.service.catalog;

import apollo.clients.dto.catalog.StoreCategoryDTO;
import apollo.clients.request.AddStoreCateRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StoreCategoryService {

  private final String API_BASE_URL = "http://localhost:9999/api/store-category";
  private final RestTemplate restTemplate;

  public StoreCategoryService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Map<String, Object>> getAllStoreCategories() {
    ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
            API_BASE_URL, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
    );
    return responseEntity.getBody();
  }

  public List<StoreCategoryDTO> getAllStoreCategoryDTOs() {
    List<Map<String, Object>> categories = getAllStoreCategories();
    return categories.stream().map(category -> {
      StoreCategoryDTO dto = new StoreCategoryDTO();

      // Safely convert ID to Long
      Object idObj = category.get("id");
      if (idObj instanceof Integer) {
        dto.setId(((Integer) idObj).longValue());
      } else if (idObj instanceof Long) {
        dto.setId((Long) idObj);
      } else {
        throw new IllegalArgumentException("Unexpected type for id: " + idObj.getClass().getName());
      }

      dto.setName((String) category.get("name"));

      // Handle storeId conversion if needed
      Object storeIdObj = category.get("storeId");
      if (storeIdObj instanceof Integer) {
        dto.setStoreId(((Integer) storeIdObj).longValue());
      } else if (storeIdObj instanceof Long) {
        dto.setStoreId((Long) storeIdObj);
      } else {
        throw new IllegalArgumentException("Unexpected type for storeId: " + storeIdObj.getClass().getName());
      }

      return dto;
    }).collect(Collectors.toList());
  }

  public List<StoreCategoryDTO> getStoreCategoriesByStoreId(int storeId) {
    ResponseEntity<List<StoreCategoryDTO>> response = restTemplate.exchange(
            API_BASE_URL + "/by-store/" + storeId, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<StoreCategoryDTO>>() {}
    );
    return response.getBody();
  }

  public StoreCategoryDTO createStoreCategory(AddStoreCateRequest addStoreCateRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("name", addStoreCateRequest.getName());
    body.add("heroImage", addStoreCateRequest.getHeroImage().getResource());
    body.add("squareImage", addStoreCateRequest.getSquareImage().getResource());
    body.add("parentStoreCategory", addStoreCateRequest.getParentStoreCategory().getId());
    body.add("storeId", addStoreCateRequest.getStoreId());

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<StoreCategoryDTO> response = restTemplate.exchange(
            UriComponentsBuilder.fromHttpUrl(API_BASE_URL + "/add").toUriString(),
            HttpMethod.POST,
            requestEntity,
            new ParameterizedTypeReference<StoreCategoryDTO>() {}
    );

    return response.getBody();
  }
}
