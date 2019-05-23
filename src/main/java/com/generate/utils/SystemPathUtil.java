package com.generate.utils;

public class SystemPathUtil {

    /**
     * 获取当前对象的路径
     */
    public static String getObjectPath(Object object){
        return object.getClass().getResource(".").getFile();
    }
    /**
     * 获取到项目的路径
     */
    public static String getProjectPath(){
        return System.getProperty("user.dir");
    }
}
