package com.javarush.task.task33.task3303;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

/* 
Десериализация JSON объекта
*/
public class Solution {
    public static <T> T convertFromJsonToNormal(String fileName, Class<T> clazz) throws IOException {
       File file = new File(fileName);

        ObjectMapper objectMapper = new ObjectMapper();
         T o=objectMapper.readValue(file,clazz);


        return o;
    }

    public static void main(String[] args) {

    }
}
