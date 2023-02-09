package me.java.algorithm.ch2_divide_and_conquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P03_Karatsuba_ {

    // num[]의 자릿수 올림을 처리한다.
    public static void normalize(ArrayList<Integer> num) {
        num.add(0);
        // 자릿수 올림을 처리한다.
        for (int i = 0; i + 1 < num.size(); i++) {
            if (num.get(i) < 0) {
                int borrow = (Math.abs(num.get(i)) + 9) / 10;
                num.set(i + 1, num.get(i + 1) - borrow);
                num.set(i, num.get(i) + borrow * 10);
            } else {
                num.set(i + 1, num.get(i + 1) + num.get(i) / 10);
                num.set(i, num.get(i) % 10);
            }
        }
        while(num.size() > 1 && num.get(num.size() - 1) == 0) num.remove(num.size() - 1);
    }
    // 두 긴 자연수의 곱을 반환한다.
    // 각 배열에는 각 수의 자릿수가 1의 자리에서부터 시작해 저장되어 있다.
    // 예: multiply({3, 2, 1}, {6, 5, 4}) = 123 * 456 = 56088 = {8, 8, 0, 6, 5}
    public static ArrayList<Integer> multiply(final ArrayList<Integer> a, final ArrayList<Integer> b) {
        ArrayList<Integer> c = new ArrayList<>();
        for (int i = 0; i < a.size() + b.size() + 1; i++) {
            c.add(0);
        }
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                c.set(i + j, c.get(i + j) + a.get(i) * b.get(j));
            }
        }
        normalize(c);
        return c;
    }


    // a += b * (10^k);를 구현합니다.
    public static void addTo(ArrayList<Integer> a, final ArrayList<Integer> b, int k) {
        int size = Math.max(a.size(), b.size() + k);
        while (a.size() != size) a.add(0);
        for (int i = 0; i < b.size(); i++) {
            a.set(i + k, a.get(i + k) + b.get(i));
        }
    }
    // a -= b;를 구현합니다. a >= b를 가정합니다.
    public static void subFrom(ArrayList<Integer> a, final ArrayList<Integer> b) {
        for (int i = 0; i < b.size(); i++) {
            a.set(i, a.get(i) - b.get(i));
        }
        normalize(a);
    }
    // 배열의 일부분만 따로 떼어냅니다.
    public static ArrayList<Integer> subList(ArrayList<Integer> a, int fromIndex, int toIndex) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            list.add(a.get(i));
        }
        return list;
    }
    // 두 긴 정수의 곱을 반환합니다.
    public static ArrayList<Integer> karatsuba(final ArrayList<Integer> a, final ArrayList<Integer> b) {
        int an = a.size(); int bn = b.size();
        // a가 b보다 짧을 경우 둘을 바꾼다.
        if (an < bn) return karatsuba(b, a);
        // 기저 사례: a나 b가 비어 있는 경우
        if (an == 0 || bn == 0) return new ArrayList<>();
        // 기저 사례: a가 비교적 짧은 경우 O(n^2) 곱셈으로 변경한다.
        if (an <= 50) return multiply(a, b);
        int half = an / 2;
        // a와 b를 밑에서 half 자리와 나머지로 분리한다.
        ArrayList<Integer> a0 = subList(a,0, half);
        ArrayList<Integer> a1 = subList(a, half, a.size());
        ArrayList<Integer> b0 = subList(b, 0, Math.min(b.size(), half));
        ArrayList<Integer> b1 = subList(b, Math.min(b.size(), half), b.size());
        // z2 = a1 * b1
        ArrayList<Integer> z2 = karatsuba(a1, b1);
        // z0 = a0 * b0
        ArrayList<Integer> z0 = karatsuba(a0, b0);
        // a0 = a0 + a1; b0 = b0 + b1
        addTo(a0, a1, 0); addTo(b0, b1, 0);
        // z1 = (a0 * b0) - z0 - z2;
        ArrayList<Integer> z1 = karatsuba(a0, b0);
        subFrom(z1, z0);
        subFrom(z1, z2);
        // result = z0 + z1 * 10^half + z2 * 10^(half*2)
        ArrayList<Integer> result = new ArrayList<>();
        addTo(result, z0, 0);
        addTo(result, z1, half);
        addTo(result, z2, half + half);
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = Stream.of(4, 3, 2, 1).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> b = Stream.of(8, 7, 6, 5).collect(Collectors.toCollection(ArrayList::new));

        System.out.println(karatsuba(a, b));
    }
}
