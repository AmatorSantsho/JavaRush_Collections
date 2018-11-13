package com.javarush.task.task33.task3310.strategy;

/**
 * Created by 123 on 06.09.2017.
 */
public interface StorageStrategy {
    boolean containsKey(Long key);
   boolean containsValue(String value);
    Long getKey(String value);
    String getValue(Long key);
    void put(Long key, String value);
}
