package com.example.apigatewayservice.grpc;

import com.example.apigatewayservice.configuration.grpc.AuthGrpcManagedChannel;
import com.example.authservice.grpc.TokenVerifyRequest;
import com.example.authservice.grpc.TokenVerifyServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthGrpcService {

    private final AuthGrpcManagedChannel authGrpcManagedChannel;

    public AuthGrpcService(AuthGrpcManagedChannel authGrpcManagedChannel) {
        this.authGrpcManagedChannel = authGrpcManagedChannel;
    }

    public AuthGrpcResponse getAuthGrpc(String token) {
        var stub = TokenVerifyServiceGrpc.newBlockingStub(authGrpcManagedChannel.getManagedChannel());
        var authGrpcResponse = stub.verify(
                TokenVerifyRequest.newBuilder().setToken(token).build()
        );
        return AuthGrpcResponse.of(authGrpcResponse.getIsExpired(), authGrpcResponse.getUserId(), authGrpcResponse.getRole());
    }

    @AllArgsConstructor(staticName = "of")
    @Value
    public static class AuthGrpcResponse {
        Boolean isExpired;
        Long userId;
        String role;
    }
}
