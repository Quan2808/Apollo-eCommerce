package apollo.clients.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDTO {
    private Long id;
    private String district;
    private String ward;
    private String city;
    private String street;

}
