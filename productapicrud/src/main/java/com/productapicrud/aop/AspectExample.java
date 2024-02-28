package com.productapicrud.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectExample {
	
	private static final Logger logger = LoggerFactory.getLogger(AspectExample.class);
	
	@Before(value = " execution(* com.productapicrud.controller.ProductController.*(..)) ")
	public void beforeAdvice(JoinPoint joinPoint) {
		logger.info("inside product controller before advice");
	}
	@After(value = " execution(* com.productapicrud.controller.ProductController.*(..)) ")
	public void afterAdvice(JoinPoint joinPoint) {
		logger.info("inside product controller after advice");
	}
	@AfterReturning(value = " execution(* com.productapicrud.controller.ProductController.*(..)) ")
	public void afterReturning(JoinPoint joinPoint) {
		logger.info("inside product controller after returning");
	}
//	@Around(value = " execution(* com.productapicrud.controller.ProductController.*(..)) ")
//	public void aroundAdvice(ProceedingJoinPoint joinPoint) {
//		logger.info("inside product controller around Advice");
//	}
}
