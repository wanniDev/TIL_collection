class Solution {
    private static final int EMPTY = Integer.MAX_VALUE;
    private static final int GATE = 0;
    private static final int WALL = -1;
    private int[][] dirs = new int[][]{
        {1,0},
        {0,1},
        {-1,0},
        {0,-1}
    };

    public void wallsAndGates(int[][] rooms) {
        Queue<int[]> queue = new LinkedList<>();
        for (int row = 0; row < rooms.length; row++) {
            for (int col = 0; col < rooms[0].length; col++) {
                if (rooms[row][col] == GATE) {
                    queue.add(new int[]{row, col});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            for (int[] dir : dirs) {
                int nr = point[0] + dir[0];
                int nc = point[1] + dir[1];
                if (nr < 0 || nr >= rooms.length || nc < 0 || nc >= rooms[0].length || rooms[nr][nc] != EMPTY)
                    continue;
                rooms[nr][nc] = rooms[point[0]][point[1]] + 1;
                queue.add(new int[]{nr, nc});
            } 
        }
    }
}
