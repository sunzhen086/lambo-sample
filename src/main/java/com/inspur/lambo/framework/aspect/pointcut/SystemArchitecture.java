package com.inspur.lambo.framework.aspect.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class SystemArchitecture {
	
	@Pointcut("execution(* *(..))")
	public void anyMethod(){
		
	}
	
	@Pointcut("execution(public * *(..))")
	public void anyPublicMethod(){
		
	}
	
	@Pointcut("execution(* com.inspur..*.service..*(..))")
	public void globalServiceLayer(){
		
	}
	
	@Pointcut("execution(* com.inspur..*.controller..*(..))")
	public void globalControllerLayer(){
		
	}
};

