package apollo.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStoreRequest {
    private String name;
    private MultipartFile homeImage;
    private MultipartFile dealsImage;
    private MultipartFile dealsSquareImage;
    private MultipartFile interactiveImage;
    private MultipartFile logo;
}
