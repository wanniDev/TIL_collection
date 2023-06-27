# 아이템4. inferred 타입으로 리턴하지 말라

코틀린의 타입 추론(type inferred)은 JVM 세계에서 가장 널리 알려진 코틀린의 특징이다. 자바10부터는 자바도 타입 추론을 도입했다.

inferred 타입은 정확하게 오른쪽에 있는 피연산자에 맞게 설정된다는 것을 기억해야 한다. 절대로 슈퍼클래스 또는 인터페이스로는 설정되지 않는다.

```kotlin
open class Animal
class Zebra: Animal()

fun main() {
  var animal = Zebra()
  animal = Animal() // fail
}

fun main() {
  var animal: Animal = Zebra()
  animal = Animal() // success
}
```

직접 조작할 수 없는 경우에는 문제를 간단하게 해결할 수 없다.

```kotlin
interface CarFactory {
  fun produce(): Car
}

val DEFAULT_CAR: Car = Fiat126P()
```

코드를 작성하다 보니 DEFAULT_CAR는 Car로 명시적으로 지정되어 있으므로 따로 필요 없다고 판단해서, 함수의 리턴 타입을 제거했다고 해보자.

```kotlin
interface CarFactory {
  fun produce() = DEFAULT_CAR
}

val DEFAULT_CAR = Fiat126()
```

`CarFactory`는 이제 `Fiat126P` 이외의 자동차를 생산하지 못한다.

리턴 타입은 API를 잘 모르는 사람에게 전달해 줄 수 있는 중요한 정보이다. 따라서 타입은 외부에서 확인할 수 있게 명시적으로 지정해 주는 것이 좋다.

## 정리

타입을 확실하게 지정해야 하는 경우에는 명시적으로 타입을 지정해야 한다는 원칙만 갖고 있으면 된다. 이는 굉장히 중요한 정보이므로, 숨기지 않는 것이 좋다.(아이템 14: 타입이 명확하게 보이지 않는 경우 확실하게 저장하라’에서도 자세하게 설명한다.)

또한 안전을 위해서 외부 API를 만들 때는 반드시 타입을 지정하고, 이렇게 지정한 타입을 특별한 이유와 확실한 확인 없이는 제거하지 말자. interred 타입은 프로젝트가 진전될 때, 제한이 너무 많아지거나 예측하지 못한 결과를 낼 수 있다.