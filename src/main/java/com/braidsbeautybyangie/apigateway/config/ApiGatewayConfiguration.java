package com.braidsbeautybyangie.apigateway.config;

import com.braidsbeautybyangie.apigateway.filters.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    private final AuthenticationFilter authenticationFilter;

    public ApiGatewayConfiguration(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
                // Rutas públicas (sin autenticación)
                .route(p -> p.path("/v1/user-service/auth/**")
                        .uri("lb://user-service"))
                .route(p -> p.path("/v1/user-service/user/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ROLE_ADMIN"))))
                        .uri("lb://user-service"))
                .route(p -> p.path("/v1/user-service/customer/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ROLE_ADMIN"))))
                        .uri("lb://user-service"))
                .route(p -> p.path("/v1/user-service/employee/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ROLE_ADMIN"))))
                        .uri("lb://user-service"))
                .route(p -> p.path("/v1/product-service/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config("ROLE_ADMIN"))))
                        .uri("lb://product-service"))
                .route(p -> p.path("/v1/payment-service/**")
                        .uri("lb://payment-service"))
                .route(p -> p.path("/v1/reservation-service/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://reservation-service"))
                .route(p -> p.path("/v1/credit-card/**")
                        .uri("lb://credit-card-processor-service"))
                .route(p -> p.path("/v1/orders-service/**")
                        .uri("lb://orders-service"))
                .build();

    }

}
