package org.maveric.currencyexchange.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* org.maveric..*.*(..))")
    public void logging() {
    }

    @Before("logging()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getClass().getSimpleName();
        logger.info("Entering method: '{}\'' class: '{}\''", methodName, className);
    }

    @After("logging()")
    public void logMethodExit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getClass().getSimpleName();
        logger.info("Exiting method: '{}\'' class: '{}\''", methodName, className);
    }
}