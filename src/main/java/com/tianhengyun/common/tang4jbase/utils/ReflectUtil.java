package com.tianhengyun.common.tang4jbase.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {

    /**
     * 获取子类及其父类的所有字段(含多继承)
     *
     * @param clazz 类
     * @param <T>   类型
     * @return 字段数组
     */
    @SuppressWarnings("unchecked")
    public static <T> Field[] getAllDeclaredFields(Class<T> clazz) {
        List<Field> tempList = new ArrayList<>();
        while (!DataUtil.isEmpty(clazz)) {
            Field[] declaredFields = clazz.getDeclaredFields();
            tempList.addAll(Arrays.asList(declaredFields));
            clazz = (Class<T>) clazz.getSuperclass();
        }
        Field[] allFieldArr = new Field[tempList.size()];
        tempList.toArray(allFieldArr);
        return allFieldArr;
    }

    /**
     * 获取父类的所有字段(含多继承)
     *
     * @param clazz 类
     * @param <T>   类型
     * @return 字段数组
     */
    @SuppressWarnings("unchecked")
    public static <T> Field[] getAllSuperDeclaredFields(Class<T> clazz) {
        List<Field> tempList = new ArrayList<>();
        while (!DataUtil.isEmpty(clazz) && !DataUtil.isEmpty(clazz.getSuperclass())) {
            clazz = (Class<T>) clazz.getSuperclass();
            Field[] declaredFields = clazz.getDeclaredFields();
            tempList.addAll(Arrays.asList(declaredFields));
        }
        Field[] allFieldArr = new Field[tempList.size()];
        tempList.toArray(allFieldArr);
        return allFieldArr;
    }

    public static <T, E> E copyProperties(T t, Class<E> eClass) {

        // 判断传入源数据是否为空，如果空，则抛自定义异常
        if (null == t) {
            throw new RuntimeException("数据源为空");
        }

        // 获取源对象的类的详情信息
        Class<?> sClass = t.getClass();
        // 获取源对象的所有属性
        Field[] sFields = sClass.getDeclaredFields();
        // 获取目标对象的所有属性
        Field[] tFields = eClass.getDeclaredFields();
        E target;
        try {
            // 通过类的详情信息，创建目标对象 这一步等同于UserTwo target = new UserTwo()；
            target = eClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("目标对象创建失败");
        }
        // 循环取到源对象的单个属性
        for (Field sField : sFields) {
            // 循环取到目标对象的单个属性
            for (Field tField : tFields) {
                // 判断源对象的属性名、属性类型是否和目标对象的属性名、属性类型一致
                if (sField.getName().equals(tField.getName()) && sField.getGenericType().equals(tField.getGenericType())) {
                    try {
                        // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
                        String sName = sField.getName();
                        char[] sChars = sName.toCharArray();
                        sChars[0] -= 32;
                        String sMethodName = "get" + String.valueOf(sChars);
                        // 获得属性的get方法
                        Method sMethod = sClass.getMethod(sMethodName);
                        // 调用get方法
                        Object sFieldValue = sMethod.invoke(t);
                        // 获取目标对象的属性名，将属性名首字母大写，拼接如：setUsername、setId的字符串
                        String tName = tField.getName();
                        char[] tChars = tName.toCharArray();
                        tChars[0] -= 32;
                        String tMethodName = "set" + String.valueOf(tChars);
                        // 获得属性的set方法
                        Method tMethod = eClass.getMethod(tMethodName, tField.getType());
                        // 调用方法，并将源对象get方法返回值作为参数传入
                        tMethod.invoke(target, sFieldValue);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(ReflectUtil.class.getName() + "调用copyProperties出现异常:" + e.getMessage());
                    }
                }
            }
        }

        // 返回集合
        return target;


    }


    public static <T, E> List<E> copyProperties(List<T> listT, Class<E> eClass) {

        // 判断传入源数据是否为空，如果空，则抛自定义异常
        if (CollectionUtils.isEmpty(listT)) {
            throw new RuntimeException("数据源为空");
        }
        List<E> listE = new ArrayList<>();
        for (T t : listT) {
            listE.add(copyProperties(t, eClass));
        }
        // 返回集合
        return listE;


    }

}
