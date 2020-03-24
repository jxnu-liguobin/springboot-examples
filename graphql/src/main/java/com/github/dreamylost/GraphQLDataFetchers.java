package com.github.dreamylost;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;


/**
 * 获取数据
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
@Component
public class GraphQLDataFetchers {


    public DataFetcher getHelloWorldDataFetcher() {
        return environment -> "world";
    }

    public DataFetcher getEchoDataFetcher() {
        return environment -> environment.getArgument("toEcho");
    }


}