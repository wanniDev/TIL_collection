package me.java.algorithm.ch2_divide_and_conquer;

public class P00_FastSum {
    public long fastSum(int n) {
        if (n == 1)
            return 1;
        if (n % 2 == 1)
            return fastSum(n - 1) + n;
        return fastSum(n / 2) * 2 + (long) (n/2) * (n/2);
    }

    public long slowSum(int n) {
        long sum = 0;
        for (int i = n; i > 0; i--) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        long l1 = System.currentTimeMillis();
        System.out.println(new P00_FastSum().fastSum(Integer.MAX_VALUE));
        long l2 = System.currentTimeMillis();
        System.out.printf("fastSum: %dms\n", l2 - l1);

        long l3 = System.currentTimeMillis();
        System.out.println(new P00_FastSum().slowSum(Integer.MAX_VALUE));
        long l4 = System.currentTimeMillis();
        System.out.printf("slowSum: %dms\n", l4 - l3);
    }
}