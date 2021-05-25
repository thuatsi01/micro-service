package com.example.customer.configuration.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Scope("prototype")
public class AuthGrpcManagedChannel {

    private final ManagedChannel managedChannel;

    public AuthGrpcManagedChannel(
            @Value("${grpc-service.auth-service.host-name}") String hostname,
            @Value("${grpc-service.auth-service.port}") Integer port) {
        this.managedChannel = ManagedChannelBuilder.forAddress(hostname, port)
                .usePlaintext()
                .build();
    }

    public ManagedChannel getManagedChannel() {
        return managedChannel;
    }

    @PreDestroy
    public void destroy() {
        if (managedChannel != null && !managedChannel.isShutdown()) {
            managedChannel.shutdown();
        }
    }
}
