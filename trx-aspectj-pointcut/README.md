# AspectJ 모드 + pointcut 으로 jdbc Transaction 처리

구현 테스트는 총 3 종류.

> `mvn dependency:copy-dependencies` 를 이용하여, aspectjweaver 와 spring-instrument jar를 편한 곳에 복사 해 둔다. (-javaagent 옵션을 편하게 사용하기 위함)

## com.example.demo package

`org.springframework.transaction.aspectj.AnnotationTransactionAspect` 코드를 참고하여
직접 `org.springframework.transaction.interceptor.TransactionAspectSupport` 확장 구현.
`com.example.demo.PointcutTransactionAspect` 에 대상이 되는 pointcut expression 을 수정하십시오.
`com.example.demo.PointcutTransactionAspectConfig#transactionAttributeSource` 함수에 원하는 transaction 전략을 정의 하세요

- terminal 에서 아래와 같이 실행 및 테스트
```bash
mvn clean package

java -javaagent:./aspectjweaver-1.8.10.jar -jar target/trx-aspectj-pointcut-1.0-SNAPSHOT.jar
```

- eclipse 에서 실행시 vm arguments
```eclipse

-Dorg.aspectj.weaver.loadtime.configuration=${project_loc}/aspectjxmls/demo1-aop.xml
-javaagent:${project_loc}/aspectjweaver-1.8.10.jar

```

## com.example.demo2 package

**이 사용법 추천**

`org.springframework.transaction.aspectj.AnnotationTransactionAspect` 코드를 참고하여
직접 `org.springframework.transaction.aspectj.AbstractTransactionAspect` 확장 구현.
`com.example.demo2.PointcutTransactionAspect2` 에 대상이 되는 pointcut expression 을 수정하십시오.
`com.example.demo.PointcutTransactionAspectConfig#transactionAttributeSource` 함수에 원하는 transaction 전략을 정의 하세요

- terminal 에서 아래와 같이 실행 및 테스트
```bash
mvn -Pdemo2 clean package

java -javaagent:./aspectjweaver-1.8.10.jar -jar target/trx-aspectj-pointcut-1.0-SNAPSHOT.jar
```

- eclipse 에서 실행시 vm arguments
```eclipse

-Dorg.aspectj.weaver.loadtime.configuration=${project_loc}/aspectjxmls/demo2-aop.xml
-javaagent:${project_loc}/aspectjweaver-1.8.10.jar

```

## org.springframework package

`@EnableTrasactionManagement` 의 구현 방식을 모사하여 만든 것.
spring @EnableTransactionManagement 기능 학습 용도.


## h2database

[download](http://h2database.com/html/download.html)

## LTW 방식

```sh
java -javaagent:aspectjweaver-1.8.10.jar -javaagent:spring-instrument-4.3.10.RELEASE.jar -jar target/trx-aspectj-pointcut-1.0-SNAPSHOT.jar
```

이클립스에서 개발을 위해 `-javaagent:${project_loc}/aspectjweaver-1.8.10.jar -javaagent:${project_loc}/spring-instrument-4.3.10.RELEASE.jar` vm 옵션을 사용하였다.

## CTW 방식 (aspectj maven plugin)

CTW 을 이용한다면, spring-instrument jar는 필요하지 않고, `@EnableLoadTimeWeaving` 코드 역시 필요 하지 않다.

decompiler 를 통해 보면 이상한 코드들이 삽입 되어 있음을 확인할 수 있다.

```sh
java -javaagent:aspectjweaver-1.8.10.jar -jar target/trx-aspectj-pointcut-1.0-SNAPSHOT.jar
```

## EnableTransactionManagement 요약

@EnableTransactionManagement ASPECTJ 모드 사용시 @Transaction annotation driven transaction 처리를 한다.

@Transactional 은 org.springframework.transaction.annotation.Transactional 과 javax.transaction.Transactional 가 있는데, 내부적으로 둘다 지원 한다.

@Transactional 기반 Transaction 처리는 spring-aspects 모듈의 AnnotationTransactionAspect 에서 수행.

## ajcore txt 파일 관련

exception 발생시 파일을 dump 하는데 이를 disable 하기 위해 -D option 사용 [링크](https://eclipse.org/aspectj/doc/released/pdguide/ajcore.html)

`-Dorg.aspectj.weaver.Dump.exception=false` vm 옵션을 주자.

## [Xlint:cantFindType] error print

기대하지 않은 3rd party module 에 위빙을 해서 그러함.

META-INF/aop.xml 파일에 아래와 같은 정보를 기재.

```xml
<?xml version="1.0"?>

<!--
    AspectJ load-time weaving config file with Spring aspects.
-->
<aspectj>


    <weaver options="-showWeaveInfo">
        <include within="com.yourpackage..*"/> <!-- 이부분... -->
    </weaver>


    <aspects>
        <aspect name="a.b.c.TrxAspect"/>
    </aspects>

</aspectj>
```

## Spring autowired bean for @Aspect aspect is null

@Aspect bean 이 실행시점 bean 과 스프링 컨테이너에서 관리 되는 bean 이 서로 다르다.

[이 방법](https://stackoverflow.com/questions/9633840/spring-autowired-bean-for-aspect-aspect-is-null) 으로 해결 하였다.

xml 방식 빈 설정시 `factory-method=aspectOf` 를 명시 해 준다.

java config 방식 빈 설정은 `TrxAspectConfig.java` 파일 참조
