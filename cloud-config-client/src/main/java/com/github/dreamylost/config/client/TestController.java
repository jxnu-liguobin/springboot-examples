package com.github.dreamylost.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date: 2018-07-11
 * @author: liguobin
 * @description: 测试配置中心
 */
@RefreshScope//如果代码中需要动态刷新配置，在需要的类上加上该注解就行
@RestController
public class TestController {

    @Value("${from}")
    private String form;

    @Autowired
    private Environment env;

    @RequestMapping("from")
    public String from() {
        String env1 = env.getProperty("from", "undefined");
        String env2 = this.form;
        return "@Value:" + env1 + "------" + "Env:" + env2;
    }
}

