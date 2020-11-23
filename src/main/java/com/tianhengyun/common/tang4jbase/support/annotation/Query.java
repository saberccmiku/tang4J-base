package com.tianhengyun.common.tang4jbase.support.annotation;


import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})//此注解作用于类和字段上
public @interface Query {
    String value() default "eq";
}
