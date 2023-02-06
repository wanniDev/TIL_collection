package me.java.algorithm.ch2_divide_and_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class P02_Fence {
    private static final int[] fences = {7, 1, 5, 9, 6, 7, 3};

    public static void main(String[] args) {

        System.out.println(solve(0, fences.length - 1));
    }

    private static int solve(int left, int right) {
        if (left == right)
            return fences[left];

        int mid = (left + right) / 2;

        int ret = Math.max(solve(left, mid), solve(mid + 1, right));

        int low = mid;
        int high = mid + 1;
        int height = Math.min(fences[low], fences[high]);
        ret = Math.max(ret, height * 2);

        while (left < low || right > high) {
            if (high < right && (low == left || fences[low - 1] < fences[high + 1])) {
                high++;
                height = Math.min(height, fences[high]);
            } else {
                low--;
                height = Math.min(height, fences[low]);
            }
            ret = Math.max(ret, height * (high - low + 1));
        }
        return ret;
    }
}
