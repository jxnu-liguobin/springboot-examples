package com.github.dreamylost.webflux.service;

import com.github.dreamylost.webflux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {

    Flux<User> getAllUser();

    Mono<User> getUserById(Long id);

    Mono<Void> saveUser(Mono<User> userMono);

}
