package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.Helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Files;
/**
 * Created by 123 on 11.09.2017.
 */
public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            this.path = Files.createTempFile(Helper.generateRandomString(),null);
            Files.deleteIfExists(path);
            Files.createFile(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize(){

        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public void putEntry(Entry<Long, String> entry){

        try {
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(Files.newOutputStream(path));
            objectOutputStream.writeObject(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry<Long, String> getEntry(){
        if (getFileSize()==0)
            return null;

        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(Files.newInputStream(path));

            Entry<Long, String> e=(Entry<Long, String>)objectInputStream.readObject();
            return e;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
public void remove(){
    try {
        Files.delete(path);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
