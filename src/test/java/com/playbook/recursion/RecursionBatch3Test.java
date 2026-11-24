package com.playbook.recursion;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RecursionBatch3Test {

    @Test void combinations() {
        List<List<Integer>> want = List.of(List.of(1, 2), List.of(1, 3), List.of(1, 4),
                List.of(2, 3), List.of(2, 4), List.of(3, 4));
        assertEquals(want, Combinations.backtrack(4, 2));
        for (int n = 0; n <= 9; n++)
            for (int k = 0; k <= n + 1; k++) {
                List<List<Integer>> ref = Combinations.bitmask(n, k);
                assertEquals(ref, Combinations.backtrack(n, k), n + "C" + k);
                assertEquals(ref, Combinations.includeExclude(n, k), n + "C" + k);
                assertEquals(ref, Combinations.iterative(n, k), n + "C" + k);
                assertEquals(ref.size(), Combinations.count(n, k));
            }
        assertEquals(List.of(List.of()), Combinations.backtrack(3, 0));
        assertEquals(184_756L, Combinations.count(20, 10));
        assertEquals(Combinations.count(60, 30), 118264581564861424L);
    }

    @Test void ratInMaze() {
        int[][] maze = {
                {1, 0, 0, 0},
                {1, 1, 0, 1},
                {1, 1, 0, 0},
                {0, 1, 1, 1}};
        assertEquals(List.of("DDRDRR", "DRDDRR"), RatInMaze.allPaths(maze));
        assertEquals(2, RatInMaze.countPaths(maze));
        assertTrue(RatInMaze.pathExists(maze));
        assertEquals(List.of(), RatInMaze.allPaths(new int[][]{{1, 0}, {0, 1}}));
        assertEquals(List.of(""), RatInMaze.allPaths(new int[][]{{1}}));
        assertEquals(List.of(), RatInMaze.allPaths(new int[][]{{0}}));
        // all 12 simple paths in an open 3×3 grid
        assertEquals(12, RatInMaze.countPaths(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}));
        Random rnd = new Random(21);
        for (int t = 0; t < 400; t++) {
            int n = 1 + rnd.nextInt(4);
            int[][] m = new int[n][n];
            for (int[] row : m) for (int c = 0; c < n; c++) row[c] = rnd.nextInt(10) < 7 ? 1 : 0;
            List<String> paths = RatInMaze.allPaths(m);
            assertEquals(paths.size(), RatInMaze.countPaths(m));
            assertEquals(!paths.isEmpty(), RatInMaze.pathExists(m));
            List<String> sorted = new ArrayList<>(paths);
            sorted.sort(Comparator.naturalOrder());
            assertEquals(sorted, paths, "DFS order must already be lexicographic");
            for (String p : paths) assertTrue(isValidSimplePath(m, p), p);
        }
    }

    private static boolean isValidSimplePath(int[][] m, String p) {
        int n = m.length, r = 0, c = 0;
        boolean[][] seen = new boolean[n][n];
        seen[0][0] = true;
        for (char ch : p.toCharArray()) {
            switch (ch) { case 'D' -> r++; case 'U' -> r--; case 'L' -> c--; default -> c++; }
            if (r < 0 || c < 0 || r >= n || c >= n || m[r][c] == 0 || seen[r][c]) return false;
            seen[r][c] = true;
        }
        return r == n - 1 && c == n - 1;
    }

    @Test void palindromePartitioning() {
        assertEquals(List.of(List.of("a", "a", "b"), List.of("aa", "b")), PalindromePartitioning.backtrack("aab"));
        assertEquals(List.of(List.of("a")), PalindromePartitioning.backtrackWithTable("a"));
        assertEquals(1, PalindromePartitioning.minCuts("aab"));
        assertEquals(0, PalindromePartitioning.minCuts("racecar"));
        assertEquals(3, PalindromePartitioning.minCuts("abcd"));
        Random rnd = new Random(22);
        for (int t = 0; t < 400; t++) {
            int n = rnd.nextInt(10);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) sb.append((char) ('a' + rnd.nextInt(2)));
            String s = sb.toString();
            List<List<String>> ref = sortParts(PalindromePartitioning.bruteForce(s));
            assertEquals(ref, sortParts(PalindromePartitioning.backtrack(s)), s);
            assertEquals(ref, sortParts(PalindromePartitioning.backtrackWithTable(s)), s);
            int fewest = ref.stream().mapToInt(List::size).min().orElse(1) - 1;
            assertEquals(Math.max(0, fewest), PalindromePartitioning.minCuts(s), s);
        }
    }

    private static List<List<String>> sortParts(List<List<String>> parts) {
        List<List<String>> copy = new ArrayList<>(parts);
        copy.sort(Comparator.comparing(Object::toString));
        return copy;
    }
}
