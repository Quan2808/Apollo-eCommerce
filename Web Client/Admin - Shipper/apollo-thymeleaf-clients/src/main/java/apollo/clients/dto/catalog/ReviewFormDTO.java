package apollo.clients.dto.catalog;

import apollo.clients.dto.shipper.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFormDTO {
    private Long id;
    private int star;
    private String title;
    private String content;
    private String image01;
    private String image02;
    private String image03;
    private String image04;
    private String image05;
    private String video;
    private boolean verified_admin;
    private Date update_at;
    private UserDTO userDto;
    private List<OptionValueDTO> optionValueDTOList;

}

