package apollo.clients.service.user;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    private final String API_BASE_URL = "http://localhost:9999/api/accounts";

    private final RestTemplate restTemplate;

    public UserService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<?> getAllAccountsType(String type) {
        return restTemplate.getForObject(API_BASE_URL + "/" + type, List.class, type);
    }

    public Object getAccountTypeById(String type, Long id) {
        return restTemplate.getForObject(API_BASE_URL + "/" + type + "/" + id, Object.class, type, id);
    }
}
