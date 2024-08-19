package com.apollo.service.impl;

import com.apollo.configuration.JwtTokenUtil;
import com.apollo.dto.JwtResponse;
import com.apollo.dto.UserLoginDTO;
import com.apollo.dto.UserRegisterDTO;
import com.apollo.entity.User;
import com.apollo.entity.VerificationToken;
import com.apollo.exception.RedirectException;
import com.apollo.repository.UserRepository;
import com.apollo.repository.VerificationTokenRepository;
import com.apollo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public JwtResponse login(UserLoginDTO userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else if (!user.isEnabled()) {
            throw new UsernameNotFoundException("User has not been confirmed");
        }
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new AuthenticationServiceException("Wrong password");
        }
        String accessToken = jwtTokenUtil.generateToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public User register(UserRegisterDTO userRegisterDto) {
        logger.info("Registering user with email: {}", userRegisterDto.getEmail());
        if (userRepository.findByEmail(userRegisterDto.getEmail()) != null) {
            throw new UsernameNotFoundException("User already exists");
        }
        String password = userRegisterDto.getPassword();
        User user = new User();
        user.setClientName(userRegisterDto.getClientName());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(userRegisterDto.getPhoneNumber());
        user.setRole("ROLE_USER");
        userRepository.save(user);
        logger.info("User registered successfully: {}", user.getEmail());
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void createVerificationToken(User user) {
        logger.info("Creating verification token for user: {}", user.getEmail());
        Date expireDate = calculateExpiryDate(60 * 24);
        String token = UUID.randomUUID().toString();
        VerificationToken userToken = new VerificationToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setExpiryDate(expireDate);
        tokenRepository.save(userToken);
        logger.info("Verification token created for user: {}", user.getEmail());
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
                User user = verificationToken.getUser();
                user.setEnabled(true);
                userRepository.save(user);
                logger.info("User verified successfully: {}", user.getEmail());
            } else {
                throw new RedirectException("Expired token", "http://localhost:3000/confirm?status=error");
            }
        } else {
            throw new RedirectException("Invalid token", "http://localhost:3000/confirm?status=error");
        }
    }

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
