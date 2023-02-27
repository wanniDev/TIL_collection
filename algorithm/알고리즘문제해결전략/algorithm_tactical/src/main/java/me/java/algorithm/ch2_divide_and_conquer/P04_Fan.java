package me.java.algorithm.ch2_divide_and_conquer;

import java.util.ArrayList;

public class P04_Fan {
    public int hugs(String members, String fans) {
        int M = members.length();
        int F = fans.length();

        ArrayList<Integer> A = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int d = members.charAt(i) == 'M' ? 1 : 0;
            A.add(d);
        }


        for (int i = 0; i < F; i++) {
            int s = members.charAt(i) == 'M' ? 1 : 0;
            B.add(M - i - 1, s);
        }

        ArrayList<Integer> C = karatsuba(A, B);
        int sum = 0;
        for (int i = M - 1; i < F; i++) {
            if (C.get(i) == 0)
                sum++;
        }
        return sum;
    }

    private ArrayList<Integer> karatsuba(ArrayList<Integer> a, ArrayList<Integer> b) {
        // TODO 나중에 풀어보기
        return null;
    }

}
