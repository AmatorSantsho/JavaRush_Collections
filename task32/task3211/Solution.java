package com.javarush.task.task32.task3211;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/* 
Целостность информации
*/

public class Solution {
    public static void main(String... args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new String("test string"));
        oos.flush();
        System.out.println(compareMD5(bos, "5a47d12a2e3f9fecf2d9ba1fd98152eb")); //true

    }

    public static boolean compareMD5(ByteArrayOutputStream byteArrayOutputStream, String md5) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(byteArrayOutputStream.toByteArray());
        byte[] a=messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <a.length ; i++) {
            String s = Integer.toHexString(0xff&a[i]);
            System.out.println("s before: " + s);
            s=(s.length()==1)? "0"+s:s;
            System.out.println("s after: " + s);

            stringBuffer.append(s);
        }
        if (stringBuffer.toString().equals(md5))
            return true;
        else
        return false;
    }
}
