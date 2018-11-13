package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        String number = args[1];
        String text = args[2];
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        randomAccessFile.seek(Long.parseLong(number));
        byte []b=new byte[text.length()];
randomAccessFile.read(b,0,text.length());
        if(convertByteToString(b).equals(text)){
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write("true".getBytes());
        }else {
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write("false".getBytes());
        }
        randomAccessFile.close();
    }
    public static String convertByteToString(byte readBytes[])
    {
        return new String(readBytes);
    }
}

