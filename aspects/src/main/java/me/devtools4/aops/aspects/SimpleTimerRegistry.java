package me.devtools4.aops.aspects;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import java.util.Map;
import me.devtools4.aops.annotations.Instrumentation.TimerRegistry;

public class SimpleTimerRegistry implements TimerRegistry<Timer> {

  private final MetricRegistry registry;
  private final Map<String, Timer> timers;

  public SimpleTimerRegistry(MetricRegistry registry, Map<String, Timer> timers) {
    this.registry = registry;
    this.timers = timers;
  }

  @Override
  public Timer timer(Class<?> clazz, String... names) {
    return timers.computeIfAbsent(MetricRegistry.name(clazz, names), registry::timer);
  }
}