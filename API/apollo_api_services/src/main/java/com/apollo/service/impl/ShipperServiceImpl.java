package com.apollo.service.impl;

import com.apollo.configuration.JwtTokenUtil;
import com.apollo.constraint.Role;
import com.apollo.dto.JwtResponse;
import com.apollo.dto.ShipperLoginDTO;
import com.apollo.dto.ShipperRegisterDTO;
import com.apollo.entity.Shipper;
import com.apollo.entity.VerificationToken;
import com.apollo.exception.RedirectException;
import com.apollo.repository.ShipperRepository;
import com.apollo.repository.VerificationTokenRepository;
import com.apollo.security.JwtUserDetailsService;
import com.apollo.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service("shipperService")
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Override
    public JwtResponse loginShipper(ShipperLoginDTO shipperLoginDTO) {
        Shipper shipper = shipperRepository.findByEmail(shipperLoginDTO.getEmail());
        if (shipper == null) {
            throw new UsernameNotFoundException("Shipper not found");
        } else if (!shipper.isEnabled()) {
            throw new UsernameNotFoundException("Shipper has not been confirmed");
        }
        if (!passwordEncoder.matches(shipperLoginDTO.getPassword(), shipper.getPassword())) {
            throw new AuthenticationServiceException("Wrong password");
        }

        String accessToken = jwtTokenUtil.generateShipperToken(shipper);
        String refreshToken = jwtTokenUtil.generateShipperRefreshToken(shipper);

        return new JwtResponse(accessToken, refreshToken);
    }


    @Override
    @Transactional
    public Shipper register(ShipperRegisterDTO shipperRegisterDto) {
        // Check if the shipper already exists
        Shipper existingShipper = shipperRepository.findByEmail(shipperRegisterDto.getEmail());
        if (existingShipper != null) {
            throw new UsernameNotFoundException("Email has already been registered");
        }

        // Proceed with registration if no existing shipper is found
        String password = shipperRegisterDto.getPassword();
        Shipper shipper = new Shipper();
        shipper.setShipperName(shipperRegisterDto.getShipperName());
        shipper.setEmail(shipperRegisterDto.getEmail());
        shipper.setPassword(passwordEncoder.encode(password));
        shipper.setPhoneNumber(shipperRegisterDto.getPhoneNumber());
        shipper.setRole("ROLE_".concat(Role.SHIPPER.toString()));
        shipper.setEnabled(false); // Will be enabled after email verification
        shipperRepository.save(shipper);

        // Create and save the verification token
        VerificationToken verificationToken = new VerificationToken(shipper); // Using the new constructor
        verificationTokenRepository.save(verificationToken);

        // Send the confirmation email
        emailServiceImpl.sendConfirmEmailToShipper(shipper, verificationToken.getToken());

        return shipper;
    }


    @Override
    public Shipper findById(Long id) {
        return shipperRepository.findById(id).orElse(null);
    }

    @Override
    public void createVerificationToken(Shipper shipper) {
        Date expireDate = calculateExpiryDate(60 * 24);
        String token = UUID.randomUUID().toString();

        VerificationToken shipperToken = new VerificationToken();
        shipperToken.setShipper(shipper);
        shipperToken.setToken(token);
        shipperToken.setExpiryDate(expireDate);

        tokenRepository.save(shipperToken);
    }

    @Override
    public boolean checkExpiryDate(VerificationToken verificationToken) {
        Calendar cal = Calendar.getInstance();
        return (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0;
    }

    @Override
    public void verifyToken(VerificationToken verificationToken, HttpServletResponse response) throws Exception {
        if (verificationToken != null) {
            boolean isExpired = checkExpiryDate(verificationToken);
            if (!isExpired) {
                Shipper shipper = verificationToken.getShipper();
                if (shipper != null) {
                    shipper.setEnabled(true);
                    shipperRepository.save(shipper);
                } else {
                    throw new RedirectException("Invalid shipper associated with token", "http://localhost:3000/confirm?status=error");
                }
            } else {
                throw new RedirectException("Expired token", "http://localhost:3000/confirm?status=error");
            }
        } else {
            throw new RedirectException("Invalid token", "http://localhost:3000/confirm?status=error");
        }
    }


    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
