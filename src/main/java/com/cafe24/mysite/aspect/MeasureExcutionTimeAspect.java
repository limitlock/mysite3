package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExcutionTimeAspect {

	@Around("execution(* *..repository.*.*(..))||execution(* *..service.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		// before advice code
		StopWatch sw = new StopWatch();
		sw.start();

		Object result = pjp.proceed();

		// after advice code
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;

		System.out.println("[ExecutionTiem]" + "[" + taskName + "]" + totalTime);

		return result;

	}

}
