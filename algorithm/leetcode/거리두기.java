import java.util.*;

class Solution {
    int[][] dirs = new int[][] {
        {1,0},
        {0,1},
        {-1,0},
        {0,-1}
    };
    private static final int ROW = 5;
    private static final int COL = 5;
    public int[] solution(String[][] places) {
        int len = places.length;
        int[] answer = new int[len];
        for (int i = 0; i < len; i++) {
            String[] place = places[i];
            boolean isDistant = true;
            
            for (int row = 0; row < len; row++) {
                for (int col = 0; col < len; col++) {
                    if (isDistant && place[row].charAt(col) == 'P')
                        isDistant = checkDistant(row, col, place);
                }
            }
            answer[i] = isDistant ? 1 : 0;
        }
        return answer;
    }
    
    private boolean checkDistant(int row, int col, String[] place) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row, col});
        
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            for (int[] dir : dirs) {
                int nr = point[0] + dir[0];
                int nc = point[1] + dir[1];
                if (nr < 0 || nc < 0 || nr >= ROW || nc >= COL)
                    continue;
                if (nr == row && nc == col)
                    continue;
                int distant = Math.abs(nr - row) + Math.abs(nc - col);
                if (distant <= 2 && place[nr].charAt(nc) == 'P')
                    return false;
                if (distant < 2 && place[nr].charAt(nc) == 'O')
                    queue.add(new int[]{nr,nc});
            }
        }
        return true;
    }
}
