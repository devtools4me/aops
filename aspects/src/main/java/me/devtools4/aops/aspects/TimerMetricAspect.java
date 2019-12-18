package me.devtools4.aops.aspects;

import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import me.devtools4.aops.annotations.TimerMetric;
import me.devtools4.aops.annotations.TimerMetric.TimerSupplier;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimerMetricAspect {

  private final TimerSupplier<Timer> func;

  public TimerMetricAspect(TimerSupplier<Timer> func) {
    this.func = func;
  }

  @Around(TimerMetric.TIMER_METRIC_ANNOTATION)
  public Object around(ProceedingJoinPoint point) throws Throwable {
    try (final Context ctx = func.get(point.getTarget().getClass(), point.getSignature().getName()).time()) {
      return point.proceed();
    }
  }
}