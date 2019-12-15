package me.devtools4.aops.aspects;

import static org.slf4j.LoggerFactory.getLogger;

import me.devtools4.aops.annotations.Trace;
import java.util.Collection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

@Aspect
public class TraceAspect {

  private final static Logger LOG = getLogger(TraceAspect.class);

  @Around(Trace.TRACE_ANNOTATION)
  public Object around(ProceedingJoinPoint point) throws Throwable {
    String signature = point.getSignature().toShortString();
    LOG.info("{} with args: {}", signature, (Object) point.getArgs());

    long start = System.currentTimeMillis();
    Object proceed = point.proceed();
    long executionTime = System.currentTimeMillis() - start;
    LOG.info("{} executed in {} ms, result: [{}]", signature, executionTime, result(proceed));
    return proceed;
  }

  private static Object result(Object obj) {
    if (obj == null) {
      return "null";
    } else if (obj instanceof Collection) {
      return "collection size: " + ((Collection) obj).size();
    } else if (obj.getClass().isArray()) {
      return "array length: " + ((Object[]) obj).length;
    } else {
      return obj;
    }
  }
}