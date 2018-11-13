package com.javarush.task.task33.task3310.strategy;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Map;
import java.util.Set;

/**
 * Created by 123 on 16.09.2017.
 */
public class HashBiMapStorageStrategy implements StorageStrategy {

   private HashBiMap<Long,String> data=HashBiMap.create();


    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value) ;
    }

    @Override
    public Long getKey(String value) {
BiMap<String,Long> biMap=data.inverse();
 return biMap.get(value);


    }

    @Override
    public String getValue(Long key) {
        return (String)data.get(key);
    }

    @Override
    public void put(Long key, String value) {
data.put(key,value);
    }
}
