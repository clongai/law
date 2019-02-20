package com.law.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.FIELD,
	ElementType.METHOD
})
public @interface EnumField {
	public String codeType() default "";
}
