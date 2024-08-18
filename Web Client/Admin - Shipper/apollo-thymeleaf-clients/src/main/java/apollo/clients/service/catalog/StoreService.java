package apollo.clients.service.catalog;

import apollo.clients.dto.catalog.StoreDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
public class StoreService {

  private final String API_BASE_URL = "http://localhost:9999/api/stores";

  private final RestTemplate restTemplate;

  public StoreService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<StoreDTO> getAllStores() {
    ResponseEntity<List<StoreDTO>> responseEntity = restTemplate.exchange(
            API_BASE_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<StoreDTO>>() {}
    );
    return responseEntity.getBody();
  }

  public StoreDTO getStoreById(int id) {
    return restTemplate.getForObject(API_BASE_URL + "/{id}", StoreDTO.class, id);
  }

  public Map<String, Object> createStore(Long adminId, StoreDTO storeDto, MultipartFile dealsImage, MultipartFile homeImage, MultipartFile dealsSquareImage, MultipartFile interactiveImage, MultipartFile logo) {
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("name", storeDto.getName());
    body.add("dealsImage", dealsImage.getResource());
    body.add("homeImage", homeImage.getResource());
    body.add("dealsSquareImage", dealsSquareImage.getResource());
    body.add("interactiveImage", interactiveImage.getResource());
    body.add("logo", logo.getResource());
    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body);
    return restTemplate.postForObject(API_BASE_URL + "/create/" + adminId, request, Map.class);
  }

  public void deleteStore(int id) {
    restTemplate.delete(API_BASE_URL + "/{id}", id);
  }

}