package com.model2.mvc.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;

/*
 * FileName : PojoAspectJ.java
 *	:: XML 에 선언적으로 aspect 의 적용   
  */
public class LogAspectJ {

	///Constructor
	public LogAspectJ() {
		System.out.println("\nCommon :: "+getClass().getSimpleName()+"\n");
	}
	
	//Around  Advice
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
			
		System.out.println("");
		System.out.println("[Around before] 타겍객체.메서드 :"+
													joinPoint.getTarget().getClass().getName() +"."+
													joinPoint.getSignature().getName());
		if(joinPoint.getArgs().length !=0){
			System.out.println("[Around before]method에 전달되는 인자 : "+ joinPoint.getArgs()[0]);
		}
		//==> 타겟 객체의 Method 를 호출 하는 부분 
		Object obj = joinPoint.proceed();

		System.out.println("[Around after] 타겟 객체return value  : "+obj);
		System.out.println("");
		
		return obj;
	}
	
	
//	public Object logRequest(ProceedingJoinPoint joinPoint, 
//							 HttpServletRequest request) 
//							 throws Throwable {
//		
//		String uri = request.getRequestURI();
//		
//		System.out.println("\n[ Request URI ] " + uri);
//		
//		return joinPoint.proceed();
//	}
	
}//end of class