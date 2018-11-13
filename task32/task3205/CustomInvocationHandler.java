package com.javarush.task.task32.task3205;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 123 on 30.07.2017.
 */
public class CustomInvocationHandler implements InvocationHandler {

    SomeInterfaceWithMethods someInterfaceWithMethods;

    public CustomInvocationHandler(SomeInterfaceWithMethods someInterfaceWithMethods) {
        this.someInterfaceWithMethods = someInterfaceWithMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + " in");
         Object o=method.invoke(someInterfaceWithMethods, args);

        System.out.println(method.getName() + " out");
        return o;
    }
}
