package me.java.algorithm.ch2_divide_and_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class P02_Fence {
    private static final int[] fences = {7, 1, 5, 9, 6, 7, 3};

    public static void main(String[] args) {

        System.out.println(solve2(0, fences.length - 1));
    }

    private static int solve3(int left, int right) {
        if (left == right)
            return fences[left];

        int mid = left + (right - left) / 2;

        int ret = Math.max(solve3(left, mid), solve3(mid + 1, right));

        int mLeft = mid;
        int mRight = mid + 1;
        int height = Math.min(mLeft, mRight);
        ret = Math.max(ret, height * 2);

        while (left < mLeft || mRight < right) {
            if (mRight < right && (mLeft == left || fences[mLeft - 1] < fences[mRight + 1])) {
                mRight++;
                height = Math.min(height, fences[mRight]);
            } else {
                mLeft--;
                height = Math.min(height, fences[mLeft]);
            }
            ret = Math.max(ret, height * (mRight - mLeft + 1));
        }
        return ret;
    }

    private static int solve2(int left, int right) {
        // base case
        if (left == right)
            return fences[left];

        int mid = left + (right - left) / 2;

        int ret = Math.max(solve2(left, mid), solve2(mid + 1, right));

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
