
## Spring Security login/logout Rest Api Server (session based)

spring security 기본 login/logout 을 사용한다면, 기본적으로 제공하는 login page 가 나오게 되고,
login / logout 성공 혹은 실패에 따른 액션 (리다이렉트) 이 발생한다.

modern web 환경에서는 front end 와 back end 가 구분되어 있다. (과거에는 front end 를 servlet 환경에서 개발)
따라서, login/logout 기능을 비롯하여 모든 서비스는 rest api 형태로 제공되어야 한다.
그런데, spring security 의 기본 전략을 그데로 사용할 경우, 엉뚱한 결과 값으로 인해 기대한 기능을 구현할 수 없다.

몇가지 customizing 을 통해 login/logout rest api 기능을 제공하고자 한다.

## 원리

FormLoginConfigurer

UsernamePasswordAuthenticationFilter

(UsernamePasswordAuthenticationToken)

AuthenticationManager => ProviderManager => ... => 
    DaoAuthenticationProvider => 
        UserDetailService.loadUserByUsername
    
    
    
세션을 STATELESS 로 하면 REST 로그인과 API 호출은 잘 되는데 Form 로그인은 실패해서
NEVER로 변경했더니 Form 로그인은 잘 되나 REST에서 인증 후 로그아웃 이후에도 API가 계속 호출되어 버리네요.

출처: http://cusonar.tistory.com/17 [CU SONAR]
    
## 현재 구현에서 개선해야 할 사항

사용자 정보관리를 memory 로 관리 하고 있다.

```java
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("user").roles("USER")

        .and()
        .withUser("admin").password("admin").roles("ADMIN")
    ;
}
```

권한 설정 부분이 hard 코딩되어 있다.

```java
protected void configure(HttpSecurity http) throws Exception {
    ...
    
    .authorizeRequests()
      .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
      
    ...
}
```

사용자 관리 (
    사용자 : 가입, 탈퇴, 조회, 수정
    관리자 : 등록, 수정-권한.., 삭제, 조회
  )
  
 


## 현재 시스템의 단점

사용자 인증 정보를 servlet session 으로 관리 하고 있으므로 재시작, 확장시 session 이 사라지는 취약점이 있다.

## curl test

```
$ curl -i -X POST -d username=user -d password=user http://localhost:8080/login
$ 
$ curl http://localhost:8080/api/user
$ 
$ curl -i -X POST -d username=user -d password=user -c ~/user_cookies.txt http://localhost:8080/login
$ curl -i --header "Accept:application/json" -X GET -b ~/user_cookies.txt http://localhost:8080/api/user
$ 
$ 
$ curl -i -X POST -d username=admin -d password=admin -c ~/admin_cookies.txt http://localhost:8080/login
$ curl -i --header "Accept:application/json" -X GET -b ~/admin_cookies.txt http://localhost:8080/api/user
```


## reference 

[REST API 구성시, Spring Security 구현](http://netframework.tistory.com/entry/REST-API-%EA%B5%AC%EC%84%B1%EC%8B%9C-Spring-Security-%EA%B5%AC%ED%98%84)

[Spring Security for a REST API](http://www.baeldung.com/securing-a-restful-web-service-with-spring-security)