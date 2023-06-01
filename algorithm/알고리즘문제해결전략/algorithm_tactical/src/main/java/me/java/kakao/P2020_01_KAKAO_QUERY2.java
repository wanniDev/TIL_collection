package me.java.kakao;


import java.util.*;

public class P2020_01_KAKAO_QUERY2 {
    static class Trie {
        Map<Integer, Integer> lenIdx = new HashMap<>();
        Trie[] next = new Trie[26];

        void insert(String str) {
            Trie node = this;
            int len = str.length();
            for (char ch : str.toCharArray()) {
                if (node.next[ch - 'a'] == null)
                    node.next[ch - 'a'] = new Trie();
                node = node.next[ch - 'a'];
                node.lenIdx.put(len, node.lenIdx.getOrDefault(len, 0) + 1);
            }
        }

        int find(String str, int i) {
            char ch = str.charAt(i);
            if (ch == '?')
                return lenIdx.getOrDefault(str.length(), 0);
            return next[ch - 'a'] == null ? 0 : next[ch - 'a'].find(str, i + 1);
        }
    }

    public int[] solution(String[] words, String[] queries) {
        Trie front = new Trie();
        Trie back = new Trie();

        for (String word : words) {
            front.insert(word);
            back.insert(reverse(word));
        }


        return Arrays.stream(queries).mapToInt(
                query -> query.charAt(0) == '?' ?
                        back.find(reverse(query), 0) :
                        front.find(query, 0)).toArray();
    }

    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static void main(String[] args) {
        int[] solution = new P2020_01_KAKAO_QUERY2().solution(
                new String[]{"frodo", "front", "frost", "frozen", "frame", "kakao"},
                new String[]{"fro??", "????o", "fr???", "fro???", "pro?"}
        );

        System.out.println(Arrays.toString(solution));
    }
}
