package me.devtools4.aops.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

  String IDEMPOTENT_ANNOTATION = "@annotation(me.devtools4.aops.annotations.Idempotent)";

  @FunctionalInterface
  interface CheckedSupplier<T> {
    T get() throws Throwable;
  }

  interface IdempotentRepository {
    Object getOrProceed(Object[] args, CheckedSupplier<Object> func);
  }
}