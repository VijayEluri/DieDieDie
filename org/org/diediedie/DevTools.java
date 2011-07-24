// Development and Debugging Tools

package org.diediedie;

import java.lang.reflect.Method;


public abstract class DevTools
{
    public static Method[] getMethods(Object o)
    {
        Class<? extends Object> c = o.getClass();
        return c.getDeclaredMethods();
    }

    public static void printMethods(Object o)
    {
        System.out.println("Methods of " + o.getClass().getName());
        for (Method m : getMethods(o))
        {
            System.out.println("\t." + m);
        }   
    }
}
