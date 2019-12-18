package me.devtools4.aops.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimerMetric {

  String TIMER_METRIC_ANNOTATION = "@annotation(me.devtools4.aops.annotations.TimerMetric)";

  @FunctionalInterface
  interface TimerSupplier<T> {
    T get(Class<?> clazz, String... names);
  }
}