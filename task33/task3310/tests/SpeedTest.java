package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 17.09.2017.
 */
public class SpeedTest {
   public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids){
       Date start = new Date();
       for (String s:strings){
           ids.add(shortener.getId(s)) ;
       }
       Date end = new Date();
       return end.getTime() - start.getTime();
    }
   public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
       Date start = new Date();
       for (Long l:ids){
           strings.add(shortener.getString(l));

       }
       Date end = new Date();
       return end.getTime() - start.getTime();
   }
@Test
  public void   testHashMapStorage() {
    HashMapStorageStrategy hashMapStorageStrategy=new HashMapStorageStrategy();
    HashBiMapStorageStrategy hashBiMapStorageStrategy=new HashBiMapStorageStrategy();
    Shortener shortener1 =new Shortener(hashMapStorageStrategy);
    Shortener shortener2 =new Shortener(hashBiMapStorageStrategy);
    Set<String> origStrings = new HashSet<>();
    for (long i = 0; i < 10000; i++) {
        origStrings.add(Helper.generateRandomString());
    }
   Set<Long> ids1= new HashSet<Long>();
    Set<Long> ids2= new HashSet<Long>();

    long time1=getTimeForGettingIds(shortener1,origStrings,ids1);
    long time2=getTimeForGettingIds(shortener2,origStrings,ids2);
Assert.assertTrue(time1>time2);
    long time11=getTimeForGettingStrings(shortener1,ids1,new HashSet<String>());
    long time22=getTimeForGettingStrings(shortener2,ids2,new HashSet<String>());
Assert.assertEquals(time11,time22,30f);
}


   }

