package com.generate.utils;

import com.controller.Controller;

import java.util.HashMap;

public class ClassContext {

    private static HashMap<String,Object> context = new HashMap<>();

    public static void putBean(Object o){
        context.put(o.getClass().getName(),o);
    }

    public static <T extends Controller> T getBean(Class<?> cls){
        return (T)context.get(cls.getName());
    }
}
