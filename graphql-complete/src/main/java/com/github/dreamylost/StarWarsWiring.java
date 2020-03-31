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
import org.dataloader.CacheMap;
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

    //人类 数据获取
    DataFetcher humanDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };


    //机器人 数据获取
    DataFetcher droidDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load(id);
    };

    //英雄 数据获取
    DataFetcher heroDataFetcher = environment -> {
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().load("2001"); // R2D2
    };

    //朋友 数据获取
    DataFetcher friendsDataFetcher = environment -> {
        FilmCharacter character = environment.getSource();
        List<String> friendIds = character.getFriends();
        Context ctx = environment.getContext();
        return ctx.getCharacterDataLoader().loadMany(friendIds);
    };

    //数据加载
    private final DataLoaderRegistry dataLoaderRegistry;

    public StarWarsWiring() {
        this.dataLoaderRegistry = new DataLoaderRegistry();
        //注册所有角色数据
        dataLoaderRegistry.register("characters", newCharacterDataLoader());
    }

    //将DataLoaderRegistry作为bean
    @Bean
    public DataLoaderRegistry getDataLoaderRegistry() {
        return dataLoaderRegistry;
    }

    //批量根据id获取角色的信息数据
    private List<Object> getCharacterDataViaBatchHTTPApi(List<String> keys) {
        return keys.stream().map(StarWarsData::getCharacterData).collect(Collectors.toList());
    }

    //批处理，使用多线程
    private BatchLoader<String, Object> characterBatchLoader = keys -> {
        //我们在这里使用多线程
        return CompletableFuture.supplyAsync(() -> getCharacterDataViaBatchHTTPApi(keys));
    };


    //缓存，根据需要使用存储方式。这里都只是为了过编译，实际没有使用
    CacheMap<String, Object> crossRequestCacheMap = new CacheMap<String, Object>() {
        @Override
        public boolean containsKey(String key) {
            return true;
        }

        @Override
        public Object get(String key) {
            return new Object();
        }

        @Override
        public CacheMap<String, Object> set(String key, Object value) {
            return this;
        }

        @Override
        public CacheMap<String, Object> delete(String key) {
            return this;
        }

        @Override
        public CacheMap<String, Object> clear() {
            return this;
        }
    };

    //加载所有角色数据
    private DataLoader<String, Object> newCharacterDataLoader() {
        //使用缓存
        //DataLoaderOptions options = DataLoaderOptions.newOptions().setCacheMap(crossRequestCacheMap);
        //DataLoader.newDataLoader(characterBatchLoader, options);
        return new DataLoader<>(characterBatchLoader);
    }


    /**
     * graphql类型系统中的角色是一个接口，需要确定要返回的具体graphql对象类型
     */
    TypeResolver characterTypeResolver = environment -> {
        //类型解析
        FilmCharacter character = environment.getObject();
        if (character instanceof Human) {
            //人类
            return (GraphQLObjectType) environment.getSchema().getType("Human");
        } else {
            //机器人
            return (GraphQLObjectType) environment.getSchema().getType("Droid");
        }
    };

    //枚举类型解析
    EnumValuesProvider episodeResolver = Episode::valueOf;
}
