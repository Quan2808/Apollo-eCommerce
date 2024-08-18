package com.apollo.configuration;

import com.apollo.entity.Admin;
import com.apollo.entity.Shipper;
import com.apollo.entity.User;
import com.apollo.repository.AdminRepository;
import com.apollo.repository.ShipperRepository;
import com.apollo.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        // Bypass JWT filter for /api/register and /api/login endpoints
        if (path.equals("/api/register") || path.equals("/api/login")) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String id = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
            jwtToken = requestTokenHeader.substring(BEARER_PREFIX.length());
            try {
                if (jwtTokenUtil.isTokenExpired(jwtToken)) {
                    throw new ExpiredJwtException(null, null, "JWT token expired");
                }
                id = jwtTokenUtil.getIdFromToken(jwtToken);
                String entityType = jwtTokenUtil.getEntityTypeFromToken(jwtToken);
                authenticateUser(request, id, entityType);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT token", e);
            } catch (ExpiredJwtException e) {
                logger.error("JWT token has expired", e);
            }
        } else {
            logger.warn("JWT token does not begin with Bearer String");
        }

        chain.doFilter(request, response);
    }

    private void authenticateUser(HttpServletRequest request, String id, String entityType) {
        Long userId = Long.parseLong(id);
        Object principal = null;
        String role = null;

        switch (entityType) {
            case "user":
                User user = userRepository.findById(userId).orElse(null);
                if (user == null) {
                    throw new UsernameNotFoundException("User not found with ID: " + id);
                }
                principal = user;
                role = user.getRole();
                logger.info("Authenticated User: " + user.getEmail() + ", Role: " + role);
                break;

            case "admin":
                Admin admin = adminRepository.findById(userId).orElse(null);
                if (admin == null) {
                    throw new UsernameNotFoundException("Admin not found with ID: " + id);
                }
                principal = admin;
                role = admin.getRole();
                logger.info("Authenticated Admin: " + admin.getEmail() + ", Role: " + role);
                break;

            case "shipper":
                Shipper shipper = shipperRepository.findById(userId).orElse(null);
                if (shipper == null) {
                    throw new UsernameNotFoundException("Shipper not found with ID: " + id);
                }
                principal = shipper;
                role = shipper.getRole();
                logger.info("Authenticated Shipper: " + shipper.getEmail() + ", Role: " + role);
                break;

            default:
                throw new UsernameNotFoundException("Entity type not recognized: " + entityType);
        }

        if (principal != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    principal, null, Collections.singleton(new SimpleGrantedAuthority(role))
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
