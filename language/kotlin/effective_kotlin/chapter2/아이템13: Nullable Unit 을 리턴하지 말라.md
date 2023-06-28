# 아이템13. Nullable Unit 을 리턴하지 말라

코틀린에서는 void 대신, Unit과 Nothing 이라는 타입을 제공해준다.

- `Unit`은 함ㅜ가 끝났으나, 의미있는 반환값이 없는 경우 사용한다.

```kotlin
fun report() {
  // 아무것도 반환하지 않으면 return Unit이 반환된다.
}

fun main() {
  val result = report()
  println(result)
}
```

- `Nothing`은 함수가 끝이 나지 않는 경우 사용한다.

  (ex `throw Exception`, `while (true) {yield ...}`)

`Nothing?`은 모든 Nullable 타입을 상속받아서 사용한다.

타입추론 과정에서 생긴 컴파일 오류를 명시적으로 만들어 줄 수 있다.

```kotlin
var user = if ( isPass() ) 42 else fail("Not Ready")
// 이 때 fail에서 예외를 던진다면, var user는 Nothing? 타입으로 타입추론된다.
// Error: Type mismatch: inferred type is "User" but "Nothing?" was expected
```

## `Unit?`는 사용할 일이 없지 않나?

`Nothing?`도 사용하니까, `Unit?`도 사용하면 괜찮지 않을까? 라는 생각으로 간혹 Unit을 Boolean 처럼 사용하는 경우가 있다.

- `Boolean`이 `true` or `false` 를 리턴하듯
- `Unit?` 은 `Unit` or `null` 을 리턴하기 때문이다.

```kotlin
// Boolean을 이용하는 경우
fun isCorrectKey(key: String): Boolean = ...

if (isCorrectKey(key)) return
```

```kotlin
// Unit? 을 이용하는 경우
fun verifyKey(key: String): Unit? = ...

verifyKey(key) ?: return
```

사용하는 코드만 보면 `Unit?`을 사용하는게 세련되보이지만, 읽을 때는 그렇지 않다.

개발자로 하여금 `verifyKey(key)`가 무엇a을 반환하는지 예측하기 어렵고, 오류가 생겼을 때 이유를 찾기 힘들게 만든다. `Unit?`을 사용하지 말자.

따라서 `Boolean`을 사용하는 형태로 변경하는 것이 좋다.

기본적으로 `Unit?`을 리턴하거나, 이를 기반으로 연산하는 것은 좋지 않다.

