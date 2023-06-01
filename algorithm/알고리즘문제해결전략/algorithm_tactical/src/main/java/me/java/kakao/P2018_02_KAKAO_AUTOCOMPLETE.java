package me.java.kakao;

public class P2018_02_KAKAO_AUTOCOMPLETE {
    static class Trie {
        int cnt = 0;
        Trie[] next = new Trie[26];
        boolean isWord = false;
    }

    private final Trie root;
    private int result;

    public P2018_02_KAKAO_AUTOCOMPLETE() {
        this.root = new Trie();
        this.result = 0;
    }

    public int solution(String[] words) {
        for (String w : words) {
            buildTrie(root, w);
        }

        find(root, 0);

        return result;
    }

    private void find(Trie node, int depth) {
        if (node.cnt == 1) {
            result += depth;
            return;
        }

        if (node.isWord)
            result += depth;

        for (int i = 0; i < 26; i++) {
            if (node.next[i] != null)
                find(node.next[i], depth + 1);
        }
    }

    private void buildTrie(Trie node, String word) {
        for (char ch : word.toCharArray()) {
            if (node.next[ch - 'a'] == null)
                node.next[ch - 'a'] = new Trie();
            node = node.next[ch - 'a'];
            node.cnt++;
        }
        node.isWord = true;
    }

    public static void main(String[] args) {
        P2018_02_KAKAO_AUTOCOMPLETE sol = new P2018_02_KAKAO_AUTOCOMPLETE();
        P2018_02_KAKAO_AUTOCOMPLETE sol1 = new P2018_02_KAKAO_AUTOCOMPLETE();
        P2018_02_KAKAO_AUTOCOMPLETE sol2 = new P2018_02_KAKAO_AUTOCOMPLETE();

        System.out.println(sol.solution(new String[]{"go","gone","guild"}));
        System.out.println(sol1.solution(new String[]{"abc","def","ghi","jklm"}));
        System.out.println(sol2.solution(new String[]{"word","war","warrior","world"}));
    }
}
