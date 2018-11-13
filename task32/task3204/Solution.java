package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {

ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
String dict = "qwertyuiopasdfghjklzxcvbnm";
        String number = "01234567890";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <6 ; i++) {
char x = dict.charAt((int)(Math.random()*dict.length()));
            stringBuffer.append(x);
        }
stringBuffer.insert((int)(Math.random()*6), Character.toUpperCase(dict.charAt((int) (Math.random() * dict.length()))));

stringBuffer.insert((int)(Math.random()*6), number.charAt((int)(Math.random()*number.length())));
        try {
            byteArrayOutputStream.write(stringBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream;
    }
}