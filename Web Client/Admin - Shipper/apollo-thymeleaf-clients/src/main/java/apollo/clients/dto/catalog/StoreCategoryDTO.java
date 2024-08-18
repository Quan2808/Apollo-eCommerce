package apollo.clients.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StoreCategoryDTO {
  private Long id;
  private String name;
  private String heroImage;
  private String squareImage;
  private StoreCategoryDTO parentStoreCategory;
  private Long storeId;
}
