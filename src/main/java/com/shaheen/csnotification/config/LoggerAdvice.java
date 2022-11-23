package com.shaheen.csnotification.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Aspect
@RestControllerAdvice
public class LoggerAdvice {
  Logger log = LoggerFactory.getLogger(LoggerAdvice.class);

  @Pointcut(value = "execution(* com.shaheen.csnotification.controller..*(..))")
  public void loggerAdvicePointCuts() {
    // empty body because its a reference to point cut
  }

  @Around("loggerAdvicePointCuts()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    // before advice
    Class controllerName = joinPoint.getSignature().getDeclaringType();
    String methodName = joinPoint.getSignature().getName();
    String parameters = Arrays.toString(joinPoint.getArgs());
    log.info("START:{}.{}({})", controllerName.getName(), methodName, parameters);
    Object result;
    try {
      // proceed method Or not depend on need
      result = joinPoint.proceed(joinPoint.getArgs());
    } catch (Exception exception) {
      // AfterThrowing
      log.error("EXCEPTION: {}", exception.getMessage(), exception);
      throw exception;
    }
    // after returning
    if (result != null) {
      log.info("END:{}", result);
    }
    return result;
  }
}
