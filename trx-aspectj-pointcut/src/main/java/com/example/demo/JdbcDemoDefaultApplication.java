package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan
@EnableTransactionManagement(proxyTargetClass = true, mode =AdviceMode.ASPECTJ)
public class JdbcDemoDefaultApplication implements CommandLineRunner {

	public static void main(String[] args) {
		try( ConfigurableApplicationContext context = SpringApplication.run(JdbcDemoDefaultApplication.class, args); ) {
		}
	}
	
	@Autowired
	FooService fooService;

	@Autowired
	PointcutTransactionAspect trxAspect;
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println( "***********" + trxAspect );
		
		System.out.println("======================================================== start");
		
		fooService.selectMethod1();
		System.out.println("==================");
		
		fooService.helloGo();
		System.out.println("==================");
		
		fooService.insertMethod1();
		System.out.println("==================");
		
		fooService.insertMethod2();
		System.out.println("==================");
		
		System.out.println("======================================================== end");
	}
}
