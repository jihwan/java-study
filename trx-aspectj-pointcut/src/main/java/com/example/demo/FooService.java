package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class FooService {

  // self invoke test
  public void helloGo() {
    System.out.println("no trx and self invoke. ============================= demo1");
    this.selecthello();
  }

  public void selecthello() {
    System.out.println("go selecthello trx. ============================= demo1");
  }

  // self invoke test
  public void selectMethod1() {
    System.out.println("go selectMethod1 start. ============================= demo1");
    this.insertMethod2();
    System.out.println("go selectMethod1 end. ============================= demo1");
  }

  public void getMethod2() {
    System.out.println("go getMethod2 trx. ============================= demo1");
  }

  // self invoke test
  public void insertMethod1() {
    System.out.println("go insertMethod1 start. ============================= demo1");
    this.selecthello();
    System.out.println("go insertMethod1 end. ============================= demo1");
  }

  public void insertMethod2() {
    System.out.println("go insertMethod2 trx. ============================= demo1");
  }
}
