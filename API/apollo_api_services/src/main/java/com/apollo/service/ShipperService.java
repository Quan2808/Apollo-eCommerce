package com.apollo.service;

import com.apollo.dto.JwtResponse;
import com.apollo.dto.ShipperLoginDTO;
import com.apollo.dto.ShipperRegisterDTO;
import com.apollo.entity.Shipper;
import com.apollo.entity.VerificationToken;

import javax.servlet.http.HttpServletResponse;

public interface ShipperService {
    JwtResponse loginShipper(ShipperLoginDTO shipperLoginDTO);

    Shipper register(ShipperRegisterDTO shipperRegisterDTO);

    Shipper findById(Long id);

    void createVerificationToken(Shipper shipper);

    boolean checkExpiryDate(VerificationToken verificationToken);

    void verifyToken(VerificationToken verificationToken, HttpServletResponse response) throws Exception;
}
