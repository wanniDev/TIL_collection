package me.java.algorithm.ch2_divide_and_conquer;

import java.util.ArrayList;
import java.util.List;

public class P03_Karatsuba {

    public ArrayList<Integer> normalize(ArrayList<Integer> num) {
        num.add(0);

        for (int i = 0; i + 1 < num.size(); i++) {
            if (num.get(i) < 0) {
                int borrow = Math.abs(num.get(i) + 9) / 10;
                num.set(i + 1, num.get(i + 1) - borrow);
                num.set(i, num.get(i) + borrow * 10);
            } else {
                num.set(i + 1, num.get(i + 1) + num.get(i) / 10);
                num.set(i, num.get(i) % 10);
            }
        }

        if (num.get(num.size() - 1) == 0)
            num.remove(num.size() - 1);
        return num;
    }

    // a += b * (10^k)
    private ArrayList<Integer> addTo(ArrayList<Integer> a, ArrayList<Integer> b, int k) {
        a = ensureSize(a, Math.max(a.size(), b.size() + k));
        for (int i = 0; i < b.size(); i++) {
            a.set(i + k, a.get(i + k) + b.get(i));
            a = normalize(a);
        }
        return a;
    }

    // a -= b
    ArrayList<Integer> subFrom(ArrayList<Integer> a, ArrayList<Integer> b) {
        a = ensureSize(a, Math.max(a.size(), b.size()) + 1);
        for (int i = 0; i < b.size(); i++) {
            a.set(i, a.get(i) - b.get(i));
        }
        a = normalize(a);
        return a;
    }

    private ArrayList<Integer> ensureSize(ArrayList<Integer> list, int size) {
        list.ensureCapacity(size);
        while (list.size() < size) {
            list.add(0);
        }
        return list;
    }

    ArrayList<Integer> karatsuba(ArrayList<Integer> a, ArrayList<Integer> b) {
        int a_size = a.size();
        int b_size = b.size();

        // a < b swap
        if (a_size < b_size)
            return karatsuba(b, a);

        int half = a_size / 2;

        ArrayList<Integer> a0 = new ArrayList<>(a.subList(0, half));
        ArrayList<Integer> a1 = new ArrayList<>(a.subList(half, a.size()));
        ArrayList<Integer> b0 = new ArrayList<>(b.subList(0, Math.min(b_size, half)));

        ArrayList<Integer> b1 = new ArrayList<>(b.subList(Math.min(b.size(), half), b.size()));
        // z2 = a1 * b1
        ArrayList<Integer> z2 = karatsuba(a1, b1);
        // z0 = a0 * b0
        ArrayList<Integer> z0 = karatsuba(a0, b0);
        // a0 = a0 + a1;b0 = b0 + b1
        z0 = addTo(a0, a1, 0);
        z0 = addTo(b0, b1, 0);

        // z1 = (a0 * b0) - z0 -z2
        ArrayList<Integer> z1 = karatsuba(a0, b0);
        z1 = subFrom(z1, z0);
        z1 = subFrom(z1, z2);

        // ret = z0 + z1 * 10^half + z2 * 10^(half * 2)
        ArrayList<Integer> ret = new ArrayList<>();
        addTo(ret, z0, 0);
        addTo(ret, z1, half);
        addTo(ret, z2, half + half);
        return ret;
    }

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        a.add(-5);
        b.add(7);
        System.out.println(new P03_Karatsuba().multiply(a, b));
    }

    public ArrayList<Integer> multiply(List<Integer> a, List<Integer> b) {
        ArrayList<Integer> c = ensureSize(new ArrayList<>(), a.size() + b.size());
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                c.set(i + j, a.get(i) * b.get(j));
            }
        }

        normalize(c);
        return c;
    }
}
