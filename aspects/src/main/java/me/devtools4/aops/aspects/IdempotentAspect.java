package me.devtools4.aops.aspects;

import me.devtools4.aops.annotations.Idempotent;
import me.devtools4.aops.annotations.Idempotent.IdempotentRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class IdempotentAspect {
  private final IdempotentRepository repository;

  public IdempotentAspect(IdempotentRepository repository) {
    this.repository = repository;
  }

  @Around(Idempotent.IDEMPOTENT_ANNOTATION)
  public Object around(ProceedingJoinPoint point) throws Throwable {
    return repository.getOrProceed(point.getArgs(), point::proceed);
  }
}