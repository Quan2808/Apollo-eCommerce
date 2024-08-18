package com.apollo.configuration;

import com.apollo.entity.Admin;
import com.apollo.entity.Shipper;
import com.apollo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 24*60*60*1000;
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 7*24*60*60*1000;

    @Value("${security.jwt.secret-key:secret-key}")
    private String secretKey;

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        claims.put("entity_type", "user");
        return doGenerateToken(claims, user.getId().toString());
    }

    public String generateAdminToken(Admin admin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_ADMIN");
        claims.put("entity_type", "admin");
        return doGenerateToken(claims, admin.getId().toString());
    }

    public String generateShipperToken(Shipper shipper) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_SHIPPER");
        claims.put("entity_type", "shipper");
        return doGenerateToken(claims, shipper.getId().toString());
    }


    public String getEntityTypeFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("entity_type", String.class);
    }


    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateRefreshToken(claims, user.getId().toString());
    }

    public String generateAdminRefreshToken(Admin admin) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateRefreshToken(claims, admin.getId().toString());
    }

    public String generateShipperRefreshToken(Shipper shipper) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateRefreshToken(claims, shipper.getId().toString());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, User user) {
        final String id = getIdFromToken(token);
        final String entityType = getEntityTypeFromToken(token);
        return (id.equals(user.getId().toString()) && !isTokenExpired(token) && "user".equals(entityType));
    }

    public Boolean validateAdminToken(String token, Admin admin) {
        final String id = getIdFromToken(token);
        final String entityType = getEntityTypeFromToken(token);
        return (id.equals(admin.getId().toString()) && !isTokenExpired(token) && "admin".equals(entityType));
    }

    public Boolean validateShipperToken(String token, Shipper shipper) {
        final String id = getIdFromToken(token);
        final String entityType = getEntityTypeFromToken(token);
        return (id.equals(shipper.getId().toString()) && !isTokenExpired(token) && "shipper".equals(entityType));
    }

}
