package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        String date="";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date parsedData = simpleDateFormat.parse(date);
    }

    public static void main(String[] args) {

    }
}
