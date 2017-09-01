package com.springtest.demo;

import com.springtest.trx.annotation.EnableTrxPointcutDrivenMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableTrxPointcutDrivenMgr
public class CustomEnableTrxMgrTestApplication implements CommandLineRunner {

  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(CustomEnableTrxMgrTestApplication.class, args);) {
      // for (String bean : context.getBeanDefinitionNames()) {
      // System.err.println(bean);
      // }
    }
  }

  @Autowired
  FooService fooService;

  @Override
  public void run(String... arg0) throws Exception {
    System.out.println("======================================================== start");

    fooService.selectMethod1();
    System.out.println("==================");

//    fooService.helloGo();
//    System.out.println("==================");
//
//    fooService.insertMethod1();
//    System.out.println("==================");
//
//    fooService.insertMethod2();
//    System.out.println("==================");

    System.out.println("======================================================== end");
  }

}
