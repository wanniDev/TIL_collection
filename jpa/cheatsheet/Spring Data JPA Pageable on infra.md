# Spring Data JPA Pageable on infra

Spring Data Jpa에는 JPA를 Spring Data Common에서 제공하는 표준 repository 인터페이스에 맞게 직관적으로 jpa를 사용할 수 있는 편의성 기능들을 제공합니다. 이 게시글에서는 그 중에서 목록 조회에 페이징 정렬을 직관적으로 적용할 수 있게하는 Page를 infrastructure layer에 사용하는 방법에 대해 찾아보고 내용을 정리해보고자 합니다.

## 페이징 정렬 interface

### Page 인터페이스

```java
public interface Page<T> extends Slice<T> {
  int getTotalPages();				// 전체 페이지 수
  long getTotalElements();		// 전체 데이터 수
  <U> Page<U> map(Function<? super T, ? extends U> converter); // 변환기
}
```

### Slice 인터페이스

```java
public interface Slice<T> extends Streamable<T> {
  int getNumber();							// 현재 페이지
  int getSize();								// 페이지 크기
  int getNumberOfElements();		// 현재 페이지에 나올 데이터 수
  List<T> getContent();					// 조회된 데이터
  boolean hasContent();					// 조회된 데이터 존재 여부
  Sort getSort();								// 정렬 정보
  boolean isFirst();						// 현재 페이지가 첫 페이지 인지 여부
  boolean isLast();							// 현재 페이지가 마지막 페이지 인지 여부
  boolean hasNext();						// 다음 페이지 존재 여부
  boolean hasPrevious();				// 이전 페이지 존재 여부
  Pageable getPageable();				// 페이지 요청 정보
  Pageable nextPageable();			// 다음 페이지 객체
  Pageable previousPageable();	// 이전 페이지 객체
  <U> Slice<U> map(Function<? super T, ? extends U> converter); // 변환기
}
```

## 페이징 처리 적용 방법

### 방법 1. 빈 등록

이 방법은 Pageable 객체를 다음과 같이 빈으로 등록하여

```java
@Configuration
public class PageableConfig {
  @Bean
  public Pageable defaultPageable() {
    return PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"))
  }
}
```

아래 처럼 di를 적용하여 jpa 조회 코드에 페이징 처리를 적용합니다.

```java
@Service
public class SomeService {
  private final Pageable pageable;
  //.... 여러가지 di 객체들
  
  public SomService(Pageable pageable) {
    this.pageable = pageable;
  }
  
  public List<SomeEntity> something() {
     return repository.findAll(pageable);
  }
}
```

### 방법 2. 컴포넌트 안에 미리 초기화

이 방법은 Pageable 객체를 따로 빈으로 등록하지 않고 페이징 정렬을 처리하는 클래스 내부에 미리 초기화하여, 사용하는 방법입니다.

```java
@Service
public class SomeService {
  private final Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
  
  public SomService(Pageable pageable) {
    this.pageable = pageable;
  }
  
  public List<SomeEntity> something() {
     return repository.findAll(pageable);
  }
}
```

컴포넌트의 경우, 스프링 애플리케이션이 시작된 후, 빈 생명주기 콜백의 설정에 따라 등록이 되니, 위에서 처럼 final을 통해 초기화와 동시에 불변화를 진행한다면, 컴포넌트 내부에 초기화를 선언해놓는다면, 동시성 관련해서 큰 문제는 없어보입니다.

## 어떤 방식을 써야할까?

결론부터 이야기하자면, 정답은 없다고 할 수 있겠습니다. 필요에 따라 둘다 사용할 수도 있고, 둘 중 하나만 사용할 수도 있습니다. 가장 중요한건 기준을 정하여 일관적인 방향을 유지하는 것이죠.

저의 경우는 Layered Architecture에서 정의하는 계층의 이동방향에 맞는 코드를 사용해야한다는 기준을 가지고 있습니다. 적어도 현재 진행하고 있는 프로젝트에서는요.

즉, presentation 계층의 클래스에는 같은 presentation 코드와 application 코드. application 코드에서는 domain 코드. domain 코드에서는 외부 오픈소스 라이브러리에 의존하지 않는 코드를 작성하되 필요하다면 인터페이스로 정의하고 해당 인터페이스를 구현하는 infrastructure 코드를 사용한다는 기준을 가지고 있는거죠.

그렇다면, 저의 경우는 방법 2를 통해 domain 계층의 코드에서 사용하는 repository 인터페이스를 구현하는 클래스에 pageable을 초기화하여 사용하는 것이죠.

이 글을 보시는 여러분들은 어떠한 기준을 가지고 쓸지 고민해보고 둘 중에 한가지 혹은 또다른 방식을 사용하시면 되겠습니다.