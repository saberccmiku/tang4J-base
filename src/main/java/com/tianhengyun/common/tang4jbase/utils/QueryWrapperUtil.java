package com.tianhengyun.common.tang4jbase.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tianhengyun.common.tang4jbase.exception.ValidateException;
import com.tianhengyun.common.tang4jbase.support.Operator;
import com.tianhengyun.common.tang4jbase.support.annotation.Query;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author fjy
 */
public class QueryWrapperUtil {

    /**
     * 全字段匹配的sql
     *
     * @param t   表实体类
     * @param <T> 表实体类
     * @return 表实体类全字段匹配sql
     */
    public static <T> Wrapper<T> initNormalQuerySql(T t) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        //通过反射获取子类及其父类变量字段
        Field[] allDeclaredFields = ReflectUtil.getAllDeclaredFields(t.getClass());
        StringBuffer sb;
        for (Field field : allDeclaredFields) {
            sb = new StringBuffer();
            try {
                field.setAccessible(true);
                Object obj = field.get(t);
                //成员变量不是空并且非final态变量
                if (!DataUtil.isEmpty(obj) && !Modifier.isFinal(field.getModifiers())) {

                    //判断字段是否被mybaits的TableField标注了
                    boolean isIdField = field.isAnnotationPresent(TableField.class);
                    boolean isId = field.isAnnotationPresent(TableId.class);
                    if (isIdField||isId) {
                        if (isIdField){
                            TableField tableField = field.getAnnotation(TableField.class);
                            //被mybaits的TableField标注并且注释不在数据库的字段中的成员变量不应该纳入查询范围
                            if (tableField.exist()) {
                                wrapper.eq(sb.append(tableField.value()).toString(), obj);
                            }
                        }else {
                            TableId tableId = field.getAnnotation(TableId.class);
                            wrapper.eq(sb.append(tableId.value()).toString(), obj);
                        }

                    } else {
                        //这里当实体类成员变量不是来自数据库就会报错 调用该方法时需要注意 可以使用上面的方面过滤
                        wrapper.eq(sb.append(UnderscoreUtil.underscoreName(field.getName())).toString(), obj);
                    }
                    //排序 默认按照order_no字段 如果没有则需要指定
                    if (obj.equals("orderNo")) {
                        wrapper.orderByAsc("order_no");
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
            field.setAccessible(false);
        }
        return wrapper;
    }


    /**
     * 全字段模糊匹配的sql
     *
     * @param t   表实体类
     * @param <T> 表实体类
     * @return 表实体类全字段模糊匹配sql
     */
    public static <T> Wrapper<T> initFuzzyQuerySql(T t) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (!Objects.isNull(t)) {
            //通过反射获取子类及其父类变量字段
            Field[] allDeclaredFields = ReflectUtil.getAllDeclaredFields(t.getClass());
            StringBuffer sb;
            for (Field field : allDeclaredFields) {
                sb = new StringBuffer();
                try {
                    field.setAccessible(true);
                    Object obj = field.get(t);
                    //成员变量不是空并且非final态变量
                    if (!DataUtil.isEmpty(obj) && !Modifier.isFinal(field.getModifiers())) {

                        //判断字段是否被mybaits的TableField标注了
                        boolean isIdField = field.isAnnotationPresent(TableField.class);
                        boolean isId = field.isAnnotationPresent(TableId.class);
                        if (isIdField||isId) {
                            if (isIdField){
                                TableField tableField = field.getAnnotation(TableField.class);
                                //被mybaits的TableField标注并且注释不在数据库的字段中的成员变量不应该纳入查询范围
                                if (tableField.exist()) {
                                    wrapper.like(sb.append(tableField.value()).toString(), obj);
                                }
                            }else {
                                TableId tableId = field.getAnnotation(TableId.class);
                                wrapper.like(sb.append(tableId.value()).toString(), obj);
                            }
                        } else {
                            //这里当实体类成员变量不是来自数据库就会报错 调用该方法时需要注意 可以使用上面的方面过滤
                            wrapper.like(sb.append(UnderscoreUtil.underscoreName(field.getName())).toString(), obj);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }

                field.setAccessible(false);
            }
        }
        return wrapper;

    }


    /**
     * queryWrapper调用多参数多类型函数//TODO
     */
    public static <T, M> QueryWrapper<T> createQueryWrapper(M request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        //解决单独使用排序等sql语句时无效的问题 相当于 where 1 = 1
        queryWrapper.eq("1", 1);
        //获取sql操作符
        Field[] declaredFields = request.getClass().getDeclaredFields();
        //值为空情况可参入操作的sql操作符
        String[] operatorArr = new String[]{Operator.groupBy.getName(),
                Operator.orderByAsc.getName(), Operator.orderByDesc.getName(),
                Operator.isNull.getName(), Operator.isNotNull.getName()};
        List<String> operatorList = Arrays.asList(operatorArr);
        //反射执行方式所需的参数
        List<Object> args = new ArrayList<>();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Query.class)) {
                String operator = field.getAnnotation(Query.class).value();
                // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
                String sName = field.getName();
                char[] sChars = sName.toCharArray();
                sChars[0] -= 32;
                String sMethodName = "get" + String.valueOf(sChars);
                // 获得属性的get方法
                Method sMethod = request.getClass().getMethod(sMethodName);
                // 调用get方法
                Object sFieldValue = sMethod.invoke(request);
                if (!DataUtil.isEmpty(sFieldValue) || operatorList.contains(operator)) {//空字符不参入sql操作
                    //获取对应操作符的QueryWrapper方法
                    // (因为方法可能在上级或更上级的父类并且同名的方法很多，所以我们需要进一步判断参数类型)
                    Method[] methods = queryWrapper.getClass().getMethods();
                    for (Method method : methods) {
                        if (method.getName().equalsIgnoreCase(operator)) {
                            Type[] types = method.getParameterTypes();
                            //判断参数类型
                            //queryWrapper方法存在则执行queryWrapper对应的方法
                            if (types.length == Operator.getOperator(operator).getParameter() &&
                                    types[0].getTypeName().equalsIgnoreCase(Object.class.getName())) {
                                Object[] arr = (Object[]) sFieldValue;
                                args.add(0, field.getName());
                                args.addAll(Arrays.asList(arr));
                                method.invoke(queryWrapper, args.toArray());
                                args.clear();//及时GC
                                break;
                            }
                        }
                    }
                }
            }
        }
        return queryWrapper;
    }

    /**
     * 从入参实体类获取主键
     *
     * @param request 入参实体类
     * @param <T>     入参实体类
     * @return 主键
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> Serializable getPrimaryKey(T request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] declaredFields = request.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
            String sName = field.getName();
            if (field.isAnnotationPresent(TableId.class)) {
                char[] sChars = sName.toCharArray();
                sChars[0] -= 32;
                String sMethodName = "get" + String.valueOf(sChars);
                // 获得属性的get方法
                Method sMethod = request.getClass().getMethod(sMethodName);
                // 调用get方法
                Object sFieldValue = sMethod.invoke(request);
                return (Serializable) sFieldValue;
            }

        }
        throw new ValidateException("未找到主键标识@TableId");
    }
}
