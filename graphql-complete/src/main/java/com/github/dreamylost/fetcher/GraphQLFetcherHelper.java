package com.github.dreamylost.fetcher;

import com.github.dreamylost.context.Context;
import com.github.dreamylost.domain.FilmCharacter;
import graphql.schema.DataFetcher;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author liguobin@growingio.com
 * @version 1.0, 2020/3/31
 */
public class GraphQLFetcherHelper {

    //人类 数据获取
    public static DataFetcher humanDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };

    //人类 数据读取器，获取所有s
    //实际应用应该实现DataFetcher接口而不是使用DataLoaderRegistry
    public static DataFetcher humansDataFetcher = environment -> {
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().loadMany(asList("1000", "1001", "1002", "1003"));
    };

    //机器人 数据获取
    public static DataFetcher droidDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };

    //英雄 数据获取
    public static DataFetcher heroDataFetcher = environment -> {
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load("1002"); // R2D2
    };

    //朋友 数据获取
    public static DataFetcher friendsDataFetcher = environment -> {
        FilmCharacter character = environment.getSource();
        List<com.github.dreamylost.domain.FilmCharacter> friendIds = character.getFriends();
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().loadMany(friendIds.stream().map(FilmCharacter::getId).collect(Collectors.toList()));
    };
}
