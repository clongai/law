package com.law.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.law.common.ComponentType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Component {

	public String value() default "";
	
	public ComponentType type() default ComponentType.OTHER;
}
