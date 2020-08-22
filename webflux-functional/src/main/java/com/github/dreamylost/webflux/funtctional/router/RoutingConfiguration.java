package com.github.dreamylost.webflux.funtctional.router;

import com.github.dreamylost.webflux.funtctional.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(UserHandler userHandler) {
        return route(GET("/api/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAllUser)
                .andRoute(GET("/api/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(POST("/api/save").and(accept(MediaType.APPLICATION_JSON)), userHandler::saveUser);
    }

}
