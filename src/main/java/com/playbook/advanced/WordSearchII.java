package com.playbook.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Find every dictionary word that can be traced on a letter board through adjacent (4-dir) cells, each cell used at
 * most once per word (LeetCode 212). Result sorted.
 */
public final class WordSearchII {
    private WordSearchII() {}

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /** Brute force: run a separate backtracking search for each word from each cell. O(W · R·C · 4^L). */
    public static List<String> perWord(char[][] board, String[] words) {
        TreeSet<String> found = new TreeSet<>();
        for (String w : words) {
            outer:
            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[0].length; c++)
                    if (match(board, w, 0, r, c)) {
                        found.add(w);
                        break outer;
                    }
        }
        return new ArrayList<>(found);
    }

    private static boolean match(char[][] b, String w, int k, int r, int c) {
        if (k == w.length()) return true;
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length || b[r][c] != w.charAt(k)) return false;
        char saved = b[r][c];
        b[r][c] = '#';                                       // mark used on this path
        boolean ok = false;
        for (int[] d : DIRS) if (!ok) ok = match(b, w, k + 1, r + d[0], c + d[1]);
        b[r][c] = saved;                                     // backtrack
        return ok;
    }

    /**
     * Trie + one DFS per cell: walk the board and the trie TOGETHER; stop as soon as the path is not a prefix of any
     * word. All words share the work of common prefixes. Found words are un-flagged so each is reported once.
     */
    public static List<String> trie(char[][] board, String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode n = root;
            for (char ch : w.toCharArray()) n = n.next.computeIfAbsent(ch, x -> new TrieNode());
            n.word = w;
        }
        TreeSet<String> found = new TreeSet<>();
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++) dfs(board, r, c, root, found);
        return new ArrayList<>(found);
    }

    private static final class TrieNode {
        final java.util.Map<Character, TrieNode> next = new java.util.HashMap<>();
        String word;                                         // non-null where a word ends
    }

    private static void dfs(char[][] b, int r, int c, TrieNode parent, TreeSet<String> found) {
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length) return;
        char ch = b[r][c];
        TrieNode node = parent.next.get(ch);
        if (node == null) return;                            // '#' or no word continues this way → prune
        if (node.word != null) {
            found.add(node.word);
            node.word = null;                                // report once
        }
        b[r][c] = '#';
        for (int[] d : DIRS) dfs(b, r + d[0], c + d[1], node, found);
        b[r][c] = ch;
        if (node.next.isEmpty() && node.word == null) parent.next.remove(ch);   // prune exhausted branches
    }
}
