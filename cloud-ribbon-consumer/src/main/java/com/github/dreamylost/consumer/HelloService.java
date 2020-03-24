package com.github.dreamylost.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Hystrix断路器
 * <p>
 * 错误回调处理的请求
 *
 * @author 梦境迷离
 * @title: HelloService
 * @description:
 * @date 2018-07-29
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    //同步执行
    @HystrixCommand(fallbackMethod = "helloFallback")//使用注解配置服务降级实现方法
    public String hellService() {
        return restTemplate.getForEntity("http://CLOUD-HELLO-PROVIDER/hello", String.class).getBody();
    }

    //错误回调
    public String helloFallback() {
        return "error";
    }
}
