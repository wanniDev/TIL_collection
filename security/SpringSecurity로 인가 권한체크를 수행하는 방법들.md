# Spring Security @Secured vs @PreAuthorize

스프링 시큐리티는 크게 인증, 인가, 보안필터 이 3가지를 지원합니다. 이 3 가지 중에서 인가를 체크하는 방법에 대해 살펴보겠습니다. 인가 체크를 수행하려면 사전 설정이 필요합니다.

### @Secured 기반 권한 체크

선언된 메소드의 사전에만 체크. 사후 체크를 하려면 @PostAuthorize(혹은 @PostFilter)를 사용하거나 별도의 AOP를 설계해야합니다.

@Secured는 Voter를 추가할 수 있도록 설계되어 Voter 기반의 AccessDecisionManager와 어울립니다.

@Secured는 다음과 같이 활용됩니다.

```java
@Secured("ROLE_VIEWER")
public String getUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return securityContext.getAuthentication().getName();
}

@Secured({ "ROLE_VIEWER", "ROLE_EDITOR" })
public boolean isValidUsername(String username) {
    return userRoleRepository.isValidUsername(username);
}
```

`getUsername()` 함수는 'ROLE_VIEWER' 권한을 가진 사용자만, 접근이 가능하고, `isValidUsername(String username)`는 "ROLE_VIEWER", "ROLE_EDITOR" 중 하나 이상의 권한을 가지고 있으면 접근이 가능합니다.

참고로 @Secured 애노테이션은 SpEL을 지원하지 않습니다.

### @RolesAllowed

기존 @Secured에 **JSR-250**이 적용된 버전입니다.

```java
@RolesAllowed("ROLE_VIEWER")
public String getUsername2() {
    //...
}

@RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })
public boolean isValidUsername2(String username) {
    //...
}
```

권한이 적용되는 패턴은 동일합니다.

### @PreAuthorize, @PostAuthorize

이 애노테이션은 Expression 기반의 접근 제어가 가능한 애노테이션입니다. 

```java
@PreAuthorize("hasRole('ROLE_VIEWER')")
public String getUsernameInUpperCase() {
    return getUsername().toUpperCase();
}
```



```java
@PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
public boolean isValidUsername3(String username) {
    //...
}
```





![image](https://github.com/wanniDev/TIL_collection/assets/81374655/5f93c0c8-140d-43f6-a281-ebb627ba2fd8)



## 참고

- https://www.baeldung.com/spring-security-method-security
- https://velog.io/@dnrwhddk1/Spring-Security-Secured-%EA%B8%B0%EB%B0%98-%EA%B6%8C%ED%95%9C-%EC%B2%B4%ED%81%AC