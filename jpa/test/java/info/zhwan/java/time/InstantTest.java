package info.zhwan.java.time;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class InstantTest {

  @Test
  public void testInstant() throws Exception {

    Instant now = Instant.now();
    System.err.println("now is " + now);


    TimeUnit.SECONDS.sleep(2);


    Instant after = Instant.now();
    System.err.println("after is " + after);


    Duration between = Duration.between(now, after);
    System.err.println("between is " + between.toMillis());


  }
}
