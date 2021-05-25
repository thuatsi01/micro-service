package com.example.authservice.controller;

import com.example.authservice.model.request.AuthRequest;
import com.example.authservice.model.response.AuthResponse;
import com.example.authservice.service.AuthService;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Single<ResponseEntity<AuthResponse>> loginAsync(@Valid @RequestBody AuthRequest request) {
        return authService.authorizeAsync(request)
                .map(ResponseEntity::ok);
    }
}
