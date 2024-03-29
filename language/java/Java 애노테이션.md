# **자바의 애노테이션**

## 자바의 애노테이션이란?

메타 데이터의 한 종류로, 코드에 추가적인 정보를 제공하는 데 사용됩니다. 해당 코드가 어떻게 작동하거나 사용되어야 하는지에 대한 정보를 제공하거나, 컴파일러에게 특정 경고를 무시하도록 지시하는 등의 역할을 수행할 수 있습니다. 자바에서 사용되는 대표적인 애노테이션은 다음과 같습니다.

**`@Override`**

이 애노테이션은 부모 클래스의 메소드를 오버라이드 한다는 것을 나타냅니다. 컴파일러는 이 애노테이션을 보고 실제 부모 클래스의 메소드를 오버라이드 하는지 확인하고, 그렇지 않으면 경고를 발생시킵니다.

또다른 예로는 `@Deprecated`, `@SuppressWarnings` 등이 있습니다.

**사용자 정의 애노테이션**

이 외에도 개발자가 직접 애노테이션을 만들 수 있습니다. 개발자는 자바의 애노테이션을 만들어서 컴파일 시에 애노테이션을 처리할 수 있는 도구나 프레임워크를 사용하여 추가 정보를 처리할 수도 있습니다.

```java
public @interface MyCustomAnnotation {
    int value();
}
```

```java
@MyCustomAnnotation(value = 5)
public class MyClass {
    // ...
}
```

이렇게 어노테이션을 사용하면, 코드가 실행될 때 또는 컴파일 시에 어노테이션을 처리할 수 있는 도구나 프레임워크를 사용하여 추가 정보를 처리할 수 있습니다. 예를 들어, 스프링 프레임워크는 `@Autowired`, `@Component` 등의 어노테이션을 사용하여 의존성 주입, 컴포넌트 스캔 등의 기능을 제공합니다.

## 스프링 프레임워크 애노테이션

스프링 프레임워크에서는 다양한 어노테이션을 통해 여러 기능을 제공하고 있습니다. `@Autowired`와 `@Component` 외에도 주요한 어노테이션에는 다음과 같은 것들이 있습니다:

1. **`@Service`:** 해당 클래스가 서비스 계층의 컴포넌트임을 나타내는 어노테이션입니다. `@Component`와 기능적으로 동일하지만, 코드의 명확성과 가독성을 위해 사용됩니다.
2. **`@Repository`:** 해당 클래스가 데이터 저장소에 접근하는 코드를 담당하는 리포지토리 또는 DAO(Data Access Object) 계층의 컴포넌트임을 나타냅니다. `@Component`와 기능적으로 동일하지만, 코드의 명확성과 가독성을 위해 사용됩니다.
3. **`@Controller`:** 해당 클래스가 웹 요청을 처리하는 컨트롤러 계층의 컴포넌트임을 나타냅니다. `@Component`와 기능적으로 동일하지만, MVC 패턴에서 컨트롤러 역할을 명시적으로 표시하기 위해 사용됩니다.
4. **`@RequestMapping`:** 특정 URL 패턴에 대한 요청을 처리할 메소드나 클래스를 지정합니다. HTTP 메소드(GET, POST, PUT 등)와 함께 사용하여, 특정 요청 방식에 대해 처리할 메소드를 지정할 수도 있습니다.
5. **`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`:** 각각 GET, POST, PUT, DELETE 방식의 HTTP 요청에 대해 처리할 메소드를 지정합니다. `@RequestMapping`의 특수한 형태로 볼 수 있습니다.
6. **`@PathVariable`:** URL 패턴 내의 특정 부분을 메소드의 파라미터로 전달합니다.
7. **`@RequestParam`:** 요청 파라미터를 메소드의 파라미터로 전달합니다.
8. **`@ResponseBody`:** 메소드가 반환하는 값을 HTTP 응답 본문으로 사용합니다.
9. **`@RequestBody`:** HTTP 요청 본문을 메소드의 파라미터로 전달합니다.
10. **`@Bean`:** 개발자가 직접 컨트롤이 필요한 빈 객체를 스프링에 등록할 때 사용하는 어노테이션입니다. 일반적으로 Java 설정 파일(JavaConfig)에서 사용됩니다.
11. **`@Configuration`:** 해당 클래스가 스프링 설정 파일임을 나타내는 어노테이션입니다.
12. **`@Aspect`:** 해당 클래스가 Aspect(관점)를 구현한 클래스임을 나타내는 어노테이션입니다. Aspect Oriented Programming(AOP)에서 사용됩니다.

