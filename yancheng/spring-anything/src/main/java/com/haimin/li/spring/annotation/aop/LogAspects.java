package com.haimin.li.spring.annotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * ������
 * @author lfy
 * 
 * @Aspect�� ����Spring��ǰ����һ��������
 *
 */
@Aspect
public class LogAspects {
	
	//��ȡ�������������ʽ
	//1����������
	//2����������������
	@Pointcut("execution(public int com.haimin.li.spring.annotation.aop.MathCalculator.*(..))")
	public void pointCut(){};
	
	//@Before��Ŀ�귽��֮ǰ���룻�������ʽ��ָ�����ĸ��������룩
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		System.out.println(""+joinPoint.getSignature().getName()+"���С�����@Before:�����б��ǣ�{"+Arrays.asList(args)+"}");
	}
	
	@After("com.haimin.li.spring.annotation.aop.LogAspects.pointCut()")
	public void logEnd(JoinPoint joinPoint){
		System.out.println(""+joinPoint.getSignature().getName()+"����������@After");
	}
	
	//JoinPointһ��Ҫ�����ڲ�����ĵ�һλ
	@AfterReturning(value="pointCut()",returning="result")
	public void logReturn(JoinPoint joinPoint, Object result){
		System.out.println(""+joinPoint.getSignature().getName()+"�������ء�����@AfterReturning:���н����{"+result+"}");
	}
	
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(JoinPoint joinPoint, Exception exception){
		System.out.println(""+joinPoint.getSignature().getName()+"�쳣�������쳣��Ϣ��{"+exception+"}");
	}

}
