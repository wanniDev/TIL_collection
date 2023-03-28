class Solution {
    int[][] dirs = new int[][] {
        {1,0},
        {0,1},
        {-1,0},
        {0,-1}
    };
    
    private int rLen;
    private int cLen;
    private int answer;
    
    public int numIslands(char[][] grid) {
        this.rLen = grid.length;
        this.cLen = grid[0].length;
        this.answer = 0;
        
        for(int row = 0; row < rLen; row++) {
            for (int col = 0; col < cLen; col++) {
                if (grid[row][col] == '1')
                    bfs(row, col, grid);
            }
        }
        
        return answer;
    }
    
    private void bfs(int row, int col, char[][] grid) {
        answer++;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row, col});
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            for (int[] dir: dirs) {
                int nr = point[0] + dir[0];
                int nc = point[1] + dir[1];
                if (nr < 0 || nr >= rLen || nc < 0 || nc >= cLen)
                    continue;
                if (grid[nr][nc] == '0')
                    continue;
                grid[nr][nc] = '0';
                queue.add(new int[]{nr, nc});
            }
        }
    }
}


class Solution2 {

    int[][] dirs = {
            {1,0}, {0,1}, {-1, 0}, {0, -1}
    };
    
    public int numIslands(char[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[0].length;

        int answer = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (grid[i][j] == '1') {
                    answer++;
                    dfs(i, j, grid);
                }
            }
        }
        return answer;
    }

    private void dfs(int row, int col, char[][] grid) {
        if (!isRange(row, col, grid))
            return;
        if (grid[row][col] == '0')
            return;
        grid[row][col] = '0';
        for (int[] dir : dirs) {
            dfs(row + dir[0], col + dir[1], grid);
        }
    }

    private boolean isRange(int row, int col, char[][] grid) {
        return row < grid.length && col < grid[0].length && row >= 0 && col >= 0;
    }
}
