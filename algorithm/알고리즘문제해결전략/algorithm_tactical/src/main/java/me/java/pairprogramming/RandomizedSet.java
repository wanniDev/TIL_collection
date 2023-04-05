package me.java.pairprogramming;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizedSet {
    private Map<Integer, Integer> elementToIdx;
    private List<Integer> list;
    private Random rand;

    public RandomizedSet() {
        this.elementToIdx = new HashMap<>();
        this.list = new ArrayList<>();
        this.rand = ThreadLocalRandom.current();
    }

    public boolean add(int val) {
        if (elementToIdx.containsKey(val))
            return false;
        int appendIdx = list.size();
        elementToIdx.put(val, appendIdx);
        list.add(appendIdx, val);
        return true;
    }

    public boolean remove(int val) {
        if (!elementToIdx.containsKey(val))
            return false;
        int targetIdx = elementToIdx.get(val);
        int lastElement = list.get(list.size() - 1);
        list.set(targetIdx, lastElement);
        list.remove(list.size() - 1);
        elementToIdx.remove(val);
        elementToIdx.put(lastElement, targetIdx);
        return true;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}
