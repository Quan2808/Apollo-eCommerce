package com.apollo.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateVariantRequest {
    private List<Long> valueIdList;
    private Long product_id;
}
