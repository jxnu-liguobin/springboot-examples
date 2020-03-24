package com.github.dreamylost;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 梦境迷离
 * @title: DubboConsumerLauncher
 * @description: 消费者
 * @date 2018-07-08
 */
@SpringBootApplication
@EnableDubboConfiguration
public class DubboConsumerLauncher {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DubboConsumerLauncher.class, args);
    }

}
