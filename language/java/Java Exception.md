# Java Exception

Exception은 프로그램이 예상치 못한 이벤트 혹은 조건을 만났을 때 발생합니다. Exception이 발생하면 프로그램의 정상적인 흐름이 중단되며, 심각한 경우 프로그램이 아예 종료될 수도 있습니다.

따라서, Exception 처리는 프로그래밍 언어마다 제공하는 매커니즘을 올바르게 활용하고 예외 메시지를 명확하게 전달하여, 개발자가 후속 작업에 대한 방향을 제시해주어야 합니다. 예외 매커니즘의 구조는 대부분의 경우 try/catch/finally로 구성되어 있습니다.

## try/catch/finally 매커니즘

try 블록 내부에는 예외가 발생할 가능성이 있는 코드를 작성하고, 해당 코드가 발생하는 예외를 catch 블록에서 받아 해당 예외를 어떻게 처리할 지 결정합니다. 그리고 finally 블록 내부에는 예외 발생여부와 상관없이 해당 구문안의 코드를 실행시키게 되어 있습니다. 해당 블록의 경우 대체로 리소스 정리가 필요한 코드를 작성하는게 일반적입니다.

```java
public class Foo {
  public void hello() {
    try {
      // 비즈니스 로직 수행.
    } catch(Exception e) {
      // 예외 발생시 지정한 예외 메시지를 출력한다.
      System.out.println(e.getMessage());
    }
  }
}
```

## Checked exception & Unchecked exception

앞서 다뤘던 Exception은 일반적으로는 두가지 타입의 예외가 있습니다.

1. Checked exception
2. Unchecked exception

이 둘의 차이점은 컴파일러가 예외 처리를 강제하는지의 여부입니다.

### 1. Checked Exception

컴파일러가 예외를 강제로 처리하도록 하는 예외입니다. CheckedException은 예외가 발생할 수 있는 메소드를 작성할 대 반드시 예외를 처리해야 합니다. 이러한 예외는 보통 예외가 발생한 즉시 복구가 가능한 경우에 많이 활용됩니다. 예를 들어, FileNotFoundException, IOException 등이 있습니다.

### 2. Unchecked Exception

이 유형의 예외는 컴파일러가 강제로 처리하지 않습니다. UncheckedException은 주로 프로그래밍 오류로 인해 발생하는 예외로 예외를 처리하지 않아도 컴파일 과정에 문제가 생기지 않습니다. 대부분의 런타임 예외는 UncheckedException에 속하며, 이는 보통 프로그램의 버그를 나타냅니다. NullPointerException, ArrayIndexOutOfBoundsException, IllegalArgumentException 등이 있습니다.

기본적으로 CheckedException은 복구가 가능한 상황에서 개발자가 그 상황을 처리하도록 강제하기 위해 사용되며, UncheckedException은 프로그램의 심각한 문제를 나타내기 위해 사용됩니다. 따라서, CheckedException을 적절히 처리하여 프로그램의 안정성을 높이고, UncheckedException을 통해 프로그램의 버그를 찾아 수정할 수 있습니다.