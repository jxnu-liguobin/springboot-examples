package com.github.dreamylost;

/**
 * @author 梦境迷离
 * @title: CityDubboService
 * @description: 城市接口
 * @date 2018-07-08
 */
public interface CityDubboService {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName
     */
    City findCityByName(String cityName);
}
