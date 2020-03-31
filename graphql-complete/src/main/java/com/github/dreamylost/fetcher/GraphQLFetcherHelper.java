package com.github.dreamylost.fetcher;

import com.github.dreamylost.context.Context;
import com.github.dreamylost.domain.FilmCharacter;
import graphql.schema.DataFetcher;

import java.util.List;

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

    //机器人 数据获取
    public static DataFetcher droidDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };

    //英雄 数据获取
    public static DataFetcher heroDataFetcher = environment -> {
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load("2001"); // R2D2
    };

    //朋友 数据获取
    public static DataFetcher friendsDataFetcher = environment -> {
        FilmCharacter character = environment.getSource();
        List<String> friendIds = character.getFriends();
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().loadMany(friendIds);
    };
}
