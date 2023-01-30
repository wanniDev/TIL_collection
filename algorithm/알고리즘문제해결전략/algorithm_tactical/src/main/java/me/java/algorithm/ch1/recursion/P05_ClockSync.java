package me.java.algorithm.ch1.recursion;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P05_ClockSync {
    private int[][] linked = {
        //   0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15
            {3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0
            {0, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0}, // 1
            {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 3}, // 2
            {3, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0}, // 3
            {0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 3, 0, 3, 0, 0, 0}, // 4
            {3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3}, // 5
            {0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3}, // 6
            {0, 0, 0, 0, 3, 3, 0, 3, 0, 0, 0, 0, 0, 0, 3, 3}, // 7
            {0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 8
            {0, 0, 0, 3, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0}  // 9
    };

    /*
2
12 6 6 6 6 6 12 12 12 12 12 12 12 12 12 12
12 9 3 12 6 6 9 3 12 9 12 9 12 12 6 6
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseCnt = sc.nextInt();
        sc.nextLine();
        while (testCaseCnt > 0) {
            List<Integer> clocks = Arrays
                    .stream(sc.nextLine()
                            .split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            int solve = new P05_ClockSync().solve(clocks, 0);
            System.out.printf("Answer for : %s, is %d%n", clocks, solve);
            testCaseCnt--;
        }
    }

    /**
     *
     * @param clocks    3, 6, 9, 12시 중 하나로 맞춰진 시계들을 나열한 리스트
     * @param switchNum 누르고자 하는 스위치 번호
     * @return          모든 시계가 12시로 정렬된 상태이면 0, 아니면 9999 리턴
     */
    private int solve(List<Integer> clocks, int switchNum) {
        if (switchNum == linked.length)
            return isAligned(clocks) ? 0 : 9999; // 정수 오버플로우 방지

        int result = Integer.MAX_VALUE;
        for (int cnt = 0; cnt < 4; cnt++) { // 스위치를 0번 누르는 경우부터 세 번 누르는 경우까지
            result = Math.min(result, cnt + solve(clocks, switchNum + 1));
            push(clocks, switchNum);
        }
        // 여기서 버튼들을 4번 누르기 때문에, 시계들의 시간은 원상태로 돌아온다.
        return result;
    }





    private void push(List<Integer> clocks, int switchNum) {
        if (switchNum >= linked.length) {
            throw new IllegalArgumentException("Argument switchNum: " + switchNum + "must not exceed 9.");
        }
        int[] linkedClocks = linked[switchNum];
        for (int i = 0; i < linkedClocks.length; i++) {
            if (linkedClocks[i] > 0) {
                int nextHour = clocks.get(i) + linkedClocks[i];
                clocks.set(i, nextHour == 15 ? 3 : nextHour);
            }

        }
    }

    private boolean isAligned(List<Integer> clocks) {
        for (Integer clock : clocks) {
            if (clock != 12)
                return false;
        }
        return true;
    }
}
