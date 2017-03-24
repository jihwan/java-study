
jpa 예제 테스트 코드는, EntityManager (hibernate 구현체) 객체 생명 주기를 직접 관리 하지 않고,

Spring 에서 관리 하도록 하였다. 그리고 Spring Boot 을 이용하였다.

테스트용 database 는 h2를 사용하였고, h2 mode 는 Oracle 을 이용하였다.

h2 database 는 tcp Server 모드로 실행 하도록 하고, 테스트를 위한 h2 database 연결 역시 그러하다.

연결 정보는 src/main/resources/application.properties 를 확인하면 된다.

