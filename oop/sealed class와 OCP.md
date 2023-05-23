# sealed class와 OCP

## Enum의 한계

이펙티브 자바를 보면, 기존에 상수로 정의했던 특정 값들의 집합으로 enum을 사용하는걸 권고하는 내용이 있습니다.

enum을 통해 기존에 선언했던 상수와는 전혀 다른 값을 사용하는 경우를 미연에 방지하기 위해서 입니다..

그러나, enum의 한계는 아래의 경우를 해결하고자 할 때 문제가 생깁니다.

```kotlin
// enum은 동일한 필드 구성을 강요하므로, 아래와 같은 코드는 불가능하다.
enum class Result {
  SUCCESS,
  FAILED(val message)
}
```

## Sealed class

Enum과 같은 value의 제약사항의 경우는 sealed class로 극복이 가능합니다.

```kotlin
sealed class Result<out T : Any> {
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Failed(val exception: Exception) : Result<Nothing>()
    object InProgress : Result<Nothing>()
}
```

## sealed class와 when의 사용

```kotlin
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()

    //        data class Failed(val exception: Exception) : Result<Nothing>()
    sealed class Failed(val exception: Exception, val message: String?) : Result<Nothing>() {
        class NetworkError(exception: Exception, message: String?) : Failed(exception,message)
        class StorageIsFull(exception: Exception, message: String?) : Failed(exception, message)
    }

    object InProgress : Result<Nothing>()
}

fun start() {
    val result = getResult()
    processResult(result)
}

fun processResult(result: Result<String>) {
    when (result) {
        is Result.Success -> println(result.data)
        is Result.Failed -> {
            println(result.exception, result.exception)
        }
        is Result.InProgress -> println("processing!!")

    }.exhaustive
}

fun getResult(): Result<String> {
      //return Result.Success("result is success")
    return Result.Failed.NetworkError(Exception("Wow Exception!!"), "Network connection is failed")
}
```

## OCP와 sealed class

사실 이 글을 쓰게 된 이유는 sealed class를 소개하는 것보다는 이 클래스를 사용하고, 나중에 Result 항목을 늘리게 되면, 해당 when 구문을 찾아서 반영해야하는 상황이 생길 수 있다.

그렇다면, 이러한 행위는 과연 OCP를 위반하는 행위일까? 이 이슈는 생각보다 의견이 분분합니다. 사실 이러한 류의 이슈는 개발을 하다보면 얼마든지 생길 수 있는 경우인데요. 저의 경우는 ***'OCP'를 원칙적으로 위반하는 행위지만, SOLID를 만든 목적의 취지에는 부합하다*** 라고 이야기 하고 싶습니다.

왜냐면 OCP의 정석은 기존의 코드를 변형할 필요없이 새로운 구현체를 통해, 이루어지는 확장을 추구하는 것이기 때문입니다. 따라서 어떠한 경우에도 추가된 코드로 인해, 기존 코드를 변경해야하 한다면, 그것 역시 변형에 닫혀있음 보장하지 못하는 상황이라 할 수 있겠죠.

그러나, 그렇다고 sealed class를 사용하는 것은 OCP를 위반하니, 사용하면 안된다는 것은 잘못된 생각이라 봅니다.

사실 SOLID 원칙을 만든 이유 중 하나가 바로 **객체지향의 구조를 짜임새 있게 만들어, 유지보수의 효율을 높이는 것** 이기 때문입니다.

sealed class 와 when을 활용하고, sealed class의 항목이 확장됨에 따라 when 구문의 코드를 변형하는 것은, 분명 OCP의 원칙을 위반하는 것은 맞습니다.(아닐수도 있구요.) 그러나, 이러한 스타일의 코드를 씀으로서 when 구문에 추가해야할 조건 분기문이 누락되었는지를 컴파일 타임에 확인하고 바로 적용할 수 있는 것은 개발자의 시간 리소스를 절약하여 **유지보수의 효율을 높이는 것**이라 할 수 있습니다. 따라서, sealed class와 when을 같이 사용하는 것은 분명 OCP를 위반하지만 OCP와 같은 SOLID가 추구하는 가치관에는 부합하다 라고 의견을 내고 싶네요!

### 출처

- https://tourspace.tistory.com/467
- https://discuss.kotlinlang.org/t/do-sealed-exhaustive-when-statements-break-ocp-open-closed-principle/23610