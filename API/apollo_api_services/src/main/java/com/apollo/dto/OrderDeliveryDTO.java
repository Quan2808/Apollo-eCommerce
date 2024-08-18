package com.apollo.dto;

import com.apollo.entity.Shipper;
import com.apollo.entity.ShopOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryDTO {
    private Long id;
    private String status;
    private Date assignedDate;
    private Shipper shipper;
    private ShopOrder order;
    private String inducement;
}
