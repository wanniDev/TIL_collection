package me.java.kakao;


import java.util.*;

public class Solution {
    static class Trie {
        int cnt = 0;
        Trie[] next = new Trie[26];
    }

    public int[] solution(String[] words, String[] queries) {
        List<Integer> ret = new ArrayList<>();
        Map<Integer, Trie> map = new HashMap<>();
        Map<Integer, Trie> revMap = new HashMap<>();

        for (String w : words) {
            int wLen = w.length();
            if (!map.containsKey(wLen)) {
                map.putIfAbsent(wLen, new Trie());
                revMap.putIfAbsent(wLen, new Trie());
            }

            buildTrie(w, map.get(wLen));
            buildTrie(reverse(w), revMap.get(wLen));
        }

        for (String q : queries) {
            int qLen = q.length();

            int cnt = 0;
            if (map.containsKey(qLen)) {
                if (q.charAt(0) == '?') {
                    cnt = scan(reverse(q), revMap.get(qLen));
                } else {
                    cnt = scan(q, map.get(qLen));
                }
            }
            ret.add(cnt);
        }

        return ret.stream().mapToInt(Integer::intValue).toArray();
    }

    private static String reverse(String w) {
        return new StringBuilder(w).reverse().toString();
    }

    private int scan(String s, Trie node) {
        char[] arr = s.toCharArray();
        for (char ch : arr) {
            if (ch == '?') {
                return node.cnt;
            }

            int i = ch - 'a';
            if (node.next[i] == null) {
                return 0;
            }

            node = node.next[i];
        }
        return 0;
    }

    private void buildTrie(String s, Trie node) {
        char[] arr = s.toCharArray();
        node.cnt++;
        for (char ch : arr) {
            int i = ch - 'a';
            if (node.next[i] == null)
                node.next[i] = new Trie();
            node = node.next[i];
            node.cnt++;
        }
    }

    public static void main(String[] args) {
        int[] solution = new Solution().solution(
                new String[]{"frodo", "front", "frost", "frozen", "frame", "kakao"},
                new String[]{
                        "fro??", "????o", "fr???", "fro???", "pro?",
                        "?????"}
        );

        System.out.println(Arrays.toString(solution));
    }
}
