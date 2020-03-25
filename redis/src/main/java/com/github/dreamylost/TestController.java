package com.github.dreamylost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    RedisTemplate redisTemplate;

    //输入测试地址 http://localhost:8081/?p=90909
    @RequestMapping("/")
    @ResponseBody
    public User hello(String p) {
        System.out.println("have a request");
        //redis-cli get hello
        //查看value    [\"com.github.dreamylost.User\",{\"uid\":123,\"userName\":\"zhangsan\",\"passWord\":\"pwd\"}]
        redisTemplate.opsForValue().set("hello", new User(123, "zhangsan", p));
        User s = (User) redisTemplate.opsForValue().get("hello");
        return s;
    }

}