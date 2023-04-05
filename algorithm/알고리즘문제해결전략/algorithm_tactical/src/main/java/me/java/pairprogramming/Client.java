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
            System.out.printf("\"%s\"님이 뽑고 싶은 숫자 1~12 중 하나를 입력해주세요.\n", name);
            String pick = sc.next();
            while (!Character.isDigit(pick.charAt(0)) || Integer.parseInt(pick) < 1 || Integer.parseInt(pick) > 12) {
                System.out.println("뭐하세요? 똑바로 안해요? 왜 \"" + pick + "\" 으로 쓰신거죠? \"숫자\" 1~12 중 하나를 쓰라고 분.명.히 알려드렸습니다.");
                pick = sc.next();
            }
            int num = Integer.parseInt(pick);
            dic.put(num, name);
        }
        for (Map.Entry<Integer, String> entry : dic.entrySet()) {
            System.out.println(entry.getValue() + "님이 뽑으신 번호 :" + entry.getValue());
        }
        int result = 0;
        while (!dic.containsKey(result)) {
            result = Picker.newInstance().pickFrom(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            System.out.println("당첨 번호는 " + result);
            if (dic.containsKey(result)) {
                System.out.println("축하합니다. " + dic.get(result) + "님");
                break;
            }
            System.out.println("당첨자가 없네요.. 다시 추첨을 하겠습니다.");
            Thread.sleep(1000);
        }
    }
}
