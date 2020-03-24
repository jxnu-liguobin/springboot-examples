package com.github.dreamylost;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 梦境迷离
 * @title: DubboProviderLauncher
 * @description: 提供者(生产者)
 * @date 2018-07-08
 */
@SpringBootApplication
@EnableDubboConfiguration
public class DubboProviderLauncher {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DubboProviderLauncher.class, args);
    }

}
