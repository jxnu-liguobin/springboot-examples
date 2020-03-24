package com.github.dreamylost.context;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;

/**
 * 上下文对象被传递到graphql查询的每个级别，在这种情况下，它包含数据加载器注册表。
 * 这使我们能够按请求保留数据加载器，因为它们经常缓存数据，而交叉请求缓存通常不是您想要的。
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public class Context {

    final DataLoaderRegistry dataLoaderRegistry;

    Context(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    public DataLoader<String, Object> getCharacterDataLoader() {
        return dataLoaderRegistry.getDataLoader("characters");
    }
}
