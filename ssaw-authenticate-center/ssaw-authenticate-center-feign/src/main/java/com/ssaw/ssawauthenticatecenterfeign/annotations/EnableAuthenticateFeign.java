package com.ssaw.ssawauthenticatecenterfeign.annotations;

import java.lang.annotation.*;

/**
 * @author HuSen
 * @date 2019/4/27 15:45
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAuthenticateFeign {
}
