package com.example.authservice.service;

import com.example.authservice.model.request.AuthRequest;
import com.example.authservice.model.response.AuthResponse;
import io.reactivex.Single;

public interface AuthService {

    Single<AuthResponse> authorizeAsync(AuthRequest request);
}
