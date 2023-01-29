package me.java.algorithm.ch1.recursion;

import java.util.Arrays;

public class P02_Picnic {
    int n = 6;
    int m = 10;

    boolean[][] areFriends = new boolean[m][m];
    boolean[] taken = new boolean[n];

    int solution() {
        int[][] sp = {
            {0, 1}, {0, 2}, {1, 2}, {1, 3}, {1, 4},
            {2, 3}, {2, 4}, {3, 4}, {3, 5}, {4, 5}
        };

        int[][] graph = new int[m][m];
        for (int[] ints : sp) {
            areFriends[ints[0]][ints[1]] = true;
            graph[ints[0]][ints[1]] = 1;
        }

        for (int[] areFriend : graph) {
            System.out.println(Arrays.toString(areFriend));
        }

        return countPairings(taken);
    }

    private int countPairings(boolean[] taken) {
        int firstFree = -1;
        for (int i = 0; i < taken.length; i++) {
            if (!taken[i]) {
                firstFree = i;
                break;
            }
        }
        if (firstFree == -1)
            return 1;
        int ret = 0;
        for (int pairWith = firstFree + 1; pairWith < n; pairWith++) {
            if (!taken[pairWith] && areFriends[firstFree][pairWith]) {
                taken[firstFree] = taken[pairWith] = true;
                ret += countPairings(taken);
                taken[firstFree] = taken[pairWith] = false;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(new P02_Picnic().solution());
    }
}