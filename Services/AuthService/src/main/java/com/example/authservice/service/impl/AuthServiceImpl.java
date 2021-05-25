package com.example.authservice.service.impl;

import com.example.authservice.businesslogic.AuthBusinessLogic;
import com.example.authservice.model.request.AuthRequest;
import com.example.authservice.model.response.AuthResponse;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.service.AuthService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthBusinessLogic authBusinessLogic;

    public AuthServiceImpl(AuthRepository authRepository, AuthBusinessLogic authBusinessLogic) {
        this.authRepository = authRepository;
        this.authBusinessLogic = authBusinessLogic;
    }

    @Override
    public Single<AuthResponse> authorizeAsync(AuthRequest request) {
        return Single.fromCallable(() -> authRepository.findByPhoneNumber(request.getPhoneNumber()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(authOptional -> {
                    var auth = authOptional.orElseThrow(RuntimeException::new);

                    if (!auth.getPassword().equalsIgnoreCase(request.getPassword())) {
                        throw new RuntimeException();
                    }

                    return AuthResponse.builder()
                            .phoneNumber(auth.getPhoneNumber())
                            .token(authBusinessLogic.generateJwtToken(auth))
                            .expiredTime(authBusinessLogic.getExpireTime())
                            .build();
                });
    }
}
