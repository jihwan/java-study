package com.springtest.demo;

import org.springframework.stereotype.Component;

@Component
public class FooService {

  // self invoke test
  public void helloGo() {
    System.out.println("no trx and self invoke");
    this.selecthello();
  }

  public void selecthello() {
    System.out.println("go selecthello trx");
  }

  // self invoke test
  public void selectMethod1() {
    System.out.println("go selectMethod1 start");
    this.insertMethod2();
    System.out.println("go selectMethod1 end");
  }

  public void getMethod2() {
    System.out.println("go getMethod2 trx");
  }

  // self invoke test
  public void insertMethod1() {
    System.out.println("go insertMethod1 start");
    this.selecthello();
    System.out.println("go insertMethod1 end");
  }

  public void insertMethod2() {
    System.out.println("go insertMethod2 trx");
  }
}
