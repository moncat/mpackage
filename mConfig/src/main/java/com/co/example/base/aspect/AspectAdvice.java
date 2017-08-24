package com.co.example.base.aspect;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


/**
 * @author zyl
 * 根据实际需要开放注解使用
 */
@Slf4j
//@Aspect
//@Configuration
public class AspectAdvice {
	/**
	 * <a href = 'http://www.cnblogs.com/lic309/p/4079194.html'>参考博客</a><br/>
	 * -@Before：前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）。<br/>
	 * -@AfterReturning ：后置通知：在某连接点正常完成后执行的通知，通常在一个匹配的方法返回的时候执行。<br/>
	 * -@AfterThrowing:异常通知：在方法抛出异常退出时执行的通知。　<br/>　　　　　　
	 * -@After 最终通知。当某连接点退出的时候执行的通知(不论是正常返回还是异常退出)<br/>
	 *  <strong>切入点指示符</strong>：<br/>
	 *  execution - 匹配方法执行的连接点，这是你将会用到的Spring的最主要的切入点指示符。<br/>
		within - 限定匹配特定类型的连接点（在使用Spring AOP的时候，在匹配的类型中定义的方法的执行）。<br/>
		this - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中bean reference（Spring AOP 代理）是指定类型的实例。<br/>
		target - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中目标对象（被代理的应用对象）是指定类型的实例。<br/>
		args - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中参数是指定类型的实例。传递实参！！<br/>    
		-@target - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中正执行对象的类持有指定类型的注解。<br/>
		-@args - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中实际传入参数的运行时类型持有指定类型的注解。<br/>
		-@within - 限定匹配特定的连接点，其中连接点所在类型已指定注解（在使用Spring AOP的时候，所执行的方法所在类型已指定注解）。<br/>
		-@annotation - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中连接点的主题持有指定的注解。<br/>
		-@bean：通过受管Bean的名字来限定连接点所在的Bean。该关键词是Spring2.5新增的。
	 */
	//定义切点
	@Pointcut("execution(public * com.co.example.service..*.*(..))")
    public void executeService() {}
	
	
    /**
     * 前置通知，方法调用前被调用
     * @param joinPoint
     */
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        log.debug("我是前置通知!!!");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        System.out.println(signature.getName());
        //AOP代理类的名字
        System.out.println(signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> parameterMap = Maps.newHashMap();
        while (enumeration.hasMoreElements()){
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter,request.getParameter(parameter));
        }
        String str = JSON.toJSONString(parameterMap);
        if(obj.length > 0) {
            System.out.println("请求的参数信息为："+str);
        }
    }
    
    @After("executeService()")
    public void doAfterAdvice(JoinPoint joinPoint){
    	log.debug("我是后置通知!!!");
    }
	
    
    @Around("executeService()")
    public void around(ProceedingJoinPoint pjp) throws Throwable{
        log.debug("@Around 方法执行前");
        pjp.proceed();
        log.debug("@Around 方法执行后");
    }
    
    
    
    
    
}