이 외에도 많은 스프링 어노테이션이 있으며, 각각의 어노테이션은 스프링 프레임워크 내에서 특정 기능을 제공합니다.

## Spring AOP(Aspect-Oriented Programming)

Spring AOP는 프록시 기반의 AOP를 제공합니다. 즉, Aspect가 적용되어야 하는 대상 클래스에 대한 프록시를 생성하고, 이 프록시를 통해 원래의 메소드를 호출하는 방식으로 동작합니다. 이 프록시에서는 원래의 메소드 호출 전/후 또는 예외 발생 시에 적절한 advice를 실행하게 됩니다.

프록시는 런타임에 생성되므로, Spring AOP는 "런타임 시 AOP"라고도 불립니다. 이는 컴파일 시에 AOP를 적용하는 AspectJ와는 차이가 있습니다. 그럼에도 불구하고, Spring AOP는 대부분의 일반적인 경우에는 충분히 유용하며, 필요한 경우 AspectJ와 함께 사용할 수도 있습니다.

**AOP 주요 개념**

1. **Joinpoint**: Joinpoint는 프로그램의 실행 중에 특정 위치를 나타냅니다. 이 위치는 메소드 호출 또는 예외 처리와 같은 코드의 특정 지점일 수 있습니다. Spring AOP에서는 메소드 실행이라는 한 종류의 joinpoint만 지원합니다.
2. **Pointcut**: Pointcut은 어떤 joinpoint를 어드바이스할 것인지 결정하는 기준을 나타냅니다. 이는 특정 메소드 이름, 애노테이션 타입 등을 기준으로 정의할 수 있습니다. Pointcut 표현식을 사용하여 더 복잡한 규칙을 정의할 수도 있습니다.
3. **Advice**: Advice는 특정 joinpoint에서 실행되어야 할 액션 또는 코드를 나타냅니다. `@Before`, `@After`, `@Around` 등의 어노테이션이 이에 해당합니다. Advice는 메소드 호출 전, 후 또는 예외 발생 시 등 특정 시점에 실행될 수 있습니다.
4. **Aspect**: Aspect는 pointcut과 advice를 결합한 것으로, 공통적으로 적용되어야 하는 기능(예: 로깅, 트랜잭션 관리, 보안)을 모듈화합니다. Aspect는 클래스나 메소드에 분산된 코드를 한 곳에 모으는 역할을 하므로, 코드의 중복을 줄이고 가독성을 높일 수 있습니다.

**spring aop 주요 애노테이션**

스프링 프레임워크에서 AOP(관점 지향 프로그래밍)를 지원하기 위한 주요 어노테이션들은 다음과 같습니다:

1. **`@Aspect`:** 이 어노테이션은 클래스가 Aspect를 구현한 것임을 나타냅니다. 즉, 이 클래스는 공통 관심사(cross-cutting concerns)를 모듈화한 것이며, `@Before`, `@After` 등의 어드바이스(advice) 어노테이션을 가진 메소드를 포함합니다.
2. **`@Before`:** 이 어노테이션은 특정 조인포인트(join point)에 도달하기 전에 어드바이스 메소드를 실행하도록 지정합니다. 이 어노테이션의 파라미터로는 포인트컷(pointcut) 표현식이 들어갑니다.
3. **`@After`:** 이 어노테이션은 특정 조인포인트가 완료된 후에(즉, 메소드 실행이 끝난 후에) 어드바이스 메소드를 실행하도록 지정합니다. 이는 메소드가 성공적으로 완료되었는지, 예외가 발생했는지에 상관없이 실행됩니다.
4. **`@AfterReturning`:** 이 어노테이션은 특정 조인포인트가 성공적으로 완료된 후에 어드바이스 메소드를 실행하도록 지정합니다.
5. **`@AfterThrowing`:** 이 어노테이션은 조인포인트에서 예외가 발생한 후에 어드바이스 메소드를 실행하도록 지정합니다.
6. **`@Around`:** 이 어노테이션은 조인포인트를 어드바이스 메소드가 완전히 감싸서 해당 메소드를 호출하기 전과 후에 사용자 지정 동작을 정의할 수 있게 해줍니다.
7. **`@Pointcut`:** 이 어노테이션은 복잡한 포인트컷 표현식을 재사용할 수 있게 해주는 이름있는 포인트컷을 정의하는 데 사용됩니다.

이들 어노테이션은 스프링 AOP를 구성하는데 필요한 주요 구성요소들입니다. 이를 통해 로깅, 트랜잭션 관리, 보안 등과 같은 공통 관심사를 분리하고 모듈화하는 것이 가능해집니다.