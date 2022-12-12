# LRU with Spring

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



