package me.java.leetcode.ch02_recurrsion2.ch02_00_divide_and_conquer;

import java.util.Arrays;

public class P00_MergeSort {
    public int[] mergeSort(int[] input) {
        // base case
        int len = input.length;
        if (len <= 1)
            return input;


        // 1. divide
        int pivot = len / 2;
        int[] left = mergeSort(Arrays.copyOfRange(input, 0, pivot));
        int[] right = mergeSort(Arrays.copyOfRange(input, pivot, len));

        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right) {
        int retCursor = 0;
        int leftCursor = 0;
        int rightCursor = 0;
        int[] ret = new int[left.length + right.length];

        // 2,3. conquer & merge
        while (leftCursor < left.length && rightCursor < right.length) {
            if (left[leftCursor] < right[rightCursor]) {
                ret[retCursor++] = left[leftCursor++];
            } else {
                ret[retCursor++] = right[rightCursor++];
            }
        }
        // 위와 같은 식으로 정렬을 수행하면, 필연적으로 왼쪽 혹은 오른쪽 부분의 일부를 채우지 못한 상태로 반복문이 끝난다.
        // 그러나 이러한 경우는 합치는 과정에서 정렬된 상태로 남게된다.
        while (leftCursor < left.length) {
            ret[retCursor++] = left[leftCursor++];
        }
        while (rightCursor < right.length) {
            ret[retCursor++] = right[rightCursor++];
        }
        return ret;
    }

    // test
    public static void main(String[] args) {
        int[] result = new P00_MergeSort().mergeSort(new int[]{9, 2, 11, 3, 10, 8, 4, 5});
        System.out.println(Arrays.toString(result));
    }
}
