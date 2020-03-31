package com.github.dreamylost;

import com.github.dreamylost.cache.GraphQLCacheHelper;
import com.github.dreamylost.dao.StarWarsData;
import com.github.dreamylost.fetcher.GraphQLFetcherHelper;
import com.github.dreamylost.resolver.GraphQLResolverHelper;
import graphql.schema.DataFetcher;
import graphql.schema.TypeResolver;
import graphql.schema.idl.EnumValuesProvider;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
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
        //对于每个请求，都使用缓存，开启数据共享
        DataLoaderOptions options = DataLoaderOptions.newOptions().setCacheMap(GraphQLCacheHelper.getCacheMap());
        //加载所有角色数据，注册到characters
        dataLoaderRegistry.register("characters", DataLoader.newDataLoader(characterBatchLoader, options));
    }

    //graphql类型系统中的角色是一个接口，需要确定要返回的具体graphql对象类型
    TypeResolver characterTypeResolver = GraphQLResolverHelper.characterTypeResolver;

    //枚举类型解析
    EnumValuesProvider episodeResolver = GraphQLResolverHelper.episodeResolver;

    //人类 数据获取
    DataFetcher humanDataFetcher = GraphQLFetcherHelper.humanDataFetcher;

    //机器人 数据获取
    DataFetcher droidDataFetcher = GraphQLFetcherHelper.droidDataFetcher;

    //英雄 数据获取
    DataFetcher heroDataFetcher = GraphQLFetcherHelper.heroDataFetcher;

    //朋友 数据获取
    DataFetcher friendsDataFetcher = GraphQLFetcherHelper.friendsDataFetcher;

    //将DataLoaderRegistry作为bean，保留给ContextProvider
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

}
