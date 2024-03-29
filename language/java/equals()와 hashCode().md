# equals()와 hashCode()

java를 공부하시거나 업무를 수행하시는 분들은 이 두 함수가 매우 낯이 익을겁니다. 하지만 개인적으로 느끼기에 너무도 흔하기에 오히려 제대로 알기 어려운 부분인듯 하여, 이번에 이 두 함수의 차이점을 다시한번 짚어보며, 최대한 자세하게 알아볼까 합니다.

## equals()와 hsahCode()은 무엇일까? 그리고, 언제 사용할까?

우선 두 개의 질문이 생각나는데... 하나씩 해결해보도록 하죠.

**equals()와 hashCode()는 무엇일까?**

`equals()`  메소드는 비교 대상인 두 객체가 서로 같은지를 비교할 때 사용합니다. 이 메소드는 두 객체의 내용이 같은지를 확인하여 같음과 다름의 여부를 반환합니다. 즉, 동일하다면, true를 리턴하고 그렇지 않다면 false를 리턴하죠. 일반적으로는 클래스의 모든 필드를 비교하는 방식으로 해당 메소드를 구현합니다.

`hashCode()` 메소드는 객체를 식별하는데 사용합니다. 이 메소드는 정수 값으로 이루어진 객체의 해시 코드를 반환하며, 그 값은 객체의 내용을 기반으로 생성됩니다. 해시코드를 활용하면 객체를 빠르게 검색하고 비교할 수 있습니다. 그러나 경우에 따라 두 개의 다른 객체가 동일한 해시 코드를 가질 수 있으므로 해시 코드만으로는 절대적으로 두 객체가 동등한지 여부를 결정할 수 없습니다. 따라서, `hashCode()` 메소드를 구현할 때는 `euqals()` 메소드와 일치하도록 구현하는 것이 좋습니다.

**equals()와 hashCode()은 언제 사용할까?**

Java 에서는 해시 기반 컬렉션 프레임워크(해시맵과 같은 컬렉션)에서 객체를 검색하는 데 `hashCode()`를 사용합니다. 이 컬렉션에서 객체를 저장하거나 검색하기 전에, `hashCode()`를 호출하여 객체의 해시 코드를 가져옵니다. 그런 다음 해당 해시 코드를 가진 버킷에 객체를 저장합니다. 이를 퉁해 컬렉션에서 객체를 빠르게 검색할 수 있습니다. 동등한 객체를 동일한 버킷에 저장해야 하기 때문에, `hashCode()` 메소드를 구현할 때는 `equals()` 메소드와 일치하도록 구현하는 것이 좋습니다.

## 그렇다면, equals와 hashcode간의 관계는 무엇일까?

`equals()`와 `hashCode()`는 동일한 값을 리턴해야 합니다. 즉 아래와 같은 특성이 보장되어야 합니다.

```java
a.equals(b); // true
b.equals(a); // true
```

만약 위의 특성이 보장되면 두 객체의 hashcode가 동일하다는 것이 보장됩니다.

```java
a.haseCode() == b.hashCode() //true
```

그러나 두 객체의 hashCode()가 동일하다고 꼭 두 객체의 equals() 메소드가 항상 true가 보장되는 것은 아닙니다. 즉, 위에서 언급했듯이 a와 b 객체가 동일하지 않아도 가끔 hashCode가 동일할때가 있다는 거죠.

그래서 hashMap과 같은 해시 기반 컬렉션에서는 put과 get 같은 함수를 사용할 때 hashCode를 비교하여 동일여부를 판단하고 만약 동일하다면 2차로 equals()를 통해 동일 여부를 판단하여 나머지 연산을 수행합니다.

그런데, hashCode가 동일함에도 불구하고, equals를 통과하지 못하는 경우가 있는데요. 이걸 '해시 충돌'이라고 합니다.

## 해시 충돌(hash Collision)

해시 충돌은 위에서 언급했듯이 서로 다른 객체가 동일한 해시 코드를 갖는 경우입니다. 해시 함수의 출력 범위보다 입력 범위가 더 넓기 때문에, 해시 충돌은 불가피합니다. (정수의 조합 범위와 문자열의 조합 범위의 규모의 차이가 얼마나 나는지를 상상해보시면 될 것 같네요.)

만약에 해시 충돌이 발생했을 때는 어떻게 해야 할까요? 충돌을 해결하기 위한 방법 중 하나는 **Chaining** 입니다. 이 방법은 해시 테이블의 각 슬롯에 대해 연결 리스트를 유지하고, 동일한 해시 코드를 가진 객체는 해당 슬롯의 연결 리스트에 추가하는 것입니다. 따라서, 같은 해시 코드를 가진 객체가 있더라도 연결 리스트를 통해 모든 객체를 접근할 수 있습니다.

또 하나는 **Open Addressing** 입니다. 선형 탐사나 이차 탐사, 랜덤 탐사 등 다양한 방식으로 충돌이 발생한 슬롯 대신에 다른 슬롯을 찾아 저장하는 방법입니다.

## 요약

이상으로, hashCode와 equals라는 두 개의 함수에 대해 다시 한번 짚어보았습니다.

최대한 요약하자면, hashCode와 equals는 결국 비교하고자 하는 객체의 동등성을 보장하기 위한 함수였고, 이 특징을 활용하여 해시 맵과 같은 해시 기반의 컬렉션 내부에 활용되고 있습니다.

