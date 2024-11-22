package com.bookstore.gateway.config;


import com.bookstore.gateway.filter.JwtAdminAuthenticationFilter;
import com.bookstore.gateway.filter.JwtBasicAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAdminAuthenticationFilter adminAuthFilter;
    private final JwtBasicAuthenticationFilter basicAuthFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("catalog", r -> r.path("/bookstore/books/**")
                        .uri("lb://catalog"))

                .route("order", r -> r.path("/bookstore/orders/**")
                        .filters(f -> f.filter(basicAuthFilter))
                        .uri("lb://order"))

                .route("user", r -> r.path("/bookstore/auth/**")
                        .uri("lb://user"))

                .route("user", r -> r.path("/bookstore/users/**")
                        .filters(f -> f.filter(adminAuthFilter))
                        .uri("lb://user"))

                .route("notification", r -> r.path("/bookstore/notifications/**")
                        .filters(f -> f.filter(basicAuthFilter))
                        .uri("lb://notification"))

                .route("inventory", r -> r.path("/bookstore/inventory/**")
                        .filters(f -> f.filter(adminAuthFilter))
                        .uri("lb://inventory"))

                .build();
    }


}