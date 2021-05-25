package com.example.authservice.businesslogic.impl;

import com.example.authservice.businesslogic.AuthBusinessLogic;
import com.example.authservice.entities.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class AuthBusinessLogicImpl implements AuthBusinessLogic {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire_time}")
    private Long expireTime;

    @Override
    public String generateJwtToken(Auth auth) {
        var claims = new HashMap<String, Object>();
        claims.put("ROLE", auth.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(auth.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public Long getExpireTime() {
        return System.currentTimeMillis() + expireTime * 60 * 1000;
    }

    @Override
    public Boolean isTokenExpired(String token) {
        var expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return Long.parseLong(getClaimFromToken(token, Claims::getSubject));
    }

    @Override
    public String getUserRoleFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("ROLE", String.class));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
