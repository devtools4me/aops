package me.devtools4.aops.aspects;

import me.devtools4.aops.annotations.Idempotent;
import me.devtools4.aops.annotations.Idempotent.IdempotentSupplier;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class IdempotentAspect {

  private final IdempotentSupplier func;

  public IdempotentAspect(IdempotentSupplier func) {
    this.func = func;
  }

  @Around(Idempotent.IDEMPOTENT_ANNOTATION)
  public Object around(ProceedingJoinPoint point) {
    return func.getIfAbsent(point.getTarget().getClass(),
        point.getSignature().toShortString(),
        point.getArgs(),
        point::proceed);
  }
}