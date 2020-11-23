package com.tianhengyun.common.tang4jbase.support.annotation;

import java.lang.annotation.*;

/**
 * 用来标注字段对应实体类对应的字段,便于反射操作
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EntityField {
    String value() default "";
}
