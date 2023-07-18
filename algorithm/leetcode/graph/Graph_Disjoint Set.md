# Graph: Disjoint Set

그래프는 거대한 연결 관계를 표현하기에 아주 적합한 자료구조입니다. 그래프를 다룰 때는 각 정점의 연결관계를 확인하는 경우가 많은데, 그 연결관계를 조회하는 다양한 방법 중 하나는 `Disjoint set`을 활용하는 것입니다. 실제 적용사례를 들어보면 sns 친구관계 확인 중에 공통 관심사를 조회하는 경우가 있겠네요.

## 주요 용어

- **부모 노드 : **정점의 직접적인 부모 노드입니다.
- **루트 노드 : **부모 노드가 없는 노드로, 자신의 부모 노드로 간주될 수 있습니다. 간혹 헤드 노드라고도 부를 때도 있습니다.

## 주요 함수

- `find` : 특정 정점이 그래프에 속해있는지 조회하는 기능힙니다.
- `union` : 주어진 두 정점을 연결하는 함수입니다.

## 구현 방식

Disjoint Set을 구현하는 방법은 2가지이며 구현 방식을 선택하는 기준은 `find`를 최적화 하느냐 혹은  `union`을 최적화 하느냐에 따라 다릅니다.

- **Quick Find :** `find` 함수의 시간복잡도를 O(1)으로 고정시킬 수 있는 Disjoint Set을 구성하는 방법입니다. 대신 `union` 함수의 시간복잡도가 O(N) 이상을 소모할 수 있습니다.
- **Quick union :** `union` 함수의 시간복잡도를 조금 개선시킨 Disjoint Set 입니다. 그러나 이 경우 `find` 함수의 시간 복잡도가 증가합니다.

### 1. QuickFind

각 정점이 무조건 루트 노드를 바라보게 하여, find 함수의 시간복잡도를 O(1)으로 고정시키는 방식입니다. 대신 union이 호출할 때마다, 모든 정점을 순회하여, union 대상의 정점이 무조건 x 정점을 바라보도록 수정해야하기 때문에 `union` 함수의 시간복잡도는 호출할 때마다 O(n)이 발생하게 됩니다.

```java
// UnionFind.class
class UnionFind {
    private int[] root;

    public UnionFind(int size) {
        root = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
        }
    }

    public int find(int x) {
        return root[x];
    }
		
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            for (int i = 0; i < root.length; i++) {
                if (root[i] == rootY) {
                    root[i] = rootX;
                }
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

### 2. QuickUnion

각 정점이 union을 수행했을때 당시의 정점만을 바라보게 구성하는 Disjoint Set 입니다. FindUnion 방식과는 다르게 union을 한번 할 때마다 모든 정점을 순회하지 않습니다. 대신에 `find`를 수행할 경우 대상 노드를 여러번 참조하여 제일 상위레벨의 부모 노드를 조회해야하므로, worst case로 O(N)의 시간이 발생할 수 있습니다.

`union` 함수 역시 합치고자 하는 두 정점을  `find`를 먼저 한 다음에 연결을 하기 때문에 FindUnion처럼 모든 정점을 순회하는 경우는 드물겠지만 최악의 경우 O(N)의 시간이 소모한다고 볼 수 있겠습니다.

```java
class UnionFind {
    private int[] root;

    public UnionFind(int size) {
        root = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
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
            root[rootY] = rootX;
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

