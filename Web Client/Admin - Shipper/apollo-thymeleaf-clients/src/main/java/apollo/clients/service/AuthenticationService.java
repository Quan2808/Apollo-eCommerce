package apollo.clients.service;

import apollo.clients.dto.auth.AccountDTO;
import apollo.clients.dto.auth.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private static final String API_BASE_URL = "http://localhost:9999/api";

    private final RestTemplate restTemplate;

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> loginAdmin(String email, String password) {
        String url = API_BASE_URL + "/login/admin";
        AccountDTO adminLoginDto = new AccountDTO(email, password);
        ResponseEntity<TokenDTO> response = restTemplate.postForEntity(url, adminLoginDto, TokenDTO.class);

        Map<String, Object> result = new HashMap<>();
        result.put("token", response.getBody());
        return result;
    }

    public Map<String, Object> loginShipper(String email, String password) {
        String url = API_BASE_URL + "/login/shipper";
        AccountDTO shipperLoginDto = new AccountDTO(email, password);
        ResponseEntity<TokenDTO> response = restTemplate.postForEntity(url, shipperLoginDto, TokenDTO.class);

        Map<String, Object> result = new HashMap<>();
        result.put("token", response.getBody());
        return result;
    }

    public Map<String, Object> registerAdmin(String email, String password, String fullName) {
        String url = API_BASE_URL + "/register/admin";
        AccountDTO adminRegisterDto = new AccountDTO(email, password, fullName);
        ResponseEntity<AccountDTO> response = restTemplate.postForEntity(url, adminRegisterDto, AccountDTO.class);
        Map<String, Object> result = new HashMap<>();
        result.put("admin", response.getBody());
        return result;
    }
}
