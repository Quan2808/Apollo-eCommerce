package com.apollo.controller;

import com.apollo.configuration.JwtTokenUtil;
import com.apollo.dto.*;
import com.apollo.entity.Admin;
import com.apollo.entity.Shipper;
import com.apollo.entity.User;
import com.apollo.entity.VerificationToken;
import com.apollo.repository.VerificationTokenRepository;
import com.apollo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    private final AdminService adminService;

    private final EmailService emailService;

    private final VerificationTokenRepository verificationTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    private final ShipperService shipperService;

    public AuthController(AuthService authService, AdminService adminService, EmailService emailService, VerificationTokenRepository verificationTokenRepository, JwtTokenUtil jwtTokenUtil, @Qualifier("shipperService") ShipperService shipperService) {
        this.authService = authService;
        this.adminService = adminService;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.shipperService = shipperService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDto) {
        JwtResponse jwtResponse = authService.login(userLoginDto);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO userRegisterDto) {
        logger.info("Register endpoint hit");
        User user = authService.register(userRegisterDto);
        authService.createVerificationToken(user);
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        String token = verificationToken.getToken();
        emailService.sendConfirmEmailToUser(user, token);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{type}/info")
    public ResponseEntity<?> getUserInfo(@PathVariable String type,@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String userId = jwtTokenUtil.getIdFromToken(jwtToken);
            Object userInfo = userService.getUserById(type, Long.parseLong(userId));
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    @PostMapping("/login/shipper")
    public ResponseEntity<?> loginShipper(@RequestBody ShipperLoginDTO shipperLoginDto) {
        try {
            JwtResponse jwtResponse = shipperService.loginShipper(shipperLoginDto);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register/shipper")
    public ResponseEntity<?> registerShipper(@RequestBody ShipperRegisterDTO shipperRegisterDTO) {
        logger.info("Register endpoint hit");
        try {
            Shipper shipper = shipperService.register(shipperRegisterDTO);
            return ResponseEntity.ok(shipper);
        } catch (UsernameNotFoundException e) {
            logger.error("Error during shipper registration: ", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email has already been registered");
        } catch (Exception e) {
            logger.error("Error during shipper registration: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/register/confirmation")
    public void verifyUser(@RequestParam(name = "token") String token, HttpServletResponse response) throws Exception {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken != null) {
            if (verificationToken.getUser() != null) {
                authService.verifyToken(verificationToken, response);
                response.sendRedirect("http://localhost:3000/confirm?status=success");
            } else if (verificationToken.getShipper() != null) {
                shipperService.verifyToken(verificationToken, response);
                response.sendRedirect("http://localhost:3000/confirm?status=success");
            } else {
                response.sendRedirect("http://localhost:3000/confirm?status=error");
            }
        } else {
            response.sendRedirect("http://localhost:3000/confirm?status=error");
        }
    }


    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginDTO adminLoginDto) {
        JwtResponse jwtResponse = adminService.login(adminLoginDto);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterDTO adminRegisterDto) {
        try {
            Admin registeredAdmin = adminService.register(adminRegisterDto);
            if (registeredAdmin != null) {
                return ResponseEntity.ok(registeredAdmin);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: Invalid response received");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
