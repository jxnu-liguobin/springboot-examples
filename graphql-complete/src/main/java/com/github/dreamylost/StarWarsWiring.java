package com.github.dreamylost;

import com.github.dreamylost.context.Context;
import com.github.dreamylost.data.Episode;
import com.github.dreamylost.data.FilmCharacter;
import com.github.dreamylost.data.Human;
import com.github.dreamylost.data.StarWarsData;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import graphql.schema.idl.EnumValuesProvider;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 这是用于将获取行为置于graphql字段后面的连线
 */
@Component
public class StarWarsWiring {

    //数据加载
    private final DataLoaderRegistry dataLoaderRegistry;

    public StarWarsWiring() {
        this.dataLoaderRegistry = new DataLoaderRegistry();
        dataLoaderRegistry.register("characters", newCharacterDataLoader());
    }

    @Bean
    public DataLoaderRegistry getDataLoaderRegistry() {
        return dataLoaderRegistry;
    }


    private List<Object> getCharacterDataViaBatchHTTPApi(List<String> keys) {
        return keys.stream().map(StarWarsData::getCharacterData).collect(Collectors.toList());
    }

    //批处理
    private BatchLoader<String, Object> characterBatchLoader = keys -> {

        //我们在这里使用多线程
        return CompletableFuture.supplyAsync(() -> getCharacterDataViaBatchHTTPApi(keys));
    };

    private DataLoader<String, Object> newCharacterDataLoader() {
        return new DataLoader<>(characterBatchLoader);
    }

    //人类 数据获取器
    DataFetcher humanDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };


    DataFetcher droidDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };

    DataFetcher heroDataFetcher = environment -> {
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load("2001"); // R2D2
    };

    DataFetcher friendsDataFetcher = environment -> {
        FilmCharacter character = environment.getSource();
        List<String> friendIds = character.getFriends();
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().loadMany(friendIds);
    };

    /**
     * graphql类型系统中的角色是一个接口，需要确定要返回的具体graphql对象类型
     */
    TypeResolver characterTypeResolver = environment -> {
        FilmCharacter character = environment.getObject();
        if (character instanceof Human) {
            //人类或机器人
            return (GraphQLObjectType) environment.getSchema().getType("Human");
        } else {
            return (GraphQLObjectType) environment.getSchema().getType("Droid");
        }
    };

    EnumValuesProvider episodeResolver = Episode::valueOf;
}
