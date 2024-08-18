package com.apollo.converter;

import com.apollo.dto.ShipperDTO;
import com.apollo.dto.ShipperLoginDTO;
import com.apollo.entity.Shipper;

import java.util.List;

public interface ShipperConverter {
    ShipperDTO convertEntityToDTO(Shipper shipper);
    List<ShipperLoginDTO> convertEntitiesToDTOs(List<Shipper> shippers);
    List<ShipperDTO> entitiesToDTOs(List<Shipper> element);
    ShipperDTO entityToDTO(Shipper element);

}
