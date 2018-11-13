package com.javarush.task.task38.task3811;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 123 on 19.12.2017.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Ticket {
    Priority priority() default Priority.MEDIUM;
    String createdBy() default "Amigo";
    String [] tags()default {};

    public enum  Priority {
        LOW, MEDIUM, HIGH

    }
}
