package com.github.dreamylost.cache;

import org.dataloader.CacheMap;

/**
 * @author liguobin@growingio.com
 * @version 1.0, 2020/3/31
 */
public class GraphQLCacheHelper {


    private static final LRUCache lru = new LRUCache(10000);

    //缓存，根据需要使用存储方式。这里都只是为了过编译，实际没有使用
    private static final CacheMap<String, Object> crossRequestCacheMap = new CacheMap<String, Object>() {
        @Override
        public boolean containsKey(String key) {
            System.out.println("try get from cache");
            return lru.containsKey(key);
        }

        @Override
        public Object get(String key) {
            System.out.println("get from cache");
            return lru.get(key);
        }

        @Override
        public CacheMap<String, Object> set(String key, Object value) {
            System.out.println("set to cache");
            lru.put(key, value);
            return this;
        }

        @Override
        public CacheMap<String, Object> delete(String key) {
            System.out.println("delete from cache");
            lru.remove(key);
            return this;
        }

        @Override
        public CacheMap<String, Object> clear() {
            System.out.println("clear cache");
            lru.clear();
            return this;
        }
    };

    public static CacheMap<String, Object> getCacheMap() {
        return crossRequestCacheMap;
    }
}
