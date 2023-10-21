# 시뮬레이션:비밀번호

> https://www.inflearn.com/course/%EC%9E%90%EB%B0%94-%EC%BD%94%EB%94%A9%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%B5%9C%EC%8B%A0%EA%B8%B0%EC%B6%9C

```java
public class Solution {
  int[] dirs = {
    {0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
  };
  
  public int solution(int[] keypad, String password) {
    int[][] kArr = initKeypad(keypad, password);
    int[][] dic = initDic();
    
    buildDic(dic, kArr);
    
    return scan(dic, password);
  }
  
  private int scan(int[][] dic, String password) {
    int ret = 0;
    char[] pArr = password.toCharArray();
    int from = cArr[0] - '0' - 1;
    for (int i = 1; i < pArr.length; i++) {
      int to = cArr[i] - '0' - 1;
      ret += dic[from][to];
      prev = next;
    }
    return ret;
  }
  
  private void buildDic(int[][] dic, int[][] kArr) {
    for (int r = 0; r < dic.length; r++) {
      for (int c = 0; c < dic[0].length; c++) {
        int from = kArr[r][c]- 1;
        for (int[] dir : dirs) {
          int nr = r + dir[0];
          int nc = c + dir[1];
          if (nr < 0 || nr >= keypad.length || nc < 0 || nc >= keypad[0].length) {
            continue;
          }
          int to = kArr[nr][nc] - 1;
          dic[from][to] = 1;
        }
      }
    }
  }
  
  private int[][] initDic() {
    int[][] ret = new int[9][9];
    for (int r = 0; r < ret.length; r++) {
      for (int c = 0; c < ret[0].length; c++) {
        ret[r][c] = r == c ? 0 : 2;
      }
    }
    
    return ret;
  }
  
  private int[][] initKeypad(int[] keypad, String password) {
		int[][] ret = new int[3][3];
    for (int i = 0; i < keypad.length; i++) {
      ret[i / 3][i % 3] = keypad[i];
    }
    return ret;
  }
}
```

