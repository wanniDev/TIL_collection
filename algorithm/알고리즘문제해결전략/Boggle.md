

# Boggle

보글은 25칸의 알파벳 격자를 가지고 진행하는 게임이다.

이 게임의 목적은 상하좌우/대각선으로 인접한 칸들의 글자들을 이어서 단어를 찾아내는 것이다. 각 글자들은 대각선으로도 이어질 수 있고, 한 글자가 두 번 이상 사용될 수도 있다. 주어진 칸에서 시작해서 특정 단어를 찾을 수 있는지 확인하는 문제를 풀어보자.

> 5x5 board

```
z	h	z	z	z
z	e	z	z	z
z	z	l	l	z
z	z	z	z	o
z	z	z	z	z
```

문제 풀이에 집중하기 위해, 찾고자 하는 단어는 `hello` 하나만을 대상으로 진행하도록 한다.

## sub routine

대부분의 재귀문제가 그러하듯 문제풀이를 진행하기 위해선, 우선 문제를 최대한 하나로 쪼개야 한다.

이번 문제의 경우는 `hello`라는 단어를 맨 앞에서부터 하나씩 쪼개보면 된다. 문제 풀이 루틴은 다음과 같겠다.

1. board를 순회하면서, word의 첫 단어와 일치하는 곳의 위치를 구한다.
2. 그 위치를 기준으로 word를 하나씩 찾고 상/하/좌/우/대각선 으로 돌면서 찾고자 하는 단어와 이어지는지 확인한다.

### 0. 전역변수 선언하기

문제 풀이의 편의상 하나의 클래스에서 main 메소드로 답을 호출할 것이다. 따라서, 대부분의 메소드와 변수의 접근제어자는 static으로 지정한다.

```java
public class Problem {
  // 보글 격자 보드
  private static final char[][] board = {
    {'z', 'h', 'z', 'z', 'z'},
    {'z', 'e', 'z', 'z', 'z'},
    {'z', 'l', 'l', 'z', 'z'},
    {'z', 'z', 'z', 'o', 'z'},
    {'z', 'z', 'z', 'z', 'z'}
  };

  private static final int rowLen = dictionary.length;
  private static final int colLen = dictionary[0].length;
	
  // 상하좌우대각선 방향으로 이동하는데 사용되는 배열
  private static final int[][] directions = {
    {-1,-1},
    {-1,0},
    {-1,1},
    {1,-1},
    {1, 0},
    {1,1},
    {0,-1},
    {0,1}
  };
}
```



### 1. 일치하는 단어의 위치 조회하기

순회하면서, 맞는 단어를 찾으면 되기 때문에, 굳이 설명은 필요없을 것 같다.

`hasWord(...)` 함수는 찾고자 하는 단어의 키워드가 있을 경우, 그 단어를 기준으로 이어지는 단어가 찾고자 하는 단어인지에 대한 여부를 확인하는 함수가 될 것이다. solution 함수는 이제 찾고자 하는 단어의 첫 번째 문자가 될 것이기 때문에, 함수에 단어뿐만 아니라, 해당 단어의 위치를 나타내는 좌표도 포함해야 한다.

```java
private static boolean solution(String word) {
  for (int i = 0; i < rowLen; i++) {
    for (int j = 0; j < colLen; j++) {
      if (hasWord(i, j, word))
        return true; // hasWord가 재귀함수를 타면서 이상이 없다면, 찾고자 하는 단어가 있다는 뜻이므로, true를 리턴
    }
  }
  // 끝까지 순회했다는 것은 결국 찾고자 하는 단어가 이어지는 패턴이 없다는 뜻이다.
  return false;
}
```

### 2. 찾은 문자를 기준으로 상하좌우대각선으로 순회하며, 단어가 이어지는지를 확인해보기

이 재귀함수는 기저사례를 통과하면, 모든 방향의 단어를 찾으면서 이어지는 단어를 조회하고, 조회여부에 따라 적절한 답을 리턴할 것이다. 문제 풀이를 단순하게 하기 위해 조회하고자 하는 단어는 찾고자 하는 단어의 맨앞을 기준으로 조회 여부에 따라 찾고자 하는 단어를 앞에서 하나씩 줄일것이다.

이 함수는 결국 답을 찾는데 사용되는 핵심 재귀함수로, 기저사례(basecase)를 확실히 정해야한다.

#### Base case

1. 좌표가 범위에 벗어나는 경우 false
2. 찾는 단어가 일치하지 않는 경우 false
3. 찾고자 하는 단어의 길이가 1개일 경우 true

기저사례를 통과한 단어는 찾고자 하는 단어의 기준에 부합하므로, 사방팔방으로 단어를 순회하면서 그 다음 문자가 찾고자 하는 단어의 문자와 일치하는지 확인하는 행위를 반복한다.

```java
private static boolean hasWord(int row, int col, String word) {
	if (!isRange(row, col))
    return false;
  if (board[row][col] != word.charAt(0))
    return false;
  if (word.length() == 1)
    return true;
  // 상하좌우대각선으로 이동하면서, 기존 단어의 다음 문자의 일치 여부를 확인한다.
  for (int[] dir : directions) {
		if (hasword(row + dir[0], col + dir[1], word.substring(1)))
      return true;
  }
  return false;
}

private static boolean isRange(int row, int col) {
  return row < rowLen && col < colLen && row >= 0 && col >= 0;
}
```

## Solution



```java
public class Problem {
  private static final char[][] board = {
    {'z', 'h', 'z', 'z', 'z'},
    {'z', 'e', 'z', 'z', 'z'},
    {'z', 'l', 'l', 'z', 'z'},
    {'z', 'z', 'z', 'o', 'z'},
    {'z', 'z', 'z', 'z', 'z'}
  };

  private static final int rowLen = board.length;
  private static final int colLen = board[0].length;

  private static final int[][] direction = {
    {-1,-1},
    {-1,0},
    {-1,1},
    {1,-1},
    {1, 0},
    {1,1},
    {0,-1},
    {0,1}

  };
  public static void main(String[] args) {

    String word = "hello";
    boolean answer = solution(word);

    System.out.println("word : " + word + " is present? " + answer);
  }

  private static boolean solution(String word) {
    for (int i = 0; i < rowLen; i++) {
      for (int j = 0; j < colLen; j++) {
        if (hasWord(i, j, word))
          return true;
      }
    }
    return false;
  }

  private static boolean hasWord(int row, int col, String word) {
    if (!isRange(row, col))
      return false;
    if (board[row][col] != word.charAt(0))
      return false;
    if (word.length() == 1)
      return true;
    for (int[] dirs : direction) {
      int nextRow = row + dirs[0];
      int nextCol = col + dirs[1];
      if (hasWord(nextRow, nextCol, word.substring(1)))
        return true;
    }
    return false;
  }

  private static boolean isRange(int row, int col) {
    return row < rowLen && col < colLen && row >= 0 && col >= 0;
  }
}
```

