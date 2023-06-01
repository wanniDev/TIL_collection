package me.java.kakao;

import java.util.LinkedList;
import java.util.Queue;

public class P2018_01_KAKAO_FRIENDS4BLOCK {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        char[][] map = new char[m][n];
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                map[row][col] = board[row].charAt(col);
            }
        }

        while (true) {
            boolean deleted = false;
            char[][] copied = copy(map);

            for (int row = 0; row < m - 1; row++) {
                for (int col = 0; col < n - 1; col++) {
                    if (Character.isLetter(map[row][col])
                            && map[row][col] == map[row][col + 1]
                            && map[row][col] == map[row + 1][col]
                            && map[row][col] ==  map[row + 1][col + 1]
                        ) {
                        copied[row][col] = '_';
                        copied[row][col + 1] = '_';
                        copied[row + 1][col] = '_';
                        copied[row + 1][col + 1] = '_';
                        deleted = true;
                    }
                }
            }

            if (!deleted) break;
            map = copied;

            for (int col = 0; col < map[0].length; col++) {
                Queue<Integer> q = new LinkedList<>();
                for (int row = m - 1; row >= 0; row--) {
                    if (map[row][col] == '_') {
                        map[row][col] = '!';
                        q.add(row);
                        answer++;
                    } else if (!q.isEmpty()) {
                        map[q.poll()][col] = map[row][col];
                        map[row][col] = '!';
                        q.add(row);
                    }
                }
            }
        }

        return answer;
    }

    private char[][] copy(char[][] arr) {
        char[][] copied = new char[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            copied[i] = arr[i].clone();
        }

        return copied;
    }

    public static void main(String[] args) {
        P2018_01_KAKAO_FRIENDS4BLOCK sol = new P2018_01_KAKAO_FRIENDS4BLOCK();
        System.out.println(sol.solution(4, 5, new String[] {"CCBDE", "AAADE", "AAABF", "CCBBF"}));
    }
}
