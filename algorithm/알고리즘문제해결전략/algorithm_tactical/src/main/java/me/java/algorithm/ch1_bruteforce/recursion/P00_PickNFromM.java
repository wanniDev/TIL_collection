package me.java.algorithm.ch1_bruteforce.recursion;

import java.util.*;

public class P00_PickNFromM {

    static List<List<Integer>> result = new ArrayList<>();

    public static void main(String[] args) {
        LinkedList<Integer> pickups = new LinkedList<>();
        int len = 10;
        int pick = 4;
        pick(len, pickups, pick);
        System.out.println("------");
        System.out.println(result);
    }

    /*
    pickUps 를 그대로 넣으면, 처음엔 제대로 insert 되는 것 같다. 하지만, 결과 list에 전부 동일한 참조값을 가진 리스트들이 들어 있기 때문에,
    pickUps 의 element에 변화가 생길 때마다, list의 모든 element들에 영향을 끼친다. 즉, 마지막 단계에서 함수들을 마무리하는 시점에서 pickUps의
    원소들을 비울 때, 마찬가지로 결과 list의 모든 원소들 역시 비워진다....!!!!
     */
    private static void pick(int len, LinkedList<Integer> pickUps, int pick) {
        if (pick == 0) {
            List<Integer> combination = new ArrayList<>(pickUps);
            System.out.println(combination);
            result.add(combination);
            return;
        }

        int lastComb = pickUps.isEmpty() ? 0 : pickUps.peekLast() + 1;
        for (int i = lastComb; i < len; i++) {
            pickUps.offer(i);
            pick(len, pickUps, pick - 1);
            pickUps.pollLast();
        }
    }
}
