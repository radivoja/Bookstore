package com.bookstore.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("catalog", r -> r.path("/bookstore/books/**")
                        .uri("http://localhost:8081"))
                .route("user", r -> r.path("/bookstore/orders/**")
                        .uri("http://localhost:8082"))
                .route("user", r -> r.path("/bookstore/users/**")
                        .uri("http://localhost:8083"))
                .route("user", r -> r.path("/bookstore/notifications/**")
                        .uri("http://localhost:8084"))
                .route("user", r -> r.path("/bookstore/books/**")
                        .uri("http://localhost:8085"))
                .build();
    }


}