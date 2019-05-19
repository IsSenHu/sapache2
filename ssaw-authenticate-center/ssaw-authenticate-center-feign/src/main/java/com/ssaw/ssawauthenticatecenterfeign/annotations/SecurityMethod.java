package com.ssaw.ssawauthenticatecenterfeign.annotations;

import java.lang.annotation.*;

/**
 * @author HuSen
 * @date 2019/2/26 10:39
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityMethod {
    String antMatcher() default "";
    String scope() default "";
    String button() default "";
    String buttonName() default "";
}
