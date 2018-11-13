package com.javarush.task.task33.task3310.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 123 on 07.09.2017.
 */
public class HashMapStorageStrategy implements StorageStrategy {

    private HashMap<Long, String> data = new HashMap<>();




    @Override
    public boolean containsKey(Long key) {
        if (data.containsKey(key))
        return true;
        else
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        if (data.containsValue(value))
            return true;
        else
            return false;
    }

    @Override
    public Long getKey(String value) {
        Long key = -1L;
        if (data.containsValue(value)) {


            for (Map.Entry<Long, String> paar : data.entrySet()) {
                if (paar.getValue() == value) {
                    key = paar.getKey();
                }
            }
            return key;
        }else return null;


    }

    @Override
    public String getValue(Long key) {
        return data.get(key);
    }

    @Override
    public void put(Long key, String value) {

        data.put(key,value);

    }
}
