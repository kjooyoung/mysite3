package com.douzone.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.METHOD, ElementType.TYPE} ) // 어디에 사용할 꺼냐
@Retention( RetentionPolicy.RUNTIME ) //어느 시점에 사용할꺼냐
public @interface Auth {
	public enum Role { ADMIN, USER }
	Role value() default Role.USER;
	
	/* test */
//	String value() default "";
//	int test() default 1;
}
