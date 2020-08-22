package com.github.dreamylost.webflux.funtctional.handler;

import com.github.dreamylost.webflux.model.User;
import com.github.dreamylost.webflux.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private IUserService userService;

    @Autowired
    public UserHandler(IUserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getAllUser(ServerRequest serverRequest) {
        Flux<User> allUser = userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(allUser, User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        //获取url上的id
        Long uid = Long.valueOf(serverRequest.pathVariable("id"));
        Mono<User> user = userService.getUserById(uid);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(user, User.class);
    }

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        Mono<User> user = serverRequest.bodyToMono(User.class);
        return ServerResponse.ok().build(userService.saveUser(user));
    }

}
