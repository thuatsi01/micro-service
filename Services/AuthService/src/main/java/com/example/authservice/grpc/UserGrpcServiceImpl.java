package com.example.authservice.grpc;

import com.example.authservice.entities.Auth;
import com.example.authservice.repository.AuthRepository;
import com.example.common.exception.ErrorCode;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;

@GrpcService
public class UserGrpcServiceImpl extends CreateUserServiceGrpc.CreateUserServiceImplBase {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        var auth = new Auth();
        auth.setPhoneNumber(request.getPhoneNumber());
        auth.setRole(request.getRole());
        auth.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        auth.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        try {
            authRepository.save(auth);
            responseObserver.onNext(CreateUserResponse.newBuilder()
                    .setCode(ErrorCode.SUCCESS)
                    .setUserId(auth.getId())
                    .build()
            );
        } catch (Exception e) {
            responseObserver.onNext(CreateUserResponse.newBuilder()
                    .setCode(ErrorCode.ERR_CREATE_USER)
                    .build()
            );
        } finally {
            responseObserver.onCompleted();
        }
    }
}
