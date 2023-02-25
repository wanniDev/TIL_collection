package me.java.leetcode.ch01_recurrsion.ch01_01_mamoization;

import java.util.*;

public class P02_PascalTriangle2 {
    private Map<RowCol, Integer> cache = new HashMap<>();

    public List<Integer> getRow(int rowIdx) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < rowIdx + 1; i++) {
            result.add(getNum(rowIdx, i));
        }
        return result;
    }

    private int getNum(int row, int col) {
        RowCol key = new RowCol(row, col);
        if (cache.containsKey(key))
            return cache.get(key);
        int ret;
        if (row == 0 || col == 0 || row == col) {
            ret = 1;
        } else {
            ret = getNum(row - 1, col - 1) + getNum(row - 1, col);
        }

        cache.put(new RowCol(row, col), ret);
        return ret;
    }



    private static final class RowCol {
        private final int row;
        private final int col;

        public RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RowCol rowCol = (RowCol) o;
            return row == rowCol.row && col == rowCol.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}
