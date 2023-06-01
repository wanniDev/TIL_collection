package me.java.pairprogramming;

public class Picker {
    private final RandomizedSet randomizedSet;

    private Picker() {
        this.randomizedSet = LazyHolder.instance;
    }

    public static Picker instance() {
        return new Picker();
    }

    public int pickFrom(int... args) {
        if (args.length < 2)
            throw new IllegalArgumentException("매개 변수에 최소 2개 이상의 서로 다른 숫자를 전달 해주세요.");

        for (int arg : args) {
            randomizedSet.add(arg);
        }

        return randomizedSet.getRandom();
    }

    private static class LazyHolder {
        private static final RandomizedSet instance = new RandomizedSet();
    }
}
