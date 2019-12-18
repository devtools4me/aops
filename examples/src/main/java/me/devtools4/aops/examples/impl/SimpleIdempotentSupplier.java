package me.devtools4.aops.examples.impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import me.devtools4.aops.annotations.Idempotent.CheckedSupplier;
import me.devtools4.aops.annotations.Idempotent.IdempotentSupplier;
import org.apache.commons.codec.digest.HmacUtils;

public class SimpleIdempotentSupplier implements IdempotentSupplier {
  private final Map<String, Object> map;

  public SimpleIdempotentSupplier(Map<String, Object> map) {
    this.map = map;
  }

  @Override
  public Object getIfAbsent(Class<?> clazz, String signature, Object[] args, CheckedSupplier<Object> func) {
    String key = clazz.getCanonicalName() + "-" + signature + "-" +
        hmac(Arrays.stream(args)
        .map(Object::toString)
        .reduce("", (s1, s2) -> s1 + s2));
    map.computeIfAbsent(key, x -> func.unchecked().get());
    return map.get(key);
  }

  private static String hmac(String s) {
    return Base64.getEncoder().encodeToString(HmacUtils.getHmacSha256(s.getBytes()).doFinal());
  }
}