package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.util.List;

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
Object o=new Character('s');
        System.out.println((Byte)o);
    }

    public void methodThrowsNullPointerException() {
        String s=null;

        System.out.println(s.equals(""));
    }

    public static void main(String[] args) {
VeryComplexClass veryComplexClass=new VeryComplexClass();
        veryComplexClass.methodThrowsClassCastException();
    }
}
