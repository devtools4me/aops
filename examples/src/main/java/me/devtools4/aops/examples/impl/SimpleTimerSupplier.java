package me.devtools4.aops.examples.impl;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import java.util.Map;
import me.devtools4.aops.annotations.TimerMetric.TimerSupplier;

public class SimpleTimerSupplier implements TimerSupplier<Timer> {

  private final MetricRegistry registry;
  private final Map<String, Timer> timers;

  public SimpleTimerSupplier(MetricRegistry registry, Map<String, Timer> timers) {
    this.registry = registry;
    this.timers = timers;
  }

  @Override
  public Timer get(Class<?> clazz, String... names) {
    return timers.computeIfAbsent(MetricRegistry.name(clazz, names), registry::timer);
  }
}