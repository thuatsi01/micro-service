package com.example.shippingservice.grpc;

import com.example.authservice.grpc.CreateUserServiceGrpc;
import com.example.common.UserRoles;
import com.example.common.exception.BusinessException;
import com.example.common.exception.ErrorCode;
import com.example.shippingservice.configuration.grpc.AuthGrpcManagedChannel;
import com.example.shippingservice.model.request.CreateShipperRequest;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthGrpcService {

    @Autowired
    private AuthGrpcManagedChannel authGrpcManagedChannel;

    public Single<CreateUserResponse> createUserAsync(CreateShipperRequest request) {
        return Single.fromCallable(() -> {
            var stub = CreateUserServiceGrpc.newBlockingStub(authGrpcManagedChannel.getManagedChannel());
            var authGrpcResponse = stub.createUser(
                    com.example.authservice.grpc.CreateUserRequest.newBuilder()
                            .setPhoneNumber(request.getPhoneNumber())
                            .setPassword(request.getPassword())
                            .setRole(UserRoles.CUSTOMER.getName())
                            .build()
            );
            if (authGrpcResponse.getCode() != ErrorCode.SUCCESS) {
                throw BusinessException.of(ErrorCode.ERR_CREATE_USER);
            }
            return CreateUserResponse.of(authGrpcResponse.getUserId());
        }).subscribeOn(Schedulers.io());
    }

    @Value
    @AllArgsConstructor(staticName = "of")
    public static class CreateUserResponse {
        Long userId;
    }

}
