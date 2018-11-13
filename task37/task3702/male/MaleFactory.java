package com.javarush.task.task37.task3702.male;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;

/**
 * Created by 123 on 17.08.2017.
 */
public class MaleFactory implements AbstractFactory{
    public Human getPerson(int age){
        Human human;
        if (age<=KidBoy.MAX_AGE)
            human=new KidBoy();
        else if (age<=TeenBoy.MAX_AGE&&age>KidBoy.MAX_AGE)
        human=new TeenBoy();
        else
            human=new Man();
        return human;
    }
}
