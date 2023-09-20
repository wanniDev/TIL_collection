# Chapter1: SpringSecurity Fundamentals - 초기화 과정 이해 01

## 프로젝트 구성

- Spring Boot 2.7.x
- JDK 11
- Gradle

### Dependency

```groovy
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-security'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  testImplementation 'org.springframework.boot:spring-boot-starter-test'
  testImplementation 'org.springframework.boot:spring-security-test'
}
```

## 초기화 과정 이해: SecurityBuilder / SecurityConfigurer

- **SecurityBuilder**는 빌더 클래스로서 웹 보안을 구성하는 빈 객체와 설정 클래스들을 생성하는 역할을 하며 WebSecurity, HttpSecurity가 있다.
- **SecurityConfigurer**는 Http 요청과 관련된 보안처리를 담당하는 필터들을 생성하고 여러 초기화 설정에 관여한다.
- **SecurityBuilder**는 **SecurityConfigurer**를 포함하고 있으며 인증 및 인가 초기화 작업은 SecurityConfigurer에 의해 진행된다.

### CustomSecurityConfigurer 만들기

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        http.apply(new CustomSecurityConfigurer().flag(false));
        return http.build();
    }
}
```

```java
public class CustomSecurityConfigurer extends AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity> {
    private boolean isSecure;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
        System.out.println("init method started...");
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        if (isSecure) {
            System.out.println("https is required");
        } else {
            System.out.println("http is optional");
        }
    }

    public CustomSecurityConfigurer flag(boolean isSecure) {
        this.isSecure = isSecure;
        return this;
    }
}
```

