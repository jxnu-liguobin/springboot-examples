package com.github.dreamylost.context;

import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 上下文提供器
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
@Component
public class ContextProvider {

    final DataLoaderRegistry dataLoaderRegistry;

    @Autowired
    public ContextProvider(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    public Context newContext() {
        return new Context(dataLoaderRegistry);
    }

}
