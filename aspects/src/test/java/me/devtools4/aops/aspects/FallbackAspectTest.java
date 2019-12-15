package me.devtools4.aops.aspects;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestApp.class})
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FallbackAspectTest {
  @Test
  public void test() {}
}