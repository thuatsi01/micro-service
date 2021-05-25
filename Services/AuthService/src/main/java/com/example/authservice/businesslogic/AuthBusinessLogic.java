package com.example.authservice.businesslogic;

import com.example.authservice.entities.Auth;

public interface AuthBusinessLogic {

    String generateJwtToken(Auth auth);

    Long getExpireTime();

    Boolean isTokenExpired(String token);

    Long getUserIdFromToken(String token);

    String getUserRoleFromToken(String token);
}
