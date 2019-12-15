package me.devtools4.aops.aspects;

import static org.slf4j.LoggerFactory.getLogger;

import me.devtools4.aops.annotations.Fallback;
import me.devtools4.aops.annotations.Fallback.FallbackFunction;
import me.devtools4.aops.annotations.Fallback.Type;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

@Aspect
public class FallbackAspect {

  private final static Logger LOG = getLogger(FallbackAspect.class);

  public static final int MAX_WIDTH = 500;

  private final FallbackFunction fallbackFunction;

  public FallbackAspect(FallbackFunction fallbackFunction) {
    this.fallbackFunction = fallbackFunction;
  }

  @Around(Fallback.FALLBACK_ANNOTATION)
  public Object around(ProceedingJoinPoint point) throws Throwable {
    try {
      return point.proceed();
    } catch (Exception ex) {
      String signature = point.getSignature().toShortString();
      String msg = signature + "failed";
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      LOG.error("{}, error={}", msg, StringUtils.abbreviate(stackTrace, MAX_WIDTH));

      Optional.of((MethodSignature) point.getSignature())
          .map(x -> x.getMethod().getAnnotation(Fallback.class))
          .map(Fallback::type)
          .filter(Type.FuncCall::equals)
          .ifPresent(x -> fallbackFunction.apply(msg, stackTrace));

      throw ex;
    }
  }
}