package info.zhwan.java.time;

import org.junit.Test;
import org.springframework.data.jpa.repository.Temporal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTest {
  @Test
  public void testLocalDate() throws Exception {

    LocalDate now = LocalDate.now();
    System.err.println("now is " + now);


    System.err.println("dayOfWeek " + now.getDayOfWeek());
    System.err.println("dayOfMonth " + now.getDayOfMonth());
    System.err.println("dayOfYear " + now.getDayOfYear());

    LocalDate nextFriday = now.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    System.err.println("with " + nextFriday);
  }
}
