# 해싱&시간파싱:한 번 사용한 최초문자

```java
public int solution(String s){
      int answer = -1;
      char[] chArr = s.toCharArray();
      int[] dic = new int['z' - 'a'];
      for (int i = 0; i < chArr.length; i++) {
          char ch = chArr[i];
          dic[ch - 'a']++;
      }
      for (int i = 0; i < chArr.length; i++) {
          if (dic[chArr[i] - 'a'] == 1)
              return i + 1;
      }
      return answer;
  }

  public static void main(String[] args){
      P01OnceFirstChar T = new P01OnceFirstChar();
      System.out.println(T.solution("statitsics"));
      System.out.println(T.solution("aabb"));
      System.out.println(T.solution("stringshowtime"));
      System.out.println(T.solution("abcdeabcdfg"));
  }
```

