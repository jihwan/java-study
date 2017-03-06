package info.zhwan.java.concurrent;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by zhwan on 17. 1. 3.
 */
public class CyclicBarrierTest {
  static final int THREADS = 3;

  @Test
  public void test() {
    System.out.println("BEGIN");

    // 일을 실행하는 쓰레드를 제공하는 ExecutorService (쓰레드풀)
    ExecutorService service = Executors.newFixedThreadPool(THREADS);

    // barrier가 해제될 때 실행될 액션 (Runnable 객체)
    Runnable barrierAction = new Runnable() {
      public void run() {
        System.out.println("Barrier Action!");
      }
    };

    // 쓰레드들 간에 단계를 맞추기 위한 CyclicBarrier
    CyclicBarrier phaseBarrier = new CyclicBarrier(THREADS, barrierAction);

    // 모든 단계가 CountDownLatch
    CountDownLatch doneLatch = new CountDownLatch(THREADS);

    try {
      for (int t = 0; t < THREADS; t++) {
        service.execute(new CyclicBarrierTestTask(phaseBarrier, doneLatch, t));
      }
      System.out.println("AWAIT");
      doneLatch.await();
    } catch (InterruptedException e) {
    } finally {
      service.shutdown();
      System.out.println("END");
    }
  }

  @Ignore
  @Test
  public void fastTest() throws InterruptedException {
//    3개 스레드로 지정한 CyclicBarrier를 생성
    CyclicBarrier barrier = new CyclicBarrier(3);

    // 대기 시간이 다른 3개의 스레드를 실행
    new CyclicBarrierThread(1000, barrier).start();
    new CyclicBarrierThread(2000, barrier).start();
    new CyclicBarrierThread(3000, barrier).start();


    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
  }

}


class CyclicBarrierThread extends Thread {
  private int jobId;
  private CyclicBarrier barrier;

  public CyclicBarrierThread(int jobId, CyclicBarrier barrier) {
    this.jobId = jobId;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    try {
      while (true) {
        this.barrier.await();
        doJob();
      }
    } catch(InterruptedException | BrokenBarrierException e) {
    }
  }

  private void doJob() {
    try {
      TimeUnit.MILLISECONDS.sleep(jobId);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      System.out.println( "JobId is " + jobId + " done." );
    }
  }
}

class CyclicBarrierTestTask implements Runnable {
  final CyclicBarrier cyclicBarrier;
  final CountDownLatch countDownLatch;
  final int jobId;

  public CyclicBarrierTestTask(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch, int jobId) {
    this.cyclicBarrier = cyclicBarrier;
    this.countDownLatch = countDownLatch;
    this.jobId = jobId;
  }

  @Override
  public void run() {
    try {
      cyclicBarrier.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }

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