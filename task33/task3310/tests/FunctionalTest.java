package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 123 on 17.09.2017.
 */
public class FunctionalTest {
  public void   testStorage(Shortener shortener){
      String s1="aa";
      String s2="bb";
      String s3="aa";
      Long l1=   shortener.getId(s1);
      Long l2=   shortener.getId(s2);
      Long l3=   shortener.getId(s3);
      Assert.assertNotEquals(l1,l2);
      Assert.assertNotEquals(l2,l3);
      Assert.assertEquals(l1, l3);
String ss1=shortener.getString(l1);
      String ss2=shortener.getString(l2);
      String ss3=shortener.getString(l3);
Assert.assertEquals(s1,ss1);
      Assert.assertEquals(s2,ss2);
      Assert.assertEquals(s3,ss3);
  }

   @Test
    public void testHashMapStorageStrategy(){
       HashMapStorageStrategy storageStrategy=new HashMapStorageStrategy();
       Shortener shortener=new Shortener(storageStrategy);
       testStorage(shortener);

   }
    @Test
    public void  testOurHashMapStorageStrategy(){
        OurHashMapStorageStrategy storageStrategy=new OurHashMapStorageStrategy();
        Shortener shortener=new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void  testFileStorageStrategy(){
        FileStorageStrategy storageStrategy=new FileStorageStrategy();
        Shortener shortener=new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void  testHashBiMapStorageStrategy(){
        HashBiMapStorageStrategy storageStrategy=new HashBiMapStorageStrategy();
        Shortener shortener=new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void  testDualHashBidiMapStorageStrategy(){
        DualHashBidiMapStorageStrategy storageStrategy=new DualHashBidiMapStorageStrategy();
        Shortener shortener=new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void  testOurHashBiMapStorageStrategy(){
        OurHashBiMapStorageStrategy storageStrategy=new OurHashBiMapStorageStrategy();
        Shortener shortener=new Shortener(storageStrategy);
        testStorage(shortener);
    }

}
