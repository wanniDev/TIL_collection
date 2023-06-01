package me.java.kakao;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private static final int CACHE_MISS = 5;
    private static final int CACHE_HIT = 1;

    static class Node {
        Node next;
        Node prev;
        String key;
    }
    private int capacity;
    private final Map<String, Node> map = new HashMap<>();
    private final Node head = new Node();
    private final Node tail = new Node();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.head.next = tail;
        this.tail.prev = head;
    }

    public int getOrSet(String key) {
        if (capacity == 0) return CACHE_MISS;

        key = key.toLowerCase();

        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            addFirst(node);
            return CACHE_HIT;
        }

        if (map.size() == capacity) {
            Node node = tail.prev;
            map.remove(node.key);
            remove(node);
        }

        Node node = new Node();
        node.key = key;
        map.put(key, node);
        addFirst(node);

        return CACHE_MISS;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addFirst(Node node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }
}
