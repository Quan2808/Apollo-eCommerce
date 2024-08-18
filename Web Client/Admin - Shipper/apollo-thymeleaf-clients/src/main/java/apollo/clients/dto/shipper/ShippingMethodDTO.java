package apollo.clients.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShippingMethodDTO {
    private Long id;
    private String name;
    private Double price;
}
