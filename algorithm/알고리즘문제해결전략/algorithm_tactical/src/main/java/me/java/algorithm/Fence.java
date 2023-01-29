package me.java.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Fence {
    private final static List<Integer> fences = new ArrayList<Integer>();

    int solve(int left, int right) {
        // base case: 판자가 하나밖에 없을 때
        if (left == right)
            return fences.get(left);
        // [left, mid], [mid + 1, right] 두 구간으로 문제 분할
        int mid = (left + right) / 2;
        // 분할한 문제를 각개격파
        int ret = Math.max(solve(left, mid), solve(mid + 1, right));
        // 부분 문제 3: 두 부분에 모두 걸치는 사각형 중 가장 큰 것을 찾는다.
        int low = mid;
        int high = mid + 1;
        int height = Math.min(fences.get(low), fences.get(high));
        ret = Math.max(ret, height);

        // 사각형이 입력 전체를 덮을 때까지 확장해 나간다.
        while (left < low || high < right) {
            // 항상 높이가 더 높은 쪽으로 확장한다.
            if (high < right && (low == left || fences.get(low - 1) < fences.get(high + 1))) {
                high++;
                height = Math.min(height, fences.get(high));
            } else {
                low--;
                height = Math.min(height, fences.get(low));
            }
            // 확장한 후 사각형의 넓이
            ret = Math.max(ret, height * (high - low + 1));
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] h = new int[] {7, 1, 5, 9, 6, 7, 3};
        for (int fence : h) {
            fences.add(fence);
        }
        System.out.println(fences);
        System.out.println(new Fence().solve(0, fences.size() - 1));
    }
}
