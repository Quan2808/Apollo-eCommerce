package com.apollo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipperDTO {
    private Long id;
    private String shipperName;
    private String email;
}
