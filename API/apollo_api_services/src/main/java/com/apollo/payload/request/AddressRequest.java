package com.apollo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    Long id;
    Long userId;
    String district;
    String ward;
    String city;
    String street;
    boolean defaultAddress;
}
