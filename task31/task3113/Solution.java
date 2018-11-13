package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        Path dir = Paths.get(s);
        if (!Files.isDirectory(dir)) {
            System.out.println(s + " - не папка");
            return;
        }
final long[]a=new long[]{-1,0,0};
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
a[0]++;

                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
a[1]++;
                    a[2] = a[2]+Files.size(file);

                    return super.visitFile(file, attrs);
                }
            });





            System.out.println("Всего папок - " + a[0]);
            System.out.println("Всего файлов - " + a[1]);
            System.out.println("Общий размер - " + a[2]);


        }

}
