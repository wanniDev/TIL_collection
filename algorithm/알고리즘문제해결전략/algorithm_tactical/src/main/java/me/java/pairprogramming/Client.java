package me.java.pairprogramming;

import java.util.*;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Map<Integer, String> dic = new HashMap<>();
        System.out.println("몇 명 인가요?");
        int len = sc.nextInt();
        System.out.printf("%d명 확인했습니다.\n", len);
        for (int i = 0; i < len; i++) {
            System.out.printf("%d번째 이름을 써주세요.\n", i + 1);
            String name = sc.next();
            dic.put(i, name);
        }

        int result = len;
        int[] picks = getFrom(len);
        Set<Integer> idx = new HashSet<>();
        List<String> list = new ArrayList<>();
        while (result > 0) {
            int pick = Picker.instance().pickFrom(picks);
            if (!idx.contains(pick)) {
                String name = dic.get(pick);
                list.add(name);
                idx.add(pick);
                result--;
            }
        }
        System.out.println("축하합니다. 다음 순서대로 퇴근하세요.");
        list.forEach(System.out::println);
    }

    private static int[] getFrom(int len) {
        int[] ret = new int[len];
        for (int i = 0; i < len; i++) {
            ret[i] = i;
        }
        return ret;
    }
}
