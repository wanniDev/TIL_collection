# 자바 enum

자바의 enum은 열거형의 멤버로 구성되어 있는 집합을 나타내는 타입이며, java에서 사용되는 특수한 유형의 클래스입니다. 보통 상수로 사용되며, 해당 enum의 멤버로는 상수 메소드 등이 포함될 수 있습니다.

## enum의 사례

요일을 상수로 나타내는 상황을 예시로 들 수 있습니다. 월~금요일을 상수로 나타낼 수 있으며, 상수 메소드를 활용하여 해당 요일이 주말인지 아닌지를 판단하는 메소드를 구현하여, 다른 비즈니스상에 활용할 수 있습니다.

```java
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    public boolean isWeekend() {
        return (this == SUNDAY || this == SATURDAY);
    }
}
```

위의 코드는 다음과 같이 활용될 수 있습니다.

```java
Day day = Day.SUNDAY;
System.out.println(day.isWeekend()); // true

day = Day.MONDAY;
System.out.println(day.isWeekend()); // false
```

위의 코드를 통해, enum을 활용하면 각 요일이 주말인지 아닌지를 쉽게 판별할 수 있음을 확인할 수 있습니다.

## enum의 상수 메소드 구현 및 활용

Enum 내부에서 사용하는 상수 메소드는 해당 열거형의 각 요소에 특정 값을 연결하거나, 각 요소에 대해 특정 동작을 수행하도록 정의할 수 있습니다. 예를 들어, 각 요일에 대응하는 숫자를 표현하는 상수 메소드를 만들어 보겠습니다.

```java
public enum Day {
    SUNDAY(0), 
    MONDAY(1), 
    TUESDAY(2), 
    WEDNESDAY(3), 
    THURSDAY(4), 
    FRIDAY(5), 
    SATURDAY(6);

    private final int value;

    Day(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
```

위의 코드에서 `Day` 의 각 요일 상수에 0~6을 요소로 연결하고, 비즈니스 로직상에 해당 요소를 리턴하게 하였습니다. 위 코드를 활용하는 예시는 다음과 같습니다.

```java
Day day = Day.MONDAY;
System.out.println(day.getValue());  // 1
```

이러한 방식은 각 요소에 대한 특정 동작을 정의할 수 있으므로, 결과적으로는 코드의 가독성과 안정성을 높이는 데 도움이 될 수 있습니다. 이러한 방식을 좀 더 자세히 살펴보는 방법으로는 아래와 같은 방법이 있겠습니다.

1. **Java Enum**: Java에서 제공하는 Enum 클래스에 대한 공식 문서를 읽어보세요. Enum에 대한 기본적인 정보뿐 아니라, 사용 방법과 예시 코드도 포함되어 있습니다.
2. **Enum Methods**: Enum 클래스에는 valueOf, name, ordinal 등의 메소드가 내장되어 있습니다. 이런 메소드들을 어떻게 사용하는지에 대해 알아보세요.
3. **Enum Constructors**: Enum 요소에 특정 값을 연결하기 위해선 Enum의 생성자를 이용해야 합니다. Enum의 생성자와 초기화 방법에 대해 학습해보세요.
4. **Enum in Business Logic**: Enum을 비즈니스 로직에 어떻게 활용하는지에 대한 예시와 가이드를 찾아보세요. Enum을 어떻게 사용하면 로직의 안정성과 가독성을 높일 수 있는지를 알아보는 것이 중요합니다.
5. **Advanced Enum Usage**: Enum에 메소드를 추가하거나, 인터페이스를 구현하게 만드는 등 고급 사용법에 대해서도 알아보세요.

## Enum의 내장 메소드

1. **ordinal()**: Enum 상수의 위치(0부터 시작)를 반환합니다.
2. **name()**: Enum 상수의 이름을 문자열로 반환합니다.
3. **values()**: 해당 Enum의 모든 상수를 배열로 반환합니다.
4. **valueOf(String name)**: 주어진 문자열에 해당하는 Enum 상수를 반환합니다.

이러한 메소드들은 아래와 같이 활용될 수 있습니다.

```java
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
}

public class Main {
    public static void main(String[] args) {
        Day day = Day.MONDAY;

        // ordinal()
        System.out.println(day.ordinal());  // 출력: 1

        // name()
        System.out.println(day.name());  // 출력: MONDAY

        // values()
        for (Day d : Day.values()) {
            System.out.println(d);  // 출력: SUNDAY, MONDAY, ..., SATURDAY
        }

        // valueOf()
        Day dayFromString = Day.valueOf("TUESDAY");
        System.out.println(dayFromString);  // 출력: TUESDAY
    }
}
```

이런 내장 메소드들을 사용하면, Enum 상수에 대한 정보를 쉽게 얻거나, 문자열을 Enum 상수로 변환하는 등 다양한 작업을 수행할 수 있습니다.

Enum 클래스와 관련된 자세한 정보는 Oracle의 공식 Java 문서를 참고하시면 좋습니다. 해당 문서에는 Enum 클래스의 모든 메소드에 대한 설명과 사용 방법이 포함되어 있습니다. 또한, 직접 코드를 작성하면서 이러한 메소드들을 어떻게 활용하는지 실험해보는 것도 유익할 것입니다.