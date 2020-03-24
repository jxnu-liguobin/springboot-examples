package com.github.dreamylost;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 梦境迷离
 * @title: HelloConsumer
 * @description: 消费者-得到字符串
 * @date 2018-07-08
 */
@RestController
public class HelloConsumer {

    @Reference
    private IHelloService helloService;

    @GetMapping("sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return helloService.sayHello(name);
    }
}
