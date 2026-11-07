package com.playbook.advanced;

/** Prefix tree over lowercase a–z: insert, search, startsWith, countWordsWithPrefix and delete — all O(L). */
public final class Trie {
    /** One node per character position; children indexed by c - 'a'. */
    static final class Node {
        final Node[] next = new Node[26];
        boolean isWord;                                      // a word ENDS here (not just passes through)
        int prefixCount;                                     // words in this subtree (for countWordsWithPrefix)
    }

    final Node root = new Node();

    public void insert(String word) {
        if (search(word)) return;                            // keep counts correct for duplicates
        Node n = root;
        n.prefixCount++;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (n.next[i] == null) n.next[i] = new Node();
            n = n.next[i];
            n.prefixCount++;
        }
        n.isWord = true;
    }

    /** Whole word present? Must end on a node flagged isWord ("app" is not a word just because "apple" is). */
    public boolean search(String word) {
        Node n = walk(word);
        return n != null && n.isWord;
    }

    public boolean startsWith(String prefix) {
        Node n = walk(prefix);
        return n != null && n.prefixCount > 0;
    }

    public int countWordsWithPrefix(String prefix) {
        Node n = walk(prefix);
        return n == null ? 0 : n.prefixCount;
    }

    /** Remove a word; prune nodes no word passes through any more. Returns false if it wasn't present. */
    public boolean delete(String word) {
        if (!search(word)) return false;
        Node n = root;
        n.prefixCount--;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            Node child = n.next[i];
            if (--child.prefixCount == 0) {                  // nothing else below: cut the branch here
                n.next[i] = null;
                return true;
            }
            n = child;
        }
        n.isWord = false;
        return true;
    }

    Node walk(String s) {
        Node n = root;
        for (char c : s.toCharArray()) {
            n = n.next[c - 'a'];
            if (n == null) return null;
        }
        return n;
    }

    /** Longest common prefix of all words: walk down while the node has exactly one child and isn't a word end. */
    public static String longestCommonPrefix(String[] words) {
        if (words.length == 0) return "";
        Trie t = new Trie();
        for (String w : words) {
            if (w.isEmpty()) return "";
            t.insert(w);
        }
        StringBuilder sb = new StringBuilder();
        Node n = t.root;
        while (!n.isWord) {
            int only = -1, children = 0;
            for (int i = 0; i < 26; i++) if (n.next[i] != null) {
                children++;
                only = i;
            }
            if (children != 1) break;
            sb.append((char) ('a' + only));
            n = n.next[only];
        }
        return sb.toString();
    }
}
