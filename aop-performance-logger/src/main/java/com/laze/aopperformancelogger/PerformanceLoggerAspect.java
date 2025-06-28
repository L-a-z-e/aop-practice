package com.laze.aopperformancelogger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLoggerAspect {

    @Pointcut("execution(public * com.laze.aopperformancelogger.service..*.*(..))")
    public void serviceLayerMethods() {}

    @Around("serviceLayerMethods()")
    public Object loggerPerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        try {
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            System.out.println("[Performance Log] " + className + "." + methodName + " executed in " + executionTime + "ms");
        }
    }
}