package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by 123 on 07.09.2017.
 */
public class Helper {
    public static String generateRandomString(){
         final SecureRandom secureRandom = new SecureRandom();
        BigInteger bInt = new BigInteger(130, secureRandom);
        return bInt.toString(32);
    }

    public static void printMessage(String message){
        System.out.print(message);
    }
}
