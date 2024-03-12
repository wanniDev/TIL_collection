# Chapter1: SpringSecurity Fundamentals - 초기화 과정 이해 01

jHipster는 초기에 번거로울 수 있는 프로젝트 및 빈 구성을 미리 세팅한 상태의 모듈을 제공해줍니다. 이 부분은 편리할 수 있지만, 일부 비즈니스 룰에 맞게 기능을 구현하기 위해 커스터마이징을 하고자 하면 생기는 불편함이 일부 있을 수 있습니다.

특히, 인증 인가 보안 필터에 활용되는 Spring Security는 그 구조와 원리를 이해하지 못하면 애플리케이션 자체의 구현 난이도가 높아질 수 있으며, 요구사항에 맞게 살짝 변경하는 것 역시 버거울 수 있습니다.

따라서, 이번 글을 통해, spring security가 서버가 동작하기 시작할 때의 필터가 구성되는 원리와 인증, 인가가 수행되는 과정을 정리하여, jHipster 내부의 인증 관련 코드를 좀 더 수월하게 경량화하고 커스터마이징 하는 방법을 제시하고자 합니다.

## spring security 기본 의존성 구성 및 주요 클래스 역할

스프링 부트 환경에서 스프링 시큐리티를 적용하면 다음과 같은 라이브러리 의존성이 추가됩니다.

 ```groovy
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-security'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  testImplementation 'org.springframework.boot:spring-boot-starter-test'
  testImplementation 'org.springframework.boot:spring-security-test'
}
 ```

## 초기화 과정 이해: SecurityBuilder / SecurityConfigurer

**SecurityBuilder**는 빌더 클래스로서 웹 보안을 구성하는 빈 객체와 설정 클래스들을 생성하는 역할을 합니다. 주로 `WebSecurity`, `HttpSecurity`가 있습니다.

<img width="398" alt="스크린샷 2023-09-21 오후 2 11 59" src="https://github.com/wanniDev/TIL_collection/assets/81374655/1900f92d-2c52-4ab5-aa35-e36b35cf8f1f">

- **WebSecurity :** 전반적인 웹 보안 설정을 담당합니다. 웹 애플리케이션의 모든 요청에 대한 기본적인 보안 성정을 제공하며, 리소스 파일들과 같은 특정 요청을 보안 필터 체인에서 제외하는 역할을 합니다.
- **HttpSecurity : **요청에 대한 보안 상세 설정을 담당합니다. 특정 경로나 패턴에 대한 인증 및 권한 설정, 로그인/로그아웃 메커니즘, CSRF 등의 보안 취약점 보완하는 등의 세부적인 보안 설정을 구성할 수 있습니다.

**SecurityConfigurer**는 Http 요청과 관련된 보안처리를 담당하는 필터들을 생성하고 여러 초기화 설정에 관여합니다.

개발자는 Spring Security가 제공하는 Configurer를 활용할 수도 있고, 직접 구현하여 적용할 수도 있습니다.

<img width="527" alt="스크린샷 2023-09-21 오후 2 23 00" src="https://github.com/wanniDev/TIL_collection/assets/81374655/601c50cc-1a08-409a-b88e-8d710e330be4">

> 위 다이어그램에 보여주는 Configurer 계열의 클래스들은 예시로서, 일부만 보여드렸습니다. 실제로 spring security가 제공하는 configurer는 훨씬 다양합니다.

본래, SpringSecurity 자체로는 보안 필터 및 인증, 인가 등을 활용하기 위해선 별도의 xml을 구성하여 SpringSecurity에게 어떤 기능을 초기화할 것인지 일일이 작성해주어야 했습니다.

하지만, Spring Boot 진영에서 제공해주는 자동 설정 기능과 컴포넌트 구성을 통해 그나마 더 직관적으로 SpringSecurity를 구성할 수 있습니다. SpringSecurity(+Boot)에서 SecurityConfigure를 초기화 과정은 다음과 같습니다.

