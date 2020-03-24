package com.github.dreamylost;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author 梦境迷离
 * @title: CityDubboServiceImpl
 * @description: 提供者-City具体实现
 * @date 2018-07-08
 */
@Service
@Component// 注册为 Dubbo 服务
public class CityDubboServiceImpl implements CityDubboService {

    @Override
    public City findCityByName(String cityName) {

        return new City(1L, 2L, "南昌", "是我的故乡");
    }
}