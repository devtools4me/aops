package me.devtools4.aops.examples;

import me.devtools4.aops.examples.config.AspectsConfig;
import me.devtools4.aops.examples.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@Import({
    AspectsConfig.class,
    SwaggerConfig.class
})
public class ExampleApp {
  public static void main(String[] args) {
    SpringApplication.run(ExampleApp.class, args);
  }
}