package me.java.leetcode;

public class BullsAndCows {
    private String getHints(String secret, String guess) {
        int[] dic = new int[10];
        int bulls = 0, cows = 0;
        for (int i = 0; i < secret.length(); i++) {
            char sc = secret.charAt(i);
            char gc = guess.charAt(i);

            if (sc == gc) {
                bulls++;
            } else {
                dic[sc - '0']++;
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            char sc = secret.charAt(i);
            char gc = guess.charAt(i);

            if (sc != gc) {
                if (dic[gc - '0'] > 0) {
                    cows++;
                    dic[gc - '0']--;
                }
            }
        }
        return bulls + "A" + cows + "B";
    }

    public static void main(String[] args) {
        BullsAndCows bullsAndCows = new BullsAndCows();
        System.out.println(bullsAndCows.getHints("1807", "7810"));
        System.out.println(bullsAndCows.getHints("1123", "0111"));
        System.out.println(bullsAndCows.getHints("11", "10"));
    }
}
