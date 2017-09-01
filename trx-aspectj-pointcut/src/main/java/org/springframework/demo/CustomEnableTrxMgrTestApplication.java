package org.springframework.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableAspectJPointcutTransactionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.test.BarService;

@SpringBootApplication
@EnableAspectJPointcutTransactionManagement
@ComponentScan( basePackageClasses = {BarService.class, FooService.class})
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

  @Autowired
  BarService barService;

  @Override
  public void run(String... arg0) throws Exception {
    System.out.println("======================================================== start");

//    fooService.selectMethod1();
//    System.out.println("==================");
//
    fooService.helloGo();
    System.out.println("==================");
//
//    fooService.insertMethod1();
//    System.out.println("==================");
//
//    fooService.insertMethod2();
//    System.out.println("==================");


    // META-INF/aop.xml weave include 를 하지 않았으므로 aop 적용이 안된다.
    // program option -showWeaveInfo 를 줘야 되는데 왜지????
    System.out.println("*************************************");
    barService.selectBar();

    System.out.println("======================================================== end");
  }

}
