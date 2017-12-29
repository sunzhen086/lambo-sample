package com.inspur.lambo.framework.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.inspur.lambo.framework.aspect.annotation.LogAround;

@Component
@Aspect
public class LogAroundAspectAdvice {
	
	
	@Around(value = "com.inspur.lambo.framework.aspect.pointcut.SystemArchitecture.anyMethod() "
			+ "&& @annotation(logAround)")
	public Object logAround(ProceedingJoinPoint jp,LogAround logAround) throws Throwable {
		
		Log log = LogFactory.getLog(jp.getTarget().getClass());

		
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		
		String value = logAround.value();
		
		if(log.isInfoEnabled()){
			log.info("进入方法："+value+"----"+methodName+"---"+className);
		}

		long start = System.currentTimeMillis();
			
		Object o = jp.proceed();
		
		long end = System.currentTimeMillis();
		
		if(log.isInfoEnabled()){
			log.info("方法结束："+value+"----"+methodName+"---"+className+",用时："+(end-start)+"毫秒");
		}
		
		return o;
	}
	
}
