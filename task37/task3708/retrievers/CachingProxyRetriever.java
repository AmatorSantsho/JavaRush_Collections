package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

/**
 * Created by 123 on 16.12.2017.
 */
public class CachingProxyRetriever implements Retriever{
    private LRUCache <Long,Object>lruCache =new LRUCache(16);
    private OriginalRetriever originalRetriever;
    private Storage storage;

    public CachingProxyRetriever(Storage storage) {
        originalRetriever=new OriginalRetriever(storage);
        this.storage=storage;
    }

    @Override
    public Object retrieve(long id) {
        Object o=null;
        o=lruCache.find(id);
        if (o==null){
          o= originalRetriever.retrieve(id);
            lruCache.set(id,o);
        }
return o;
    }
}
