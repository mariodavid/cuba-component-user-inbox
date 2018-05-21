package de.diedavids.cuba.userinbox.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Shareable {
    String listComponent() default "";
    String buttonId() default "shareBtn";
    String buttonsPanel() default "buttonsPanel";
}