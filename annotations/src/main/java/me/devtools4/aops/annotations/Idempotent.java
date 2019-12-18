package me.devtools4.aops.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

  String IDEMPOTENT_ANNOTATION = "@annotation(me.devtools4.aops.annotations.Idempotent)";

  @FunctionalInterface
  interface CheckedSupplier<T> {
    T get() throws Throwable;
    default Supplier<T> unchecked() {
      return () -> {
        try {
          return this.get();
        } catch (Throwable ex) {
          throw new IllegalArgumentException(ex);
        }
      };
    }
  }

  @FunctionalInterface
  interface IdempotentSupplier {
    Object getIfAbsent(Object[] args, CheckedSupplier<Object> func);
  }
}