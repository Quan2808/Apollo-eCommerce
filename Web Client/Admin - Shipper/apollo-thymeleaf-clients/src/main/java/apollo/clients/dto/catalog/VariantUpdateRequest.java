package apollo.clients.dto.catalog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantUpdateRequest {
    private Long variantId;
    private String name;
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private double price;
    private double salePrice;
    private String img;

}