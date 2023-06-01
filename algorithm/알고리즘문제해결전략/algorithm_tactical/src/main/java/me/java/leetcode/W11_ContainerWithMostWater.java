package me.java.leetcode;

public class W11_ContainerWithMostWater {
    public int maxArea(int[] height) {
        int result = 0;
        int l = 0, r = height.length - 1;
        while (l < r) {
            result = Math.max(result, (r - l) * Math.min(height[l], height[r]));
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        W11_ContainerWithMostWater sol = new W11_ContainerWithMostWater();
        System.out.println(sol.maxArea(new int[] {1,8,6,2,5,4,8,3,7}));
    }
}
