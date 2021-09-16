package com.beers.punk.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PunkLogService {

    Logger log = LoggerFactory.getLogger(PunkLogService.class);

    @Pointcut(value = "execution(* com.beers.punk.*.*.*(..))")
    public void myLog(){

    }

    @Around("myLog()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().getName();
        Object[] array = pjp.getArgs();
        log.info("method invoked "+ className + " : " + methodName + "()" + "arguments : " + mapper.writeValueAsString(array));
        Object object = pjp.proceed();
        log.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(object));

        return object;
    }
}
