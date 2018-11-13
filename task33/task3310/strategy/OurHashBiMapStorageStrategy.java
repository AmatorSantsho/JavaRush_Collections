package com.javarush.task.task33.task3310.strategy;

import java.util.HashMap;

/**
 * Created by 123 on 14.09.2017.
 */
public class OurHashBiMapStorageStrategy implements StorageStrategy {

    private HashMap<Long, String> k2v =new HashMap<>();
    private HashMap<String, Long> v2k=new HashMap<>();



    @Override
    public boolean containsKey(Long key) {

        if (k2v.containsKey(key))
        return true;
        else return false;
    }

    @Override
    public boolean containsValue(String value) {
        if (value==null)
            return false;
        if (v2k.containsKey(value))
            return true;
        else return false;
    }

    @Override
    public Long getKey(String value) {

            return v2k.get(value);

    }

    @Override
    public String getValue(Long key) {


            return k2v.get(key);

    }

    @Override
    public void put(Long key, String value) {
k2v.put(key,value);
        v2k.put(value,key);
    }
}
