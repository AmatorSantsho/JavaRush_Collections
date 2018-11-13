package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static   <T> ArrayList <T> newArrayList(  T... elements) {
        //напишите тут ваш код
        ArrayList <T> list=new ArrayList<>();
        for (Object o:elements){
            list.add((T)o);
        }


        return list;
    }

    public static  <T> HashSet<T>   newHashSet(T... elements) {
        //напишите тут ваш код
        HashSet<T> hashSet=new HashSet<>();
        for (Object o:elements)
        hashSet.add((T)o);
        return hashSet;
    }

    public static <K,V> HashMap<K,V> newHashMap(List <? extends K> keys, List <? extends V> values) {
        HashMap <K,V> map=new HashMap<>();
        if (keys.size()!=values.size())
        throw new IllegalArgumentException();
        for (int i = 0; i <keys.size() ; i++) {
            map.put(keys.get(i),values.get(i));

        }

        //напишите тут ваш код
        return map;
    }
}
