package com.apollo.payload.response;

import com.apollo.dto.OptionTableDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionCreateResponse {
    private List<OptionTableDTO> optionTableDtoList;
    private String message;
}
