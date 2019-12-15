package me.devtools4.aops.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fallback {

  String FALLBACK_ANNOTATION = "@annotation(me.devtools4.aops.annotations.Fallback)";

  Type type() default Type.Log;

  enum Type {
    Log, FuncCall
  }

  @FunctionalInterface
  interface FallbackFunction {
    void apply(Object... other);
  }
}