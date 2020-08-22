package com.github.dreamylost.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class SpringbootWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebfluxApplication.class, args);
    }
}
