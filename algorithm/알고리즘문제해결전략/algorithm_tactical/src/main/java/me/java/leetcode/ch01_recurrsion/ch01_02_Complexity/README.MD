# 복잡도 계산하기 - 재귀(Recursion)
## 시간 복잡도
> 주어진 재귀 알고리즘의 시간 복잡도를 `O(T)`라고 했을 때, `O(T)`의 시간 복잡도 공식은 `R`(재귀 함수가 호출된 횟수)에 각 함수에 수행되는 연산의 시간 복잡도 `O(s)`를 곱한 값이라 할 수 있다.
> 
> `O(T) = R * O(s)`

### 예시
문자 배열을 재귀함수로 순차적으로 출력하는 함수를 예로 들어보자.

```java
private static void printReverse(char [] str) {
  helper(0, str);
}

private static void helper(int index, char [] str) {
  if (str == null || index >= str.length) {
    return;
  }
  helper(index + 1, str);
  System.out.print(str[index]);
}
```
위 함수의 경우, helper 함수가 문자 배열의 길이만큼 호출이 된다. 그리고, 각 함수에서 수행되는 연산은 단순히 문자 하나를 출력하는 연산 한개를 고정으로 호출하기 때문에, 각 함수의 시간복잡도는 O(1)이다.
즉 위 함수의 시간복잡도는 다음과 같을 수 있다.
```
O(T) = len(배열의 길이) * O(1) = O(N)
```

## 공간 복잡도
> 공간 복잡도를 계할 때 크게 두 가지 부류로 나눠서 계산해볼 수 있다.
> 
> 1. `recursion related`
> 2. `non-recursion related`

### Recursion Related Space
간단하게 정의하면, 재귀가 호출되는 횟수만큼 공간복잡도 역시 비례해서 증가되는 구조라 할 수 있겠다. 따라서, 재귀함수가 과도하게 호출될 경우,  [stack overflow](https://en.wikipedia.org/wiki/Stack_overflow) 현상이 발생할 수 있다.
하지만, 한 번 함수가 수행되면 해당 함수가 차지하고 있던 메모리는 돌려주게 된다.

### Non-Recursion Related Space
이름에서 유추해볼 수 있는데, 재귀 함수의 호출횟수와 상관없이 고정적으로 메모리를 차지하는 구조라고 할 수 있다.
대부분 전역 변수를 선언하고, 재귀 함수들이 해당 전역 변수를 의존하고 있는 형태의 코드라고 할 수 있다.
**memoization** 기법역시 이러한 형태의 구조로 이루어져 있다.

피보나치 수열을 재귀로 문제를 풀 경우, 필연적으로 반복된 연산을 수행할 수 밖에 없는데, 이럴 때, 전역 변수로 캐싱이 가능한 자료구조를 선언하고, 최초의 연산 결과를 보관하고 필요에 따라 가져다 씀으로서, 수열의 반복 연산을 최소화 할 수 있다.

물론 요즘에는 하드웨어 성능이 우수하고 메모리 값이 매우 저렴하여, 걱정할 필요는 없겠지만, Non-Recursion Related Space의 경우 재귀 함수가 수행된 이후에도 메모리에 그대로 할당 되어있기 때문에, 알고리즘을 수행함으로서 차지하는 메모리의 양이 어느정도인지 근사치로라도 알아둘 필요는 있을 것 같다.

## 꼬리 재귀
> 꼬리 제귀란 함수의 마지막 호출 지점이 재귀 함수로 이루어져 있는 형태를 이야기 한다.
> 
> 즉, 마지막 호출 지점에 추가 연산 없이 어직 재귀 함수만 구성되어 있어야 한다.

코드를 통해 좀 더 자세히 파악해보자.

```java
public class Main {
    
  private static int helper_non_tail_recursion(int start, int [] ls) {
    if (start >= ls.length) {
      return 0;
    }
    // 마지막 호출 지점에 재귀 호출 이후로 추가 연산작업이 수행되기 때문에 꼬리 재귀가 아니다.
    return ls[start] + helper_non_tail_recursion(start+1, ls);
  }

  public static int sum_non_tail_recursion(int [] ls) {
    if (ls == null || ls.length == 0) {
      return 0;
    }
    return helper_non_tail_recursion(0, ls);
  }

  //---------------------------------------------

  private static int helper_tail_recursion(int start, int [] ls, int acc) {
    if (start >= ls.length) {
      return acc;
    }
    // 마지막 호출 지점에 오직 재귀 함수만 선언되었기 때문에 꼬리 재귀라 할 수 있다.
    return helper_tail_recursion(start+1, ls, acc+ls[start]);
  }
    
  public static int sum_tail_recursion(int [] ls) {
    if (ls == null || ls.length == 0) {
      return 0;
    }
    return helper_tail_recursion(0, ls, 0);
  }
}
```
꼬리 재귀의 이점은 재귀 호출 이후에 쌓이는 스택의 리소스를 재사용 함으로서 스택의 오버헤드를 최소화할 수 있다는 것이다.

그러나 모든 프로그래밍 언어가 꼬리 재귀함수의 최적화 기능을 활용할 수 있는 것은 아니다.

Java와 Python의 경우, 기본적으로는 꼬리 재귀 최적화를 지원하지 않는다. 하지만  [TCO by using lambda expressions](https://stackoverflow.com/questions/13591970/does-python-optimize-tail-recursion)을 통해 꼬리 재귀의 최적화를 활용해볼 수는 있다.
