# LRU in Spring

## LRU 캐시의 의미

## LRU 매커니즘

## Example code

 lru 캐시 알고리즘은 보통, cpu의 cache hit ratio를 높이기 위해 사용되지만, 애플리케이션 코드에서도 많이 활용된다. 네트워크 통신 자치를 줄이거나, 데이터베이스와의 통신을 최대한 줄여서 얻는 성능 향상을 기대해볼 수 있기 때문이다. (정확히는 응답시간을 줄인다.)

```java
package cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class ConcurrentLRUCache<K, V> {
    private final int limit;

    private final Function<K, V> generator;

    private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();

    private final ConcurrentLinkedDeque<K> queue = new ConcurrentLinkedDeque<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private volatile int size;

    public ConcurrentLRUCache(int limit, Function<K, V> generator) {
        if (limit < 0)
            throw new IllegalArgumentException("Cache limit must not be negative");
        if (generator == null)
            throw new IllegalArgumentException("Generator mut not be null");
        this.limit = limit;
        this.generator = generator;
    }

    public V get(K key) {
        if (this.limit == 0)
            return this.generator.apply(key);

        V cached = cache.get(key);
        if (cached != null) {
            if (this.size < this.limit)
                return cached;

            this.lock.readLock().lock();
            try {
                if (this.queue.removeLastOccurrence(key))
                    this.queue.offer(key);
                return cached;
            } finally {
                this.lock.readLock().unlock();
            }
        }

        this.lock.writeLock().lock();
        try {
            cached = cache.get(key);

            if (cached != null) {
                if (this.queue.removeLastOccurrence(key))
                    this.queue.offer(key);
                return cached;
            }

            V value = generator.apply(key);
            if (this.size == this.limit) {
                // 한번 이라도 cache hit을 한번도 안할경우, 처음 queue에 들어간 head 노드 포인터에 머무를 것이다.
                K leastUsed = this.queue.poll();
                if (leastUsed != null)
                    this.cache.remove(leastUsed);
            }
            this.queue.offer(key);
            this.cache.put(key, value);
            this.size = this.cache.size();
            return value;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public boolean remove(K key) {
        this.lock.writeLock().lock();
        try {
            boolean wasPresent = (cache.remove(key) != null);
            this.queue.remove(key);
            this.size = cache.size();
            return wasPresent;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.cache.clear();
            this.queue.clear();
            this.size = 0;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public int size() {
        return this.size;
    }

    public int limit() {
        return this.limit;
    }
}
```

## 코드 분석

- `V get(K key)`
- `boolean remove(K key)`
- `void clear()`