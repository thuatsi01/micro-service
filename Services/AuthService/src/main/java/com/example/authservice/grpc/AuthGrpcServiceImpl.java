package com.example.authservice.grpc;

import com.example.authservice.businesslogic.AuthBusinessLogic;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthGrpcServiceImpl extends com.example.authservice.grpc.TokenVerifyServiceGrpc.TokenVerifyServiceImplBase {

    private final AuthBusinessLogic authBusinessLogic;

    public AuthGrpcServiceImpl(AuthBusinessLogic authBusinessLogic) {
        this.authBusinessLogic = authBusinessLogic;
    }

    @Override
    public void verify(TokenVerifyRequest request, StreamObserver<TokenVerifyResponse> responseObserver) {
        var response = TokenVerifyResponse.newBuilder()
                .setIsExpired(authBusinessLogic.isTokenExpired(request.getToken()))
                .setUserId(authBusinessLogic.getUserIdFromToken(request.getToken()))
                .setRole(authBusinessLogic.getUserRoleFromToken(request.getToken()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
