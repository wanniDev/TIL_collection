package me.java.kakao;

public class P2019_00_KAKAO_BLOCK {
    private static final int BLACK = 201;

    public int solution(int[][] board) {
        int answer = 0;
        int n = board.length;

        boolean hasDeleted = true;

        while (hasDeleted) {
            hasDeleted = false;

            for (int c = 0; c < n; c++) {
                for (int r = 0; r < n; r++) {
                    if (board[r][c] == 0 || board[r][c] == BLACK) {
                        board[r][c] = BLACK;
                    } else {
                        break;
                    }
                }
            }

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (r <= n - 2 && c <= n - 3) {
                        boolean deleted = scan(board, r, c, 2, 3);

                        if (deleted) {
                            answer++;
                            hasDeleted = true;
                        }
                    }

                    if (!hasDeleted && r <= n - 3 && c <= n - 2) {
                        boolean deleted = scan(board, r, c, 3, 2);

                        if (deleted) {
                            answer++;
                            hasDeleted = true;
                        }
                    }
                }
            }
        }
        return answer;
    }

    private boolean scan(int[][] board, int row, int col, int rl, int cl) {
        int blackCnt = 0, blockCnt = 0, color = -1;
        for (int r = 0; r < rl; r++) {
            for (int c = 0; c < cl; c++) {
                if (board[row + r][col + c] == BLACK) {
                    blackCnt++;
                } else if (board[row + r][col + c] > 0 && board[row + r][col + c] <= 200) {
                    if (color == -1 || color == board[row + r][col + c]) {
                        color = board[row + r][col + c];
                        blockCnt++;
                    }
                }
            }
        }

        if (blackCnt == 2 && blockCnt == 4) {
            for (int r = 0; r < rl; r++) {
                for (int c = 0; c < cl; c++) {
                    if (board[row + r][col + c] == color) {
                        board[row + r][col + c] = 0;
                    }
                }
            }
            return true;
        }

        return false;
    }
}
