package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 06.09.2017.
 */
public class Solution {

    public static  Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> set=new HashSet<>();
        for (String s:strings){
           set.add(shortener.getId(s)) ;
        }
        return set;
    }
    public static  Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> set=new HashSet<>();
        for (Long l:keys){
            set.add(shortener.getString(l));

        }
     return set;
    }
    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> setValuesOriginal = new HashSet<>();
        for (long i = 0; i < elementsNumber; i++)
            setValuesOriginal.add(Helper.generateRandomString());
        Shortener shortener = new Shortener(strategy);
        Date start = new Date();
        Set<Long>  setKeys = getIds(shortener, setValuesOriginal);
        Date end = new Date();
        long workTime = end.getTime() - start.getTime();
        Helper.printMessage(String.valueOf(workTime));
        start = new Date();
        Set<String> setValues = getStrings(shortener, setKeys);
        end = new Date();
        workTime = end.getTime() - start.getTime();
        Helper.printMessage(String.valueOf(workTime));
        boolean isContains = true;
        for (String str : setValues) {
            if (!setValuesOriginal.contains(str)) {
                Helper.printMessage("Тест не пройден.");
                isContains = false;
                break;
            }
        }
        if (isContains)
            Helper.printMessage("Тест пройден.");
    }



    public static void main(String[] args) {
 HashMapStorageStrategy hms=       new HashMapStorageStrategy();

testStrategy(hms,10000);
    }
}
