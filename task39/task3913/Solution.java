package com.javarush.task.task39.task3913;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) throws IOException {
        LogParser logParser = new LogParser(Paths.get("C:\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));
        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null,null));
    }
}