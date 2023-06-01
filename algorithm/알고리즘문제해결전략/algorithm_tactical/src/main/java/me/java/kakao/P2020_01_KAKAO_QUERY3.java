package me.java.kakao;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P2020_01_KAKAO_QUERY3 {
    class Trie {
        Map<Integer, Integer> lenIdx = new HashMap<>();
        Trie[] next = new Trie[26];

        void insert(String str) {
            Trie node = this;
            int len = str.length();
            lenIdx.put(len, lenIdx.getOrDefault(len, 0) + 1);

            for (char ch : str.toCharArray()) {
                int idx = ch - 'a';
                if (node.next[idx] == null)
                    node.next[idx] = new Trie();

                node = node.next[idx];
                node.lenIdx.put(len, node.lenIdx.getOrDefault(len, 0) + 1);
            }
        }

        int find(String str, int i) {
            if (str.charAt(i) == '?')
                return lenIdx.getOrDefault(str.length(), 0);

            int idx = str.charAt(i) - 'a';
            return next[idx] == null ? 0 : next[idx].find(str, i + 1);
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

    String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }


    public static void main(String[] args) {
        int[] solution = new P2020_01_KAKAO_QUERY3().solution(
                new String[]{"frodo", "front", "frost", "frozen", "frame", "kakao"},
                new String[]{"fro??", "????o", "fr???", "fro???", "pro?"}
        );

        System.out.println(Arrays.toString(solution));
    }
}
