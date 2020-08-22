package com.github.dreamylost.webflux;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class WebFluxController {

    private Map<Long, User> map = new HashMap<Long, User>(10);

    @PostConstruct
    public void init() {
        map.put(1L, new User(1L, "admin", "admin"));
        map.put(2L, new User(2L, "admin2", "admin2"));
        map.put(3L, new User(3L, "admin3", "admin3"));
    }

    @GetMapping("/getAll")
    @CrossOrigin
    public Flux<User> getAllUser() {
        return Flux.fromIterable(map.entrySet().stream().map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable("id") Long id) {
        return Mono.just(map.get(id));
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<String>> save(@RequestBody User user) {
        map.put(user.getUid(), user);
        return Mono.just(new ResponseEntity<>("添加成功", HttpStatus.CREATED));
    }
}
