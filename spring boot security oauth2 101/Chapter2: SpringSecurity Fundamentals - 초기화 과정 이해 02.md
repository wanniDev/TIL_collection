# Chapter2: SpringSecurity Fundamentals - 초기화 과정 이해 02

## 초기화 과정 이해: 자동설정에 의한 초기화 과정

> 자동설정으로 애플리케이션을 구동

1. **SpringWebMvcImportSelector**
   - WebMvcSecurityConfiguration
2. **SecurityFilterAutoConfiguration**
   - DelegatingFilterProxyRegistrationBean - DelegatingFilterProxy 등록 ("springSecurityFilterChain" 이름의 빈을 검색)
3. **WebMvcSecurityConfiguration**
   - **AuthenticationPrincipalArgumentResolver - @AuthenticationPrincipal 객체 바인딩**
   - CurrentSecurityContextArgumentResolver
   - CsrfTokenArgumentResolver
4. **HttpSecurityConfiguration**
   - HttpSecurity : 공통 설정 클래스와 필터들을 생성하고 최종적으로 **SecurityFilterChain** 빈 반환

### 1. SpringBootWebSecurityConfiguration

- HttpSecurityConfiguration 에서 생성한 HttpSecurity 객체를 주입받는다.
- Form 로그인과 Basic 로그인 기능을 추가 정의한 기본 SecurityFilterChain 빈을 정의한다.

### 2. WebsecurityConfiguration

- WebSecurity를 만드는 설정 빈이다.
- WebSecurity는 설정클래스에서 정의한 SecurityFilterChain 빈을 SecurityBuilder에 저장한다.
- WebSecurity 가 build() 를 실행하면 SecurityBuilder 에서 SecurityFilterChain 을 꺼내어 FilterChainProxy 생성자에게 전달한다

## 사용자 정의 설정 클래스를 생성하고 애플리케이션을 구동한다.

```java
@Bean
@Order(0)
public SecurityFilterChain securityFilterChain1(HttpSerucity http) throws Exception {
  http.authorizeHttpRequests().anyRequest().authenticated();
  http.formLogin();
  return http.build();
}

@Bean
@Order(1)
public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
  http.authorzieHttpRequests().anyRequest().authenticated();
  http.httpBasic();
  return http.build();
}
```

- 설정 클래스를 커스텀하게 생성하기 때문에 SpringBootWebSecurityConfiguration 의 SecurityFilterChainConfiguration 클래스 구동되지 않는다.
- 사용자 정의 설정 클래스 생성시 SecurityFilterChain 과 WebSecurityConfigurerAdapter(지금은 Deprecated 처리됨) 두 가지 방식 모두 설정할 수 없으며 하나만 정의해야 한다.