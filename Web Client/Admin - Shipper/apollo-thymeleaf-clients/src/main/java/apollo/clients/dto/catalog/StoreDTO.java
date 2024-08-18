package apollo.clients.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreDTO {
  private Long id;

  private String name;

  private String homeImage;

  private String dealsImage;

  private String dealsSquareImage;

  private String interactiveImage;

  private String logo;

  private List<StoreCategoryDTO> storeCategoryList;

}
