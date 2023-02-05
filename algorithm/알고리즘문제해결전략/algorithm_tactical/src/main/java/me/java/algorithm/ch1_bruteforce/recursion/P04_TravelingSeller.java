package me.java.algorithm.ch1_bruteforce.recursion;

import java.util.LinkedList;
import java.util.List;

public class P04_TravelingSeller {
    int max = Integer.MAX_VALUE;

    int n; // 도시의 수
    double dist[][] = new double[max][max];

    /**
     * @param path              지금까지 만든 경로
     * @param visited           각 도시의 방문 여부
     * @param currentLength     지금까지 만든 경로의 길이
     * @return                  나머지 도시들을 모두 방문하는 경로들 중 가장 짧은 것의 길이를 반환한다.
     */
    public double shortestPath(LinkedList<Integer> path, List<Boolean> visited, double currentLength) {
        // base case: 모든 도시를 다 방문했을 때는 시작 도시로 돌아가고 종료한다.
        if (path.size() == n) {
            return currentLength + dist[path.get(0)][path.get(n - 1)];
        }
        double ret = Double.MAX_VALUE; // 매우 큰 값으로 초기화
        // 다음 방문할 도시를 전부 시도해 본다.
        for (int next = 0; next < n; next++) {
            if (visited.get(next))
                continue;
            int here = path.get(path.size() - 1);
            path.add(next);
            visited.set(next, true);
            // 나머지 경로를 재귀 호출을 통해 완성하고 가장 짧은 경로의 길이를 얻는다.
            double cand = shortestPath(path, visited, currentLength + dist[here][next]);

            ret = Math.min(ret, cand);
            visited.set(next, false);
            path.pollLast();
        }
        return ret;
    }
}
