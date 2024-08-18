package apollo.clients.service.catalog;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import apollo.clients.dto.catalog.VariantDTO;

import java.util.List;
import java.util.Map;

@Service
public class VariantService {

    private final String API_BASE_URL = "http://localhost:9999/api/variant";

    private final RestTemplate restTemplate;

    public VariantService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getVariantsByProductId(int productId) {
        String url = API_BASE_URL + "/product/" + productId;
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });
        return response.getBody();
    }

    public VariantDTO createVariantForProduct(int productId, VariantDTO variantDTO) {
        String url = API_BASE_URL + "/product/" + productId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VariantDTO> request = new HttpEntity<>(variantDTO, headers);

        ResponseEntity<VariantDTO> response = restTemplate.postForEntity(url, request, VariantDTO.class);
        return response.getBody();
    }
}
