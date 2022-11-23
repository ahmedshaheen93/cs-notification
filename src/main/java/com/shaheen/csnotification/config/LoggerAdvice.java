package com.shaheen.csnotification.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;

@Aspect
@ControllerAdvice
@Slf4j
public class LoggerAdvice {
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
