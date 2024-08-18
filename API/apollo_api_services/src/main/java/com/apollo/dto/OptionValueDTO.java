package com.apollo.dto;

import com.apollo.entity.OptionValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionValueDTO {
    private Long id;
    private String value;

//    public OptionValueDTO(OptionValue optionValue) {
//        this.id = optionValue.getId();
//        this.value = optionValue.getValue();
//    }

}

