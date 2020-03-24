package com.github.dreamylost.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient // 激活发现
public class HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @Autowired
    private DiscoveryClient client;

    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/cloud-central-hello-provider", method = RequestMethod.GET)
    public String index() {
        ServiceInstance instance = client.getLocalServiceInstance();
        System.out.println("生成者：/cloud-central-hello-provider,host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "生产者 <<<" + " Hello World >>>";
    }

}
