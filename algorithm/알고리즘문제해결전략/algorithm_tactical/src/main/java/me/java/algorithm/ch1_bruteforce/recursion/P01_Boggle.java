package me.java.algorithm.ch1_bruteforce.recursion;

public class P01_Boggle {
    private static final char[][] board = {
            {'z', 'h', 'z', 'z', 'z'},
            {'z', 'e', 'z', 'z', 'z'},
            {'z', 'l', 'l', 'z', 'z'},
            {'z', 'z', 'z', 'o', 'z'},
            {'z', 'z', 'z', 'z', 'z'}
    };

    private static final int rowLen = board.length;
    private static final int colLen = board[0].length;

    private static final int[][] direction = {
            {-1,-1},
            {-1,0},
            {-1,1},
            {1,-1},
            {1, 0},
            {1,1},
            {0,-1},
            {0,1}

    };
    public static void main(String[] args) {

        String word = "hello";
        boolean answer = solution(word);

        System.out.println("word : " + word + " is present? " + answer);
    }

    private static boolean solution(String word) {
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                // 찾고자 하는 단어의 첫번째 키워드를 찾는다.
                if (hasWord(i, j, word))
                    return true;
            }
        }
        return false;
    }

    private static boolean hasWord(int row, int col, String word) {
        // 좌표가 board의 범위에 벗어나는가?
        if (!isRange(row, col))
            return false;
        // 좌표에 해당하는 문자가 답과 일치하는가?
        if (board[row][col] != word.charAt(0))
            return false;
        // 확인하고자하는 문자의 길이가 1개가 남은 시점에서 문자가 답과 일치하는가?
        if (word.length() == 1)
            return true;

        // 사방팔방으로 탐색해본 결과 찾고자 하는 문자가 포함되는가?
        for (int[] dirs : direction) {
            int nextRow = row + dirs[0];
            int nextCol = col + dirs[1];
            if (hasWord(nextRow, nextCol, word.substring(1)))
                return true;
        }
        return false;
    }

    private static boolean isRange(int row, int col) {
        return row < rowLen && col < colLen && row >= 0 && col >= 0;
    }
}
