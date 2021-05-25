package com.example.authservice.service.impl;

import com.example.authservice.businesslogic.AuthBusinessLogic;
import com.example.authservice.model.request.AuthRequest;
import com.example.authservice.model.response.AuthResponse;
import com.example.authservice.repository.AuthRepository;
import com.example.authservice.service.AuthService;
import com.example.common.exception.BusinessException;
import com.example.common.exception.ErrorCode;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthRepository authRepository;
    private final AuthBusinessLogic authBusinessLogic;

    public AuthServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AuthRepository authRepository, AuthBusinessLogic authBusinessLogic) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

                    if (!bCryptPasswordEncoder.matches(request.getPassword(), auth.getPassword())) {
                        throw BusinessException.of(ErrorCode.ERR_WRONG_PASSWORD);
                    }

                    return AuthResponse.builder()
                            .phoneNumber(auth.getPhoneNumber())
                            .token(authBusinessLogic.generateJwtToken(auth))
                            .expiredTime(authBusinessLogic.getExpireTime())
                            .build();
                });
    }
}
