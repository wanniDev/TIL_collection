package me.java.algorithm.ch1_bruteforce.recursion;

public class P03_Gameboard {
    private final int[][][] coverType = {
            {{0,0}, {1, 0}, {0, 1}}, //「
            {{0, 0}, {0, 1}, {1, 1}}, //ㄱ
            {{0, 0}, {1, 0}, {1, 1}}, //ㄴ
            {{0, 0}, {1, 0}, {1, -1}} //」
    };

    private boolean set(int[][] board, int row, int col, int type, int delta) {
        // 'result' 로 따로 변수를 따로 지정해야 함수를 멈추지 않고 모든 칸에 블록에 적용할 수 있는지 확인하도록 반복문을 순회할 수 있다.
        // 성공여부와 상관없이 일단 무조건 블록을 놔야한다.
        // 그리고 놨던 블록을 다시 회수해야 그 다음 경우의 블록을 채워볼 수 있다.
        boolean result = true;
        for (int i = 0; i < 3; i++) {
            int nr = row + coverType[type][i][0];
            int nc = col + coverType[type][i][1];
            if (nr < 0 || nr >= board.length || nc < 0 || nc >= board[0].length) {
                result = false;
            } else if ((board[nr][nc] += delta) > 1) {
                result = false;
            }
        }
        return result;
    }

    /**
     * @param board 주어진 게임 보드판
     * @return 보드판을 채울 수 있는 경우의 수
     */
    private int solution(int[][] board) {
        // board를 순회하면서, 빈 공간(0)을 찾으면 해당 좌표로 초기화한다.
        int row = -1;
        int col = -1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1)
                break;
        }

        // 블록이 다 채워진 경우, 1을 리턴해서 완성된 경우의 수 하나를 더한다.
        if (row == -1)
            return 1;

        // 보드에 채울 블록의 유형만큼 블록을 채웠다가 놓아준다.
        int ret = 0;
        for (int type = 0; type < coverType.length; type++) {
            if (set(board, row, col, type, 1))
                ret += solution(board);
            set(board, row, col, type, -1);
        }
        return ret;
    }

    public static void main(String[] args) {
//        int[][] board = new int[][] {
//                {1,1,1,1,1,1,1,1,1,1},
//                {1,0,0,0,0,0,0,0,0,1},
//                {1,0,0,0,0,0,0,0,0,1},
//                {1,0,0,0,0,0,0,0,0,1},
//                {1,0,0,0,0,0,0,0,0,1},
//                {1,0,0,0,0,0,0,0,0,1},
//                {1,0,0,0,0,0,0,0,0,1},
//                {1,1,1,1,1,1,1,1,1,1}
//        };

        int[][] board = new int[][] {
                {1,0,0,0,0,0,1},
                {1,0,0,0,0,0,1},
                {1,1,0,0,1,1,1}
        };
        int solution = new P03_Gameboard().solution(board);
        System.out.println(solution);
    }
}
