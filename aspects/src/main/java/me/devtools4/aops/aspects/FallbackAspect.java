package me.devtools4.aops.aspects;

import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Method;
import me.devtools4.aops.annotations.Fallback;
import me.devtools4.aops.annotations.Fallback.Type;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

@Aspect
public class FallbackAspect {
  @Around(Fallback.FALLBACK_ANNOTATION)
  public Object around(ProceedingJoinPoint point) throws Throwable {
    MethodSignature sig = (MethodSignature) point.getSignature();
    Fallback fallback = sig.getMethod().getAnnotation(Fallback.class);
    Type type = fallback.type();
    if (Type.None == type) {
      return point.proceed();
    }

    try {
      return point.proceed();
    } catch (Exception ex) {
      String msg = sig.toShortString() + " failed, error=" +
          StringUtils.abbreviate(ExceptionUtils.getStackTrace(ex), fallback.maxSize());

      Logger logger = getLogger(point.getTarget().getClass());
      if (Type.Method == type) {
        logger.warn(msg);
        Object target = point.getTarget();
        Method method = target.getClass().getMethod(fallback.method(), sig.getParameterTypes());
        return method.invoke(target, point.getArgs());
      } else if (Type.Log == type) {
        logger.error(msg);
      }
      throw ex;
    }
  }
}