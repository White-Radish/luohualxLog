package com.carrot.luohualx.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * @author carrot
 * @date 2022/6/17
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ComPareFieldDesc {
    /**
     * 字段描述
     * @return
     */
    String desc() default "";

    /**
     * 映射字段 针对某些需要翻译成中文的字段，比如字典数据等 存的是id，展示却要中文
     * @return
     */
    String mappingField()default "";

    /**
     * 需要执行转换，转换执行查询类的bean
     * @return
     */
    Class transferBean() default Object.class;

    /**
     * 使用的方法
     * @return
     */
    String methodName() default "";

    /**
     * 方法参数名
     * @return
     */
    String paramName() default "";

    /**
     * 查询方法如果返回的是对象，需要使用哪个属性
     * @return
     */
    String returnValueColumn() default "";
}
