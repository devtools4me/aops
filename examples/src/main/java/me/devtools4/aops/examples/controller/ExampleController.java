package me.devtools4.aops.examples.controller;

import static org.slf4j.LoggerFactory.getLogger;

import me.devtools4.aops.annotations.Fallback;
import me.devtools4.aops.annotations.Fallback.Type;
import me.devtools4.aops.annotations.Idempotent;
import me.devtools4.aops.annotations.TimerMetric;
import me.devtools4.aops.annotations.Trace;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
  private final static Logger LOG = getLogger(ExampleController.class);

  @Trace
  @TimerMetric
  @RequestMapping(value = "/trace/{param1}/{param2}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public String traceTest(@PathVariable(name = "param1") String param1, @PathVariable(name = "param2") String param2) {
    return "OK " + param1 + " " + param2;
  }

  @Trace(level = "INFO")
  @TimerMetric
  @RequestMapping(value = "/traceInfo/{param1}/{param2}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public String traceInfoTest(@PathVariable(name = "param1") String param1, @PathVariable(name = "param2") String param2) {
    return "OK " + param1 + " " + param2;
  }

  @Trace(level = "INFO")
  @TimerMetric
  @Idempotent
  @RequestMapping(value = "/idempotent/{param1}/{param2}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public String idempotentTest(@PathVariable(name = "param1") String param1, @PathVariable(name = "param2") String param2) {
    LOG.info("idempotentTest");
    return "OK " + param1 + " " + param2;
  }

  @Trace(level = "INFO")
  @TimerMetric
  @Fallback
  @RequestMapping(value = "/fallbackNone/{param1}/{param2}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public String fallbackNoneTest(@PathVariable(name = "param1") String param1, @PathVariable(name = "param2") String param2) {
    LOG.info("fallbackNoneTest");
    throw new RuntimeException("fallbackNoneTest");
  }

  @Trace(level = "INFO")
  @TimerMetric
  @Fallback(type = Type.Log)
  @RequestMapping(value = "/fallbackLog/{param1}/{param2}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public String fallbackLogTest(@PathVariable(name = "param1") String param1, @PathVariable(name = "param2") String param2) {
    LOG.info("fallbackLogTest");
    throw new RuntimeException("fallbackLogTest");
  }

  @Trace(level = "INFO")
  @TimerMetric
  @Fallback(type = Type.Method, method = "fallback")
  @RequestMapping(value = "/fallbackMethod/{param1}/{param2}",
      produces = MediaType.APPLICATION_JSON_VALUE,
      method = RequestMethod.POST)
  public String fallbackMethodTest(@PathVariable(name = "param1") String param1, @PathVariable(name = "param2") String param2) {
    LOG.info("fallbackMethodTest");
    throw new RuntimeException("fallbackMethodTest");
  }

  public String fallback(String param1, String param2) {
    LOG.info("fallback");
    return param1 + param2;
  }
}