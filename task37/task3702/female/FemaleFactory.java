package com.javarush.task.task37.task3702.female;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;
import com.javarush.task.task37.task3702.male.KidBoy;
import com.javarush.task.task37.task3702.male.Man;
import com.javarush.task.task37.task3702.male.TeenBoy;

/**
 * Created by 123 on 17.08.2017.
 */
public class FemaleFactory implements AbstractFactory{
    public Human getPerson(int age){
        Human human;
        if (age<= KidGirl.MAX_AGE)
            human=new KidGirl();
        else if (age<= TeenGirl.MAX_AGE&&age>KidGirl.MAX_AGE)
            human=new TeenGirl();
        else
            human=new Woman();
        return human;
    }
}
