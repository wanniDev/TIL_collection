# Graph2_Disjoint Set: Union by rank

앞서 정리했던 Quick Union 방식의 Disjoint Set은 union의 속도를 개선한 대신 find의 속도를 포기한 경우입니다. find의 시간 복잡도는 H를 루트 노드로부터 조회 대산의 노드까지의 길이를 H라 정의했을때, 대략  O(H)의 시간 복잡도가 발생합니다. 즉, 찾고자 하는 정점의 값이 5이고 루트 노드가 0인데, 1~4까지의 정점의 루트 노드가 0이라면, 해당 그래프는 아래와 같은 모양을 형성할 겁니다. 

![image](https://github.com/wanniDev/TIL_collection/assets/81374655/784a46b2-d5c2-48b6-be06-a6b55ecc46f6)

이러한 상태에서 정점 5를 조회하려고 한다면, 연결리스트로 순회하면서 맨 끝단의 노드를 조회하는거랑 큰 차이가 없습니다.

이러한 상황을 개선하고자 나온 것이 바로 Union by Rank Disjoint Set 입니다.

Union by Rank Disjoint Set은 그래프를 union하는 과정에서 루트노드의 값이 겹치는 정점마다 rank를 부여해서 더 적은 rank 즉, find를 수행했을 때 루트 노드로 도달할 때까지의 순회 횟수가 더 적은 방향으로 그래프를 이어주는 방식입니다. 소스코드로 표현하면 아래와 같습니다.

```java
// UnionFind.class
class UnionFind {
    private int[] root;
    private int[] rank;

    public UnionFind(int size) {
        root = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
            rank[i] = 1; 
        }
    }

    public int find(int x) {
        while (x != root[x]) {
            x = root[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                root[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                root[rootX] = rootY;
            } else {
                root[rootY] = rootX;
                rank[rootX] += 1;
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}

// App.java
// Test Case
public class App {
    public static void main(String[] args) throws Exception {
        UnionFind uf = new UnionFind(10);
        // 1-2-5-6-7 3-8-9 4
        uf.union(1, 2);
        uf.union(2, 5);
        uf.union(5, 6);
        uf.union(6, 7);
        uf.union(3, 8);
        uf.union(8, 9);
        System.out.println(uf.connected(1, 5)); // true
        System.out.println(uf.connected(5, 7)); // true
        System.out.println(uf.connected(4, 9)); // false
        // 1-2-5-6-7 3-8-9-4
        uf.union(9, 4);
        System.out.println(uf.connected(4, 9)); // true
    }
}
```

