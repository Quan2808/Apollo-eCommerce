package apollo.clients.request;

import apollo.clients.dto.catalog.StoreCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStoreCateRequest {
    private String name;
    private MultipartFile heroImage;
    private MultipartFile squareImage;
    private StoreCategoryDTO parentStoreCategory;
    private Long storeId;
}
