package com.ssaw.ssawauthenticatecenterfeign.annotations;

import java.lang.annotation.*;

/**
 * @author HuSen
 * @date 2019/2/26 11:00
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityApi {

    /** 该API的分组 */
    String group() default "";

    /** 菜单 */
    Menu[] menu() default {};

    /** 顺序 */
    String index() default "";
}