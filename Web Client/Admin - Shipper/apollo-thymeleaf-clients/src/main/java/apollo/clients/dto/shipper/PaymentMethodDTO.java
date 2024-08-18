package apollo.clients.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodDTO {
    private int id;
    private String name;
    private int cartNumber;
    private boolean defaultPayment;
}
