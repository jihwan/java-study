package info.zhwan.java.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhwan on 17. 1. 3.
 */
public class CountDownLatchTest {

  static final int TASKS = 10;

  @Test
  public void test() {
    final ExecutorService executorService = Executors.newFixedThreadPool(5);
    final CountDownLatch countDownLatch = new CountDownLatch(TASKS);
    try {
      System.out.println( TASKS + " tasks job start" );
      for (int i = 0; i < TASKS; i++) {
        executorService.execute(new CountDownLatchTestTask(countDownLatch, i));
      }
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
      System.out.println( TASKS + " tasks job end" );
    }
  }
}

class CountDownLatchTestTask implements Runnable {
  final CountDownLatch countDownLatch;
  final int jobId;
  public CountDownLatchTestTask(CountDownLatch countDownLatch, int jobId) {
    this.countDownLatch = countDownLatch;
    this.jobId = jobId;
  }

  @Override
  public void run() {
    doJob();
    countDownLatch.countDown();
  }

  private void doJob() {
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      System.out.println( "JobId is " + jobId + " done." );
    }
  }
}
