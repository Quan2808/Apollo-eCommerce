package apollo.clients.dto.catalog;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OptionTableDTO {
    private Long id;
    private String name;
    private List<OptionValueDTO> optionValueDTOList;
}

