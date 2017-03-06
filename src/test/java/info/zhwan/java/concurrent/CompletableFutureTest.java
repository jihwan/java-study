package info.zhwan.java.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public class CompletableFutureTest {
  final ExecutorService executor = Executors.newCachedThreadPool();

  @Before
  public void before() {
    log.info("test start");
  }

  @After
  public void after() {
    log.info("test end");
  }

  private void delay(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }


  //-----------------------------------------------------------------------------------------------------------------
  // 연속 연결 : 하나의 타스크 완료 혹은 예외상황을 다른 타스크와 연결

  // 앞 타스크의 결과를 계속 받아서 쓰고, 리턴
  @Test
  public void testPattern1_1() throws InterruptedException {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    CompletableFuture
        .supplyAsync(() -> {
          delay(1);
          log.info("supplyAsync()");
          return "hello";
        }, executor)
        .thenApply(s -> {
          log.info("thenApply() 1");
          return s + " world";
        })
        .thenApply(s -> {
          log.info("thenApply() 2");
          return s + " ~~~";
        })
        .thenAccept(s -> {
          log.info("thenAccept() result : {}", s);
          countDownLatch.countDown();
        });

    countDownLatch.await();
  }

  @Test
  public void testPattern1_2() throws InterruptedException {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    CompletableFuture
        .runAsync(() -> {
          delay(1);
          log.info("call 1");
        }, executor)
        .thenRunAsync(() -> {
          delay(2);
          log.info("call 2");
        })
        .thenRunAsync(() -> {
          delay(3);
          log.info("call 3");
          countDownLatch.countDown();
        });

    countDownLatch.await();
  }

  //-----------------------------------------------------------------------------------------------------------------
  // 타스크 간의 조합 : CompletableFuture 수행 중 완전히 다른 CompletableFuture 를  조합하여 실행
  @Test
  public void testPattern2() throws ExecutionException, InterruptedException {

    CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
      delay(3);
      log.info("cf1");
      return 3;
    }, executor);
    CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
      delay(1);
      log.info("cf2");
      return 1;
    }, executor);
    CompletableFuture cf3= CompletableFuture.supplyAsync(() -> {
      delay(2);
      log.info("cf3");
      return 2;
    }, executor);


    // 각각의 CompletableFuture 가 수행 되기까지 blocking 되어 있다 !!!
    // 그래서 이번 예제에서는 CountDownLatch 가 사용되지 않은 것이다.
    cf3.thenCompose(d1 -> cf1).thenCompose(d2 -> cf2).join();
    log.info( "cf1: {}, cf2: {}, cf: {}", cf1.get(), cf2.get(), cf3.get() );
  }


  //-----------------------------------------------------------------------------------------------------------------
  // 서로 다른 task 결과를 모두 사용하는 task 간 결합 : 서로 다른 task 결과를 모두 기다렸다가, 그 결과를 조합하여 그 다음일을 하는 것
  // 앞의 타스크와 파라미터로 받은 타스크의 결과를 입력값으로 받아서 새로운 결과를 리턴한다. get으로서 결과를 얻을수 있다.
  @Test
  public void testPattern3_1() throws ExecutionException, InterruptedException {
    CompletableFuture<String> cf1=
        CompletableFuture.supplyAsync(()->{
          delay(1);
          log.info("cf1");
          return "111";
    }, executor);

    CompletableFuture<String> cf2 =
        CompletableFuture
            .supplyAsync(() -> {
              delay(2);
              log.info("cf2");
              return 3;
            }, executor)
            .thenCombineAsync( cf1, (i, s) -> {
              String r = s + i;
              log.info("result string is {}", r);
              return r;
            }, executor);

    delay(4);
//    log.info( "cf2 result {}", cf2.get() );
  }


  // 앞의 타스크의 결과, 파라미터로 받은 타스크의 결과를 받아 리턴은 하지않고 결과를 소비하는 패턴이다.
  // 앞의 타스크를 먼저 수행 하고, 수행한 결과를 받을 수 있음.
  @Test
  public void testPattern3_2() throws ExecutionException, InterruptedException {
    CompletableFuture<Integer> cf1=
        CompletableFuture.supplyAsync(()->{
          delay(1);
          log.info("cf1");
          return 4;
        }, executor);

    CompletableFuture
        .supplyAsync(() -> {
          delay(2);
          log.info("cf2");
          return 3;
        }, executor)
        .thenAcceptBoth(cf1, (i1, i2) -> {
          log.info("result is i1 * i2 = {}", i1 * i2);
        });

    delay(4);
  }



  // 파라미터로 받은 타스크 완료이후, 로직을 수행한다.
  // cf1, cf2 타스크가 완료된 후에, 로직 수행. 앞선 타스크의 순서 보장은 없음
  @Test
  public void testPattern3_3() throws ExecutionException, InterruptedException {

    CountDownLatch countDownLatch = new CountDownLatch(1);

    CompletableFuture<Integer> cf1=
        CompletableFuture.supplyAsync(()->{
          delay(3);
          log.info("cf1");
          return 4;
        }, executor);

    CompletableFuture
        .supplyAsync(() -> {
          delay(2);
          log.info("cf2");
          return 3;
        }, executor)
        .runAfterBoth(cf1, () -> {
          delay(1);
          log.info("runAfterBoth()");
          countDownLatch.countDown();
        });

    countDownLatch.await();
  }


  //-----------------------------------------------------------------------------------------------------------------
  // Pattern 4 - 먼저 도착한 타스크 결과를 사용하는 타스크 결합방법

  // 앞의 타스크와 파라미터로 받은 타스크중에 더 빨리 결과를 리턴하는 타스크의 결과를 get을 통해 얻을수 있다.
  //  appyToEither를 연속적으로 써서 여러 타스크들을 연결할수 있다.
  @Test
  public void testPattern4_1() throws ExecutionException, InterruptedException {
    CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
      delay(4);
      log.info("cf1");
      return 111;
    });
    CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
      delay(2);
      log.info("cf2");
      return 222;
    });

    CompletableFuture cf3 =
        CompletableFuture
            .supplyAsync(() -> {
              delay(1);
              log.info("cf3");
              return 333;
            }, executor)
            .applyToEither(cf1, Function.<Integer>identity())
            .applyToEither(cf2, Function.<Integer>identity());

    log.info("result is {}", cf3.get());
  }


