# 시뮬레이션: 회의실

```java
public int[] solution(int[] enter, int[] exit){
    int len = enter.length;
    for (int i = 0; i < len; i++) {
        enter[i]--;
        exit[i]--;
    }
    int[] enterIdx = new int[len];
    for (int i = 0; i < len; i++) {
        enterIdx[enter[i]] = i;
    }
    int sec = 0;
    int[] enterT = new int[len];
    int[] exitT = new int[len];
    for (int i = 0, j = 0; i < len; i++) {
        while (j < len && j <= enterIdx[exit[i]]) {
            enterT[enter[j]] = sec++;
            j++;
        }
        exitT[exit[i]] = sec++;
    }
    int[] answer = new int[len];
    for (int i = 0; i < len; i++) {
        for (int j = i + 1; j < len; j++) {
            if (!(exitT[i] < enterT[j] || exitT[j] < enterT[i])) {
                answer[i]++;
                answer[j]++;
            }
        }
    }
    return answer;
}
```