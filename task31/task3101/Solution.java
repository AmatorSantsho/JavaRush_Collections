package com.javarush.task.task31.task3101;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        File path =new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
         String s= resultFileAbsolutePath.getParent();
        File destination = new File(s+"/allFilesContent.txt");
      final   ArrayList<File> list = new ArrayList<>();
        FileUtils.renameFile(resultFileAbsolutePath,destination);

       try(FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
           Files.walkFileTree(path.toPath(), new SimpleFileVisitor<Path>() {
               @Override
               public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                   if (file.toFile().length() > 50) {
                       FileUtils.deleteFile(file.toFile());
                   } else {
                       list.add(file.toFile());
                   }
                   return FileVisitResult.CONTINUE;
               }
           });
           Collections.sort(list, new Comparator<File>() {
               @Override
               public int compare(File o1, File o2) {
                   return o1.getName().compareTo(o2.getName());
               }
           });
           for (File f : list) {
               FileInputStream fil = new FileInputStream(f);
               while (fil.available() > 0) {
                   int k = fil.read();
                   fileOutputStream.write(k);
               }
               fileOutputStream.write("\n".getBytes());
               fil.close();
           }
       }





    }

    public static void deleteFile(File file) {
        if (!file.delete()) System.out.println("Can not delete file with name " + file.getName());
    }
}