//  @Test
//  public void testPattern4_2() throws ExecutionException, InterruptedException {
//    CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
//      delay(4);
//      log.info("cf1");
//      return 111;
//    });
//    CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
//      delay(2);
//      log.info("cf2");
//      return 222;
//    });
//
//    CompletableFuture cf3 = CompletableFuture
//        .supplyAsync(() -> {
//          delay(11);
//          log.info("cf3");
//          return 333;
//        }, executor)
//        .acceptEitherAsync(cf1, i -> log.info("{}", i))
//        .acceptEitherAsync(cf2, i -> log.info("{}", i));
//  }
//
//
//
//  @Test
//  public void testPattern4_3() throws ExecutionException, InterruptedException {
//    CompletableFuture cf1= CompletableFuture.supplyAsync(()->{
//      delay(1);
//      log.info("cf1");
//      return 111;
//    });
//    CompletableFuture cf2= CompletableFuture.supplyAsync(()->{
//      delay(2);
//      log.info("cf2");
//      return 222;
//    });
//
//    CompletableFuture cf3 = CompletableFuture
//        .supplyAsync(() -> {
//          delay(11);
//          log.info("cf3");
//          return 333;
//        }, executor)
//        .runAfterEitherAsync(cf1, () -> log.info("cf1 done"))
//        .runAfterEitherAsync(cf2, () -> log.info("cf2 done"));
//  }
}
