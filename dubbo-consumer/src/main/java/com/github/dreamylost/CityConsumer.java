package com.github.dreamylost;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 梦境迷离
 * @title: CityConsumer
 * @description: 消费者-得到对象
 * @date 2018-07-08
 */
@RestController
public class CityConsumer {

    @Reference
    private CityDubboService cityDubboService;

    @RequestMapping("/getCity")
    public City TestCity() {

        System.out.println(cityDubboService);
        City city = cityDubboService.findCityByName("南昌");
        System.out.println(city);
        return city;

    }
}
