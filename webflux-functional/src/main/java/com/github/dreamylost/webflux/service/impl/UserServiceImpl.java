package com.github.dreamylost.webflux.service.impl;

import com.github.dreamylost.webflux.model.User;
import com.github.dreamylost.webflux.service.IUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    private Map<Long, User> map = new HashMap<>(10);

    @PostConstruct
    public void init() {
        map.put(1L, new User(1L, "admin", "admin"));
        map.put(2L, new User(2L, "admin2", "admin2"));
        map.put(3L, new User(3L, "admin3", "admin3"));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(map.values());
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return Mono.just(map.get(id));
    }

    @Override
    public Mono<Void> saveUser(Mono<User> userMono) {
        Mono<User> mono = userMono.doOnNext(user ->
                map.put(user.getUid(), user)
        );
        return mono.then();
    }
}
