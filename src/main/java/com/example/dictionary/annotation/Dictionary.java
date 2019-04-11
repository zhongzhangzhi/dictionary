package com.example.dictionary.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dictionary {
    /**
     * 字段所属分组，以分组+字段名为唯一键
     */
    String group();
}
