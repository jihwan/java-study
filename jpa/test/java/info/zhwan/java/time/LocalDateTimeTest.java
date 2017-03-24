package info.zhwan.java.time;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {
  @Test
  public void testLocalDateTime() throws Exception {

    LocalDateTime now = LocalDateTime.now();
    System.err.println("now " + now);


    String format = DateTimeFormatter.ISO_DATE_TIME.format(now);
    System.err.println("format " + format);
  }
}
