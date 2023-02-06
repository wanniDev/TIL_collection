package me.java.algorithm.ch2_divide_and_conquer;

import java.text.StringCharacterIterator;

public class P01_QuadTree {
/*
입력
w
xbwwb
xbwxwbbwb
xxwwwbxwxwbbbwwxxxwwbbbwwwwbb

xxwwwbxwx
 */
    private static int[][] decompressed = new int[999][999];
    // 현재 단어 스캔
    // 그 단어가 b || w 일 경우,
    public void decompress(StringCharacterIterator it, int row, int col, int size) {
        char head = it.current();
        it.next();

        if (head == 'b' || head == 'w') {
            for (int dr = 0; dr < size; dr++) {
                for (int dc = 0; dc < size; dc++) {
                    decompressed[row+dr][col+dc] = (head == 'b' ? 1 : 0);
                }
            }
        } else {
            int half = size / 2;
            decompress(it, row, col, half);
            decompress(it, row+half, col, half);
            decompress(it, row, col+half, half);
            decompress(it, row+half, col+half, half);
        }
    }
}
