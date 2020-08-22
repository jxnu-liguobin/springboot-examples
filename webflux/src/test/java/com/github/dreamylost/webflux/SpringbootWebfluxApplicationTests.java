package com.github.dreamylost.webflux;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebFluxTest
public class SpringbootWebfluxApplicationTests {

    @Autowired
    private WebTestClient webClient;

    private static Map<Long, User> map = new HashMap<Long, User>(10);

    @BeforeClass
    public static void init() {
        map.put(1L, new User(1L, "admin", "admin"));
        map.put(2L, new User(2L, "admin2", "admin2"));
        map.put(3L, new User(3L, "admin3", "admin3"));
    }

    @Test
    public void contextLoads() {
        User user = new User(4L, "admin4", "admin4");
        webClient.post().uri("/api/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("添加成功");
    }

}
