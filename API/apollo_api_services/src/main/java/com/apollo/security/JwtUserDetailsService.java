package com.apollo.security;

import com.apollo.entity.Admin;
import com.apollo.entity.Shipper;
import com.apollo.entity.User;
import com.apollo.entity.VerificationToken;
import com.apollo.repository.AdminRepository;
import com.apollo.repository.ShipperRepository;
import com.apollo.repository.UserRepository;
import com.apollo.repository.VerificationTokenRepository;
import com.apollo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        VerificationToken token = verificationTokenRepository.findByUser(user);
        if (user.isEnabled()) {
            throw new UsernameNotFoundException("Email has already been registered");
        }
        if (isTokenExpired(token)) {
            emailService.sendConfirmEmailToUser(user, token.getToken());
            return null;
        }
        throw new UsernameNotFoundException("Email has not been enabled");
    }

    public UserDetails loadShipperByUsername(String email) throws UsernameNotFoundException {
        Shipper shipper = shipperRepository.findByEmail(email);
        if (shipper == null) {
            throw new UsernameNotFoundException("Shipper not found");
        }
        VerificationToken token = verificationTokenRepository.findByShipper(shipper);
        if (shipper.isEnabled()) {
            throw new UsernameNotFoundException("Email has already been registered");
        }
        if (isTokenExpired(token)) {
            emailService.sendConfirmEmailToShipper(shipper, token.getToken());
            return null;
        }
        throw new UsernameNotFoundException("Email has not been enabled");
    }

    public UserDetails loadAdminByEmail(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            return null; // Admin not found, which means email is available for registration
        }
        throw new UsernameNotFoundException("Email has already been registered");
    }

    private boolean isTokenExpired(VerificationToken token) {
        Calendar cal = Calendar.getInstance();
        return (token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0;
    }
}
