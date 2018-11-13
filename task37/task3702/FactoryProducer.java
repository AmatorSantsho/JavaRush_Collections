package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.FemaleFactory;
import com.javarush.task.task37.task3702.male.MaleFactory;

/**
 * Created by 123 on 18.08.2017.
 */
public class FactoryProducer {
    public static enum HumanFactoryType{
        MALE, FEMALE

    }

    public static AbstractFactory getFactory(HumanFactoryType e){
        if (e.equals(HumanFactoryType.MALE))
        return new MaleFactory();
        else return new FemaleFactory();
    }

}
