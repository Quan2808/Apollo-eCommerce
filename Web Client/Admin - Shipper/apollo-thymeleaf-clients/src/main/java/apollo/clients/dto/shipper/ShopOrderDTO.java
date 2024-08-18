package apollo.clients.dto.shipper;

import apollo.clients.dto.catalog.VariantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopOrderDTO {
    private Long id;
    private String orderDate;
    private PaymentMethodDTO paymentMethod;
    private ShippingMethodDTO shippingMethod;
    private UserDTO user;
    private AddressDTO address;
    private VariantDTO variant;
    private int quantity;
    private double orderTotal;
    private String deliveryDate;
}