![스크린샷 2023-09-21 오후 2 37 16](https://github.com/wanniDev/TIL_collection/assets/81374655/b11088f7-9a31-4919-8ff8-3ba1f38a6ed3)

SpringBoot의 자동 설정 기능 덕분에 개발자는 구성하고 싶은 보안의 설정이 적용된 Configurer를 컴포넌트(@Component)로 구성하거나, 직접 Configurer를 구현하여 컴포넌트에 포함하면 됩니다. 위의 과정을 거쳐 초기화 작업이 완료된 Configurer를 통해 구성된 필터 체인은 아래와 같은 형태로 적용됩니다.

<img width="967" alt="스크린샷 2023-09-21 오후 2 59 18" src="https://github.com/wanniDev/TIL_collection/assets/81374655/5f5de5b5-b80f-4a24-836d-b4a6cee169cd">

여기서 알아야할 점은 개발자가 임의로 구현한 서블릿 필터와 스프링 시큐리티에서 구성된 필터가 동작하는 순서입니다. SpringSecurity에서 구성한 `FilterChainProxy`은 `springSecurityFilterChain`이라는 이름의 빈으로 등록됩니다.

`springSecurityFilterChain`라는 빈에서 관리하는 필터 체인의 우선순위는 `SecurityProperties.DEFAULT_FILTER_ORDER`, 즉  '-100'으로 설정됩니다. 따라서, 개발자가 구현한 필터의 `Ordered` 우선순위가 높다면(즉, -100 보다 더 적은 수로 지정되어있다면) 보안 필터보다 먼저 동작하게 되고 아닌 경우에는 보안 필터가 동작한 다음에 개발자의 필터가 동작하게 됩니다.

## SecurityFilterChain 구성하기

지금까지 SecurityFilterChain이 초기화되어 보안 필터체인이 기존 서블릿 필터체인에 어떠한 형태로 생성되는지에 대해 알아보았는데요. 이번에는 초기화 이후에 생성될 SecurityFilterChain을 구성하는 법을 살펴보겠습니다.

### defaultSecurityFilterChain

스프링 부트에서는 별도의 SecurityFilterChain을 구성하지 않았다면, 자동으로 SecurityFilterChain을 구성해줍니다. 이러한 행위를 해주는 빈(Bean) 클래스인 `SpringBootWebSecurityConfiguration`을 살펴보겠습니다.

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
class SpringBootWebSecurityConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnDefaultWebSecurity
	static class SecurityFilterChainConfiguration {

		@Bean
		@Order(SecurityProperties.BASIC_AUTH_ORDER)
		SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests().anyRequest().authenticated();
			http.formLogin();
			http.httpBasic();
			return http.build();
		}

	}
	//....
}
```

`@ConditionalOnDefaultWebSecurity`의 조건에 따라, 개발자가 그 어떠한 SecurityFilter와 관련된 빈 구성을 하지 않았다면, 위와 같이 spring boot 진영에서 구성한 빈이 동작하게 됩니다. 그렇다면, SecurityFilterChain을 구성하려면 어떻게하면 될까요? 간단합니다. 위에서 springBoot가 등록해준 `@Configuration` 컴포넌트와 동일한 형태의 컴포넌트를 구현하면 됩니다.

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

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer.anyRequest().authenticated())
            .formLogin(withDefaults())
            .apply(new CustomSecurityConfigurer().flag(false));
        return http.build();
    }
}
```

위의 예시는 스프링 시큐리티에서 커스터마이징할 수 있는 SecurityFilterChain를 어떻게  구성하는지에 대한 방법을 보여줍니다. `.authorizeHttpRequest(...)`, `.formLogin(...)`과 같이 프레임워크에서 제공하는 Configurer를 활용하여 필터체인을 구성할 수도 있고, 직접 `SecurityConfigurer`를 구현하여 httpSecurity에 apply할 수 있습니다.

## 마무리

스프링 시큐리티는 인증/인가 외에도 복잡하고 엄격한 보안 정책을 비교적 단순한 필터 체인을 통해 준수할 수 있도록 도와줍니다. 따라서, 스프링 시큐리티가 구성되는 과정은 매우 복잡하고 다양한 비즈니스 룰을 충족하기 위해 커스터마이징 작업도 많이 수행해야하지만, 제대로 이해하고 활용한다면 운영하고 있는 애플리케이션의 코드 구조를 OOP 원리에 충실하여 좀 더 가성비 있는 운영에 도움이 될거라 확신합니다..

다음 문서에서는 스프링 시큐리티가 인증을 어떻게 수행하는지를 살펴보고, JHipster에서 적용된 JWT 기능을 경량화를 해보도록 하겠습니다.
