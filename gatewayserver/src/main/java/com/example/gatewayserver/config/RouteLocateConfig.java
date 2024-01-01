package com.example.gatewayserver.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class RouteLocateConfig {
    @Bean
    public RouteLocator routeLocatorConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/accounts/**")
                        .filters(
                                f -> f.rewritePath("/accounts/(?<segment>.*)","/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://ACCOUNTS")
                ).route(p -> p
                        .path("/cards/**")
                        .filters(
                                f -> f.rewritePath("/cards/(?<segment>.*)","/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://CARDS")
                ).route(p -> p
                        .path("/loans/**")
                        .filters(
                                f -> f.rewritePath("/loans/(?<segment>.*)","/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://LOANS")
                )
                .build();

    }
}
