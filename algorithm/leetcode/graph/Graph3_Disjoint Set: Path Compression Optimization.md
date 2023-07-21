# Graph3_Disjoint Set: Path Compression Optimization

바로 이전에 구현했던 disjoint set의 경우 루트 노드를 찾기위해선 부모 노드를 순차적으로 탐색해야 했습니다. 만약에 동일한 정점의 루트 노드를 다시 검색하면 동일한 과정을 반복해야 합니다. 이 프로세스를 최적화할 수 있는 방법으로 무엇이 있을까요?

바로 루트 노드를 찾은 후 기존에 조회했던 모든 정점의 부모 노드를 루트 노드로 업데이트하는 것입니다. 이렇게 하면, 처음에는 루트 노드를 찾기 위해 부모 노드를 순회하였지만, 이후로는 바로 루트 노드를 조회할 수 있게됩니다.

기존에 조회한 정점의 부모 노드를 루트 노드로 업데이트 하는 방법 중 가장 대표적인 방법은 '재귀'를 사용하는 것입니다. 이렇게 '재귀'를 활용하여 순회 과정을 최적화하는 것을 '경로 압축'이라고 합니다.

### Algorithm

```java
class UnionFind {
	private int[] root;
  
  public UnionFind(int size) {
    this.root = new int[size];
    for (int i = 0; i < size; i++) {
      root[i] = i;
    }
  }
  
  public int find(int x) {
    if (x == root[x]) {
      return x;
    }
    return root[x] = find(root[x]);
  }
  
  public void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX != rootY)
      root[rootY] = rootX;
  }
  
  public boolean coonected(int x, int y) {
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

### Time Complexity

|                     | Union-find Constructor | Find      | Union     | Connected |
| ------------------- | ---------------------- | --------- | --------- | --------- |
| **Time Complexity** | *O(N)*                 | *O(logN)* | *O(logN)* | *O(logN)* |

