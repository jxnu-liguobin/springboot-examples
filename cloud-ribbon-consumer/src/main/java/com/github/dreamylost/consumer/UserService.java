package com.github.dreamylost.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Hystrix断路器
 * <p>
 * 异步方法
 *
 * @author 梦境迷离
 * @title: UserService
 * @description:
 * @date 2018-07-29
 */

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    //异步执行
    @HystrixCommand
    public Future<String> userService() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForEntity("http://CLOUD-USER-SERVICE/user", String.class).getBody();
            }
        };
    }

}
