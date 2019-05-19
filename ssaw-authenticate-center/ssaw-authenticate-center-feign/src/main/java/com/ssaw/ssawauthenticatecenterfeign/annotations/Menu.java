package com.ssaw.ssawauthenticatecenterfeign.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HuSen
 * @date 2019/2/26 11:18
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface Menu {
    String index() default "";
    String title() default "";
    String scope() default "";
    String to() default "";
}
