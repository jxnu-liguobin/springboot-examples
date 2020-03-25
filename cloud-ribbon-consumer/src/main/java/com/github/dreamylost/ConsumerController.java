package com.github.dreamylost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * @author 梦境迷离
 * @description
 * @time 2018年4月5日
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HelloService helloService;

    @Autowired
    private UserService userService;


    //测试断路器
    @RequestMapping(value = "/ribbon-consumer2", method = RequestMethod.GET)
    public String helloConsumer2() {
        return helloService.hellService();
    }


    @RequestMapping(value = "/cloud-ribbon-consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        String string = restTemplate.getForEntity("http://CLOUD-HELLO-PROVIDER/hello", String.class).getBody();
        System.out.println("消费者:" + string);
        return "消费者 <<<" + string + " >>>";
    }


    @RequestMapping(value = "/user-consumer", method = RequestMethod.GET)
    public String userConsumer() {
        String user = restTemplate.getForEntity("http://CLOUD-USER-PROVIDER/user", String.class).getBody();
        System.out.println("消费者:" + user);

        return "消费者 <<<" + user + " >>>";
    }

    @RequestMapping(value = "/user-consumer2", method = RequestMethod.GET)
    public String userConsumer2() throws ExecutionException, InterruptedException {
        return userService.userService().get();
    }
}
