# 아이템 8: 적절하게 null을 처리하라

**null을 적절하게 리턴한다는 것은 어떤 경우일까?**

- `String.toIntOrNull()`은 String을 Int로 적절하게 변환할 수 없을 경우 null을 리턴한다.
- `Iterable.firstOrNull(() -> Boolean)`은 주어진 조건에 맞는 요소가 없을 경우 null을 리턴한다.

이처럼 null은 최대한 명확한 의미를 갖는 것이 좋다. 기본적으로 nullable은 세 가지 방법으로 처리한다.

1. ?.스마트캐스팅, Elvis 연산자 등을 활용해서 안전하게 처리하는 방식
2. 오류를 throw
3. 함수 혹은 프로퍼티를 리팩터링 해서 nullable이 나오지 않게 처리한다.

## null을 안전하게 처리하기

null을 안전하게 처리하는 방법 중 가장 널리 사용되는 방법은 뭐가 있을까? 아래와 같이 항목별로 예시를 들어보고 설명을 진행해보겠다.

**safe call**

```kotlin
printer?.print()
```

**smart casting**

```kotlin
if (printer != null) printer.print() // 스마트 캐스팅
```

**Collection에서 null 처리**

```kotlin
var imageUrls: List<String> = imageUrls.orEmpty()
```

## 방어적 프로그래밍과 공격적 프로그래밍

이 두가지 방식 모두 안전한 코드를 위해 필요한 방법론이다.

**방어적 프로그래밍**

프로덕션 환경에서 발생할 수 있는 수 많은 것들로부터 프로그램을 방어해서 안정성을 높이는 방법을 나타내는 포괄적인 용어이다. 하지만 모든 상황을 안전하게 처리하는 것은 불가능하다.

**공격적 프로그래밍**

예상치 못한 문제가 발생했을 때 이러한 문제를 개발자에게 알려서 수정하게 만드는 것이다.

`require, check, assert`가 바로 이러한 공격적 프로그래밍을 위한 도구이다.

### 오류를 throw 하기

오류를 강제로 발생시켜서 개발자가 알게 하려면 throw, !!, requireNotNull, checkNotNull 등을 활용할 수 있다.

### not-null assertion (!!)과 관련된 문제

가장 간단한 방법이지만, 예외가 발생할 때 어떤 설명도 없는 제네릭 예외가 발생하기 때문에 좋은 방법은 아니다.

아래와 같은 경우 nullability(null일 수 있는지)와 관련된 정보는 숨겨지게 되므로 놓칠 수 있다.

```kotlin
fun largestOf(vararg nums: Int): Int = nums.max()!!
```

### 의미없는 nullability 피하기

nullability는 처리할 때 비용이 발생하므로, 정말 필요한 경우가 아니면 안쓰는게 낫다.

nullability를 피할 때 사용할 수 있는 방법은 다음과 같다.

- 클래스에서 nullability에 따라 여러 함수를 만들어서 제공 가능
- 어떤 값이 클래스 생성 이후에 확실하게 설정된다는 보장이 있다면 lateinit이나 notnull에 위임하여 사용할 수 있다.
- null 대신에 빈 컬렉션으로 리턴하기
- nullable enum 대신에 None enum으로 처리하기

### lateinit 프로퍼티와 notNull 델리게이트

클래스 중에 초기화 할 수 없는 프로퍼티가 존재할 수 있고 이러한 프로퍼티는 사용하기 전에 반드시 초기화 해서 사용해야 한다.

이러한 경우 lateinit을 통해 프로퍼티가 나중에 초기화 될 것을 명시하는 키워드이다.

프로퍼티를 처음 사용하기 전에 반드시 초기화 될 것이라고 예상되는 상황에 활용한다.

**lateinit과 nullable의 차이**

- lateinit을 쓰는 경우에 !! 연산자로 풀어내지 않아도 된다.
- 초기화 된 이후에 초기화 되지 않은 상태로 돌아갈 수 없다.

**반대로 lateinit을 사용할 수 없는 경우도 존재한다.**

- primitive 타입을 초기화 해야하는 경우
- 이럴 경우 Delegates.notNull을 사용할 수 있다.

**프로퍼티 위임(Property Delegation)**

Primitive 타입은 lateinit으로 선언할 수 없다.

```kotlin
'lateinit' modifier is not allowed on local variables of primitive types
```

이러한 경우 아래와 같이 프로퍼티 위임을 통해 nullability를 피할 수 있다.

```kotlin
private var id: Int by arg(ID_ARG)
```

**by 키워드**

- `by` 키워드는 getter/getter&setter가 다른 곳에서 제공됨을 나타낸다. (즉, 위임됨)
- https://stackoverflow.com/questions/38250022/what-does-by-keyword-do-in-kotlin
- 프로퍼티 위임을 통해 nullability로 발생하는 비용과 여러가지 문제를 안전하게 처리할 수 있음