package me.java.algorithm.recurrsion;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RobotGrid {
    public static final Set<Point> cache = new HashSet<>(); // 이동이 가능하지만, 목표지점이 아니었던 지점 캐싱
    private Set<Point> path;
    private boolean[][] maze;

    public boolean solution(int m, int n) {
        this.path = new LinkedHashSet<>();

        this.maze  = new boolean[6][6];
        maze[2][0] = true;
        maze[3][0] = true;
        maze[3][1] = true;
        maze[3][2] = true;
        maze[3][3] = true;

        return computePath(m, n, maze, path);
    }

    public Set<Point> getPath() {
        return path;
    }

    public boolean[][] getMaze() {
        return maze;
    }

    private boolean computePath(int m, int n, boolean[][] maze, Set<Point> path) {
        // 격자 바깥을 넘어갈 경우
        if (m < 0 || n < 0)
            return false;

        // 장애물이 있어 이동이 불가능한 경우
        if (maze[m][n])
            return false;

        Point point = new Point(m, n);

        // 목표지점에 도달했거나, 이동 가능한 경로인 경우
        if ((m == 0) && (n == 0) ||
                computePath(m, n - 1, maze, path) ||
                computePath(m - 1, n, maze, path)) {
            path.add(point);
            return true;
        }

        cache.add(point);

        return false;
    }

    public static void main(String[] args) {
        RobotGrid grid = new RobotGrid();
        boolean solution = grid.solution(5, 5);
        System.out.println(solution);
    }
}
