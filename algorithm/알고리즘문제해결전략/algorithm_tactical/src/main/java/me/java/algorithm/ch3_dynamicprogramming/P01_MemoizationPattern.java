package me.java.algorithm.ch3_dynamicprogramming;

public class P01_MemoizationPattern {
    /**
     * 메모이제이션은 동적 계획법에서 자주 사용되는 기법이라 템플릿처럼 하나를 정해놓으면
     * 디버깅이 수월해진다.
     */

//    int[][] cache = new int[2500][2500];
//
//    // a와 b는 각각 (0, 2500) 구간 안의 정수
//    // 반환 값은 항상 int 형 안에 들어가는 음이 나닌 정수
//    int someObscureFunction(int a, int b) {
//        // base case
//        if (...) return ...;
//
//        int ret = cache[a][b];
//        // (a, b)에 대한 답을 구한 적이 있으면 곧장 반환
//        if (ret != -1) return ret;
//        // 여기에서 답을 계산한다.
//        ...
//        return ret;
//    }
}
