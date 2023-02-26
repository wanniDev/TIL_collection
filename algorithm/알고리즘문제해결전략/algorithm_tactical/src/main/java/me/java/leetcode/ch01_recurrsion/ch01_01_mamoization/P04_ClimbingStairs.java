package me.java.leetcode.ch01_recurrsion.ch01_01_mamoization;

import java.util.HashMap;
import java.util.Map;

public class P04_ClimbingStairs {
    private final Map<Integer, Integer> cache = new HashMap<>(Map.of(1, 1, 2, 2));
    public int climbStairs(int n) {
        if (cache.containsKey(n))
            return cache.get(n);
        cache.put(n, climbStairs(n - 2) + climbStairs(n - 1));
        return cache.get(n);
    }
}
