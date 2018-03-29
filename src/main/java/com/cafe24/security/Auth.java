package com.cafe24.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cafe24.security.Auth.Role;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
	
	//@Auth(role=Role.ADMIN) or @Auth(role=Role.USER)
	public enum Role {ADMIN, USER} //레벨 처리
	
	public Role role() default Role.USER;
	
//	//@Auth(value = "user")
//	public String value() default "user"; 
//	
//	//@Auth(value = "user", test = 10)
//	public int test() default 1;

}
