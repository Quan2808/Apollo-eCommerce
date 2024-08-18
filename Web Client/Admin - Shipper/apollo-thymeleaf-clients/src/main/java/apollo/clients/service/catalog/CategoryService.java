package apollo.clients.service.catalog;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
public class CategoryService {

    private static final String API_BASE_URL = "http://localhost:9999/api/category";

    private final RestTemplate restTemplate;

    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getAllCategories() {
        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                API_BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });
        return responseEntity.getBody();
    }

    public Map<String, Object> getCategoryById(int id) {
        return restTemplate.getForObject(API_BASE_URL + "/{id}", Map.class, id);
    }

    public Map<String, Object> createCategory(Map<String, Object> categoryDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(categoryDTO, headers);
        return restTemplate.postForObject(API_BASE_URL, request, Map.class);
    }

    public Map<String, Object> updateCategory(int id, Map<String, Object> categoryDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(categoryDTO, headers);
        return restTemplate.exchange(API_BASE_URL + "/{id}", HttpMethod.POST, request, Map.class, id).getBody();
    }

    public void deleteCategory(int id) {
        restTemplate.delete(API_BASE_URL + "/{id}", id);
    }
}
