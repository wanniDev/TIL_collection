# K-th Symbol in Grammar

> https://leetcode.com/explore/learn/card/recursion-i/253/conclusion/1675/

0으로 시작되는 숫자가 있다.

0은 확장될 때, 자식에게 자기 자신을 복제하고 그리고 1을 생성한다.

마찬가지로 1이 확장되면, 자식에게 자기 자신을 복제하고, 0을 생성한다.

`n`과`k`가 주어졌을 때 `n`번째 줄에서 `k^th`인덱스에 해당하는 숫자를 리턴하는 함수를 구현하라

## 풀이

0으로 시작해서 차근차근 `n`까지 확장해보고 마지막에 `k`번째 노드의 수의 부모 노드의 `k`번째 노드를 추적해보면, 재귀 방식으로 풀이할 수 있다는 것을 알 수 있게 된다.

```
					  '0'								n = 1, k = 1
				0       '1'						n = 2, k = 2
			0     1    '1'   0			n = 3, k = 3
	   0  1 0   1 '1' 0 0 1			n = 4, k = 5
```

```java
public class Solution {
	public int kthGrammar(int n, int k) {
    if (n == 1)
      return 0;
    int parent = kthGrammar(n - 1, (k + 1) % 2);
    if (parent == 1) {
      return k % 2 != 0 ? 1 : 0;
    } else {
      return k % 2 == 0 ? 1 : 0;
    }
  }
}
```

