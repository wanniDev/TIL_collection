package me.java.algorithm.ch3_dynamicprogramming;

public class P00_Binomial {
    /**
     * ( n )   ( n - 1 )   ( n - 1 )
     * (   ) = (       ) = (       )
     * ( r )   ( r - 1 )   (   r   )
     */

    /**
     * 이 식의 경우, n과 r이 커짐에 따라 반복되는 계산이 많아지고 그에따른 시간복잡도도 늘어난다.
     */
    int bino(int n, int r) {
        // base case : n = r (모든 원소를 다 고르는 경우 혹은 r = 0 (고를 원소가 없는 경우))
        if (r == 0 || n == r) return 1;
        return bino(n - 1, r - 1) + bino(n - 1, r);
    }

    int[][] cache = new int[30][30];
    int binoMemoization(int n, int r) {
        // basecase
        if (r == 0 || n == r) return 1;
        // -1이 아니라면 한 번 계산했던 값이니 곧장 반환
        if (cache[n][r] != -1)
            return cache[n][r] = binoMemoization(n - 1, r - 1) + binoMemoization(n - 1, r);
    }
}
