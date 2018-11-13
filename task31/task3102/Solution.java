package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> list = new ArrayList<>();
        Queue<File> queue = new PriorityQueue<>();
        File path = new File(root);
File []files =path.listFiles();
        for (int i = 0; i <files.length ; i++) {
            if (files[i].isFile()){
list.add(files[i].getAbsolutePath());
            } else {
queue.offer(files[i]);
                while (!queue.isEmpty()){
                   File  currentfile = queue.remove();
                    if (currentfile.isDirectory()){Collections.addAll(queue,currentfile.listFiles());}
                    else {
                        list.add(currentfile.getAbsolutePath());
                    }


                }
            }
        }






        return list;

    }

    public static void main(String[] args) {
        
    }
}
