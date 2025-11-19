package com.braidsbeautybyangie.apigateway.filters;

import com.braidsbeautybyangie.apigateway.dto.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private WebClient userServiceWebClient;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);

            return userServiceWebClient.get()
                    .uri("/v1/user-service/auth/validate?token=" + token)
                    .retrieve()
                    .bodyToMono(TokenValidationResponse.class)
                    .onErrorResume(e -> {
                        System.out.println("Error validating token: " + e.getMessage());
                        return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));
                    })
                    .flatMap(validationResponse -> {
                        if (validationResponse == null || !validationResponse.isValid()) {
                            return Mono.error(new RuntimeException("Invalid token"));
                        }

                        // Verificar rol si es necesario
                        if (config.getRequiredRole() != null && !config.getRequiredRole().isEmpty()) {
                            if (!hasRequiredRole(validationResponse.getRoles(), config.getRequiredRole())) {
                                return Mono.error(new RuntimeException("Insufficient privileges"));
                            }
                        }

                        // Mutar request con headers del usuario
                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                .header("X-User-Id", validationResponse.getUserId().toString())
                                .header("X-Username", validationResponse.getUsername())
                                .header("X-User-Roles", String.join(",", validationResponse.getRoles()))
                                .build();

                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    })
                    .onErrorResume(e -> {
                        System.out.println("Authentication error: " + e.getMessage());
                        return unauthorized("Unauthorized access to application");
                    });
        };
    }

    private Mono<Void> unauthorized(String message) {
        return Mono.error(new RuntimeException(message));
    }


    private boolean hasRequiredRole(List<String> userRoles, String requiredRole) {
        return userRoles != null && userRoles.contains(requiredRole);
    }

    public static class Config {
        private String requiredRole;

        public Config() {}

        public Config(String requiredRole) {
            this.requiredRole = requiredRole;
        }

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }
}