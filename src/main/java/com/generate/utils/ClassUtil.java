package com.generate.utils;

import com.generate.common.exception.CommonException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ClassUtil {

    private static final String _WRITE = "write";
    private static final String _READ  = "read";

    public static Set<String> getAllClsMethodName(Class cls) throws Exception {
        List<Field> allMethodField = getAllClsMethod(cls);

        Assert.isNotNull(allMethodField,"【Read Cls Field Is NUll】",CommonException.class);

        Set<String> methodNames = new HashSet<>();

        allMethodField.forEach(field -> methodNames.add(toLowerCaseFirstOne(field.getName())));
        return methodNames;
    }

    /**
     * 将对象转换为Map
     */
    public static HashMap<String,Object> classTransMap(Object o) throws Exception {
        Assert.isNotNull(o,"【ClassUtil Exception:】classTransMap Object Not Be Null",CommonException.class);
        HashMap<String,Object> nodeValMap = new HashMap<>();
        getAllClsMethod(o.getClass()).forEach(field -> {
            Method GMethod = getTypeMethods(field,_READ,o.getClass());
            if(GMethod == null){
                return;
            }
            String name = field.getName();
            name = name.replace("get","");
            try {
                nodeValMap.put(toLowerCaseFirstOne(name), GMethod.invoke(o));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return nodeValMap;
    }

    private static Method getTypeMethods(Field declaredField, String type, Class<?> aClass){

        PropertyDescriptor pd;
        try {
            if(declaredField == null){
                return null;
            }
            if(!declaredField.isAccessible()){
                declaredField.setAccessible(true);
            }
            pd = new PropertyDescriptor(declaredField.getName(), aClass);
        }catch (Exception e){
            return null;
        }
        if(_READ.equals(type)){
            return pd.getReadMethod();
        }else if(_WRITE.equals(type)){
            return pd.getWriteMethod();
        }
        return null;

    }

    public static List<Field> getAllClsMethod(Class cls) throws Exception {
        Assert.isNotNull(cls,"【ClassUtil Exception:】cls Must Not Cant Null", CommonException.class);

        //获取子类自身的属性
        List<Field> fields = new ArrayList<>(Arrays.asList(cls.getDeclaredFields()));
        //获取父类属性
        while (true){
            Class<?> superCls = cls.getSuperclass();
            if(superCls == null){
                break;
            }
            fields.addAll(Arrays.asList(superCls.getDeclaredFields()));
            cls = superCls;
        }
        return fields;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
