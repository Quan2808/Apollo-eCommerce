package com.apollo.controller.back_office;

import com.apollo.converter.ShipperConverter;
import com.apollo.dto.ShipperDTO;
import com.apollo.entity.Shipper;
import com.apollo.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/shippers")
public class ShipperController {

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShipperConverter shipperConverter;

    @PreAuthorize("hasRole('SHIPPER')")
    @GetMapping("/{shipper_id}")
    public ResponseEntity<ShipperDTO> getUser(@PathVariable("shipper_id") Long shipperId){
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new UsernameNotFoundException("Shipper not found"));
        ShipperDTO shipperDTO = shipperConverter.convertEntityToDTO(shipper);
        return ResponseEntity.ok(shipperDTO);
    }
}
