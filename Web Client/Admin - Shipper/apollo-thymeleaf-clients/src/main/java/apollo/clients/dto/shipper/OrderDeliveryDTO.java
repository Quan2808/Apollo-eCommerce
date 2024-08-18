package apollo.clients.dto.shipper;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDeliveryDTO {
    private Long id;
    private String status;
    private Date assignedDate;
    private String inducement;
    private ShopOrderDTO order;
}
