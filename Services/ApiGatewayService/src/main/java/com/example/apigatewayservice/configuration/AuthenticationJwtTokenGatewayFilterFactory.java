package com.example.apigatewayservice.configuration;

import com.example.apigatewayservice.grpc.AuthGrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationJwtTokenGatewayFilterFactory implements GatewayFilterFactory<AuthenticationJwtTokenGatewayFilterFactory.Config> {

    private final static String BEARER_TOKEN_PREFIX = "Bearer ";

    @Autowired
    private AuthGrpcService authGrpcService;

    @Autowired
    private RouterValidator routerValidator;

    @Override
    public Class<Config> getConfigClass() {
        return AuthenticationJwtTokenGatewayFilterFactory.Config.class;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnable()) {
                return chain.filter(exchange);
            }
            return filter(exchange, chain);
        };
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request))
                return this.onError(exchange);

            var bearerToken = this.getAuthHeader(request);
            if (!isValidToken(bearerToken))
                return this.onError(exchange);

            var jwtToken = bearerToken.replace(BEARER_TOKEN_PREFIX, "");
            var authResponse = authGrpcService.getAuthGrpc(jwtToken);
            if (authResponse.getIsExpired())
                return this.onError(exchange);

            this.populateRequestWithHeaders(exchange, authResponse);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private boolean isValidToken(String token) {
        return token.startsWith(BEARER_TOKEN_PREFIX);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, AuthGrpcService.AuthGrpcResponse authGrpcResponse) {
        exchange.getRequest().mutate()
                .header("user_id", String.valueOf(authGrpcResponse.getUserId()))
                .header("user_role", authGrpcResponse.getRole())
                .build();
    }

    public static class Config {
        /**
         * Control whether statistics are turned on
         */
        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public AuthenticationJwtTokenGatewayFilterFactory.Config setEnable(boolean enable) {
            this.enable = enable;
            return this;
        }
    }
}
