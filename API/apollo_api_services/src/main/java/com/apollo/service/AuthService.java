package com.apollo.service;

import com.apollo.dto.JwtResponse;
import com.apollo.dto.UserLoginDTO;
import com.apollo.dto.UserRegisterDTO;
import com.apollo.entity.User;
import com.apollo.entity.VerificationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface AuthService {
    JwtResponse login(UserLoginDTO userLoginDTO);

    User register(UserRegisterDTO userRegisterDTO);

    User findById(Long id);

    void createVerificationToken(User user);

    boolean checkExpiryDate(VerificationToken verificationToken);

    void verifyToken(VerificationToken verificationToken, HttpServletResponse response) throws Exception;
}
