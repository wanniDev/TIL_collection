package me.java.leetcode.ch01_recurrsion.ch01_02_Complexity;

public class P06_Pow {
    public double myPow(double x, int n) {
        if (x == 0)
            return 0;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        return fastPow(x, n);
    }

    private double fastPow(double x, int n) {
        if (n == 0)
            return 0;
        double half = fastPow(x, n / 2);
        if (n % 2 == 0)
            return half * half;
        else
            return half * half * x;
    }
}
