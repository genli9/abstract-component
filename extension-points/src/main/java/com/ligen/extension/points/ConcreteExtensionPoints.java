package com.ligen.extension.points;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface ConcreteExtensionPoints {

    /**
     * bizCode name
     */
    String bizCode() default "";
}
