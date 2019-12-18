package me.devtools4.aops.examples.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import java.util.concurrent.ConcurrentHashMap;
import me.devtools4.aops.annotations.Idempotent.IdempotentSupplier;
import me.devtools4.aops.annotations.TimerMetric.TimerSupplier;
import me.devtools4.aops.aspects.FallbackAspect;
import me.devtools4.aops.aspects.IdempotentAspect;
import me.devtools4.aops.aspects.TimerMetricAspect;
import me.devtools4.aops.aspects.TraceAspect;
import me.devtools4.aops.examples.impl.SimpleIdempotentSupplier;
import me.devtools4.aops.examples.impl.SimpleTimerSupplier;
import org.springframework.context.annotation.Bean;

public class AspectsConfig {
  @Bean
  public TraceAspect traceAspect() {
    return new TraceAspect();
  }

  @Bean
  public TimerMetricAspect timerMetricAspect(TimerSupplier<Timer> timerSupplier) {
    return new TimerMetricAspect(timerSupplier);
  }

  @Bean
  public FallbackAspect fallbackAspect() {
    return new FallbackAspect();
  }

  @Bean
  public IdempotentAspect idempotentAspect(IdempotentSupplier idempotentSupplier) {
    return new IdempotentAspect(idempotentSupplier);
  }

  @Bean
  public IdempotentSupplier idempotentSupplier() {
    return new SimpleIdempotentSupplier(new ConcurrentHashMap<>());
  }

  @Bean
  public TimerSupplier<Timer> simpleTimerSupplier(MetricRegistry metricRegistry) {
    return new SimpleTimerSupplier(metricRegistry, new ConcurrentHashMap<>());
  }

  @Bean
  public MetricRegistry metricRegistry() {
    return new MetricRegistry();
  }
}