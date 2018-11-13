package com.javarush.task.task34.task3409;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/* 
Настраиваем логгер
*/
public class Solution {
    public static void main(String args[]) throws IOException {
        String logProperties = "src/" + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/log4j.properties";
        Path path = Paths.get(logProperties).toAbsolutePath();
        try (InputStream is = new FileInputStream(path.toFile())) {
            Properties properties = new Properties();
//            properties.setProperty("log4jrootloger","WARN,file,stdout");
//            properties.setProperty("log4j.appender.file.File","D:\\log\\runApp.log");
//            properties.setProperty("log4j.appender.file.MaxFileSize","5Mb");
//            properties.setProperty("log4j.appender.file.MaxBackupIndex","6");
//            properties.setProperty("log4j.appender.stdout","org.apache.log4j.ConsoleAppender");
//            properties.setProperty("log4j.appender.stdout.Target","System.out");
//            properties.setProperty("log4j.appender.stdout.threshold","ERROR");

            properties.load(is);
        }
    }
}
