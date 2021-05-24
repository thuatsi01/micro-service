package com.example.apigatewayservice.grpc;

import com.example.authservice.grpc.TokenVerifyRequest;
import com.example.authservice.grpc.TokenVerifyServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthGrpcService {

    public AuthGrpcResponse getAuthGrpc(String token) {
        var channel = ManagedChannelBuilder.forAddress("localhost", 56565)
                .usePlaintext()
                .build();
        var stub = TokenVerifyServiceGrpc.newBlockingStub(channel);
        var authGrpcResponse = stub.verify(
                TokenVerifyRequest.newBuilder()
                        .setToken(token)
                        .build()
        );
        channel.shutdown();
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
