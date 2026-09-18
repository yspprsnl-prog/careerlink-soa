package com.careerlink.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> authRoute() {
        return route("auth-service")
                .route(path("/auth/**"), http())
                .before(uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> profileRoute() {
        return route("profile-service")
                .route(path("/profiles/**"), http())
                .before(uri("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> jobRoute() {
        return route("job-service")
                .route(path("/jobs/**"), http())
                .before(uri("http://localhost:8083"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> applicationRoute() {
        return route("application-service")
                .route(path("/applications/**"), http())
                .before(uri("http://localhost:8084"))
                .build();
    }
}
