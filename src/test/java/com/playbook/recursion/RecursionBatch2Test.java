package com.playbook.recursion;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecursionBatch2Test {
    private final Random rnd = new Random(404);

    @Test void parentheses() {
        assertEquals(List.of("((()))", "(()())", "(())()", "()(())", "()()()"), GenerateParentheses.backtracking(3));
        long[] catalan = {1, 1, 2, 5, 14, 42, 132, 429, 1430};
        for (int n = 0; n <= 8; n++) {
            List<String> b = GenerateParentheses.backtracking(n);
            assertEquals(catalan[n], b.size());
            if (n <= 6) assertEquals(GenerateParentheses.bruteForce(n), b);
        }
    }

    @Test void letters() {
        assertEquals(List.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"), LetterCombinations.recursive("23"));
        assertEquals(List.of(), LetterCombinations.recursive(""));
        for (String d : new String[]{"2", "79", "234", "7979"})
            assertEquals(LetterCombinations.recursive(d), LetterCombinations.iterative(d));
        assertEquals(256, LetterCombinations.recursive("7979").size());
    }

    @Test void combinationSum() {
        assertEquals(List.of(List.of(2, 2, 3), List.of(7)), CombinationSum.reusable(new int[]{2, 3, 6, 7}, 7));
        assertEquals(List.of(List.of(1, 1, 6), List.of(1, 2, 5), List.of(1, 7), List.of(2, 6)),
                CombinationSum.onceEach(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        for (int t = 0; t < 200; t++) {                          // onceEach vs brute force over all subsets
            int[] c = rnd.ints(1 + rnd.nextInt(9), 1, 8).toArray();
            int target = 1 + rnd.nextInt(15);
            Set<List<Integer>> expected = new HashSet<>();
            for (int mask = 0; mask < (1 << c.length); mask++) {
                List<Integer> pick = new ArrayList<>();
                int sum = 0;
                for (int j = 0; j < c.length; j++) if ((mask >> j & 1) == 1) {
                    pick.add(c[j]);
                    sum += c[j];
                }
                if (sum == target) {
                    pick.sort(null);
                    expected.add(pick);
                }
            }
            List<List<Integer>> got = CombinationSum.onceEach(c, target);
            assertEquals(expected, new HashSet<>(got));
            assertEquals(expected.size(), got.size(), "no duplicates");
            for (List<Integer> combo : CombinationSum.reusable(Arrays.stream(c).distinct().toArray(), target))
                assertEquals(target, combo.stream().mapToInt(Integer::intValue).sum());
        }
    }

    @Test void permutationsII() {
        assertEquals(List.of(List.of(1, 1, 2), List.of(1, 2, 1), List.of(2, 1, 1)), PermutationsII.pruned(new int[]{1, 1, 2}));
        assertEquals(1, PermutationsII.pruned(new int[]{5, 5, 5, 5, 5, 5}).size());
        for (int t = 0; t < 200; t++) {
            int[] a = rnd.ints(rnd.nextInt(7), 0, 3).toArray();
            assertEquals(PermutationsII.withSet(a), PermutationsII.pruned(a));
        }
    }

    @Test void wordSearch() {
        char[][] b = {"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        assertTrue(WordSearch.exists(b, "ABCCED"));
        assertTrue(WordSearch.exists(b, "SEE"));
        assertFalse(WordSearch.exists(b, "ABCB"));                // would reuse B
        assertEquals("ABCE", new String(b[0]), "grid restored");
        for (int t = 0; t < 200; t++) {                          // agree with the trie-based Word Search II
            char[][] g = new char[1 + rnd.nextInt(4)][1 + rnd.nextInt(4)];
            for (char[] row : g) for (int j = 0; j < row.length; j++) row[j] = "ab".charAt(rnd.nextInt(2));
            StringBuilder w = new StringBuilder();
            for (int i = 1 + rnd.nextInt(6); i > 0; i--) w.append("ab".charAt(rnd.nextInt(2)));
            boolean viaTrie = !com.playbook.advanced.WordSearchII.trie(copy(g), new String[]{w.toString()}).isEmpty();
            assertEquals(viaTrie, WordSearch.exists(g, w.toString()));
        }
    }

    private static char[][] copy(char[][] g) {
        char[][] c = new char[g.length][];
        for (int i = 0; i < g.length; i++) c[i] = g[i].clone();
        return c;
    }

    @Test void sudoku() {
        String[] puzzle = {
                "53..7....", "6..195...", ".98....6.",
                "8...6...3", "4..8.3..1", "7...2...6",
                ".6....28.", "...419..5", "....8..79"};
        char[][] board = Arrays.stream(puzzle).map(String::toCharArray).toArray(char[][]::new);
        assertTrue(SudokuSolver.solve(board));
        assertTrue(SudokuSolver.isValidSolution(board));
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (puzzle[r].charAt(c) != '.') assertEquals(puzzle[r].charAt(c), board[r][c], "givens kept");
        String[] hard = {                                        // "Arto Inkala" puzzle, famously hard for humans
                "8........", "..36.....", ".7..9.2..",
                ".5...7...", "....457..", "...1...3.",
                "..1....68", "..85...1.", ".9....4.."};
        char[][] h = Arrays.stream(hard).map(String::toCharArray).toArray(char[][]::new);
        assertTrue(SudokuSolver.solve(h));
        assertTrue(SudokuSolver.isValidSolution(h));
        char[][] bad = Arrays.stream(puzzle).map(String::toCharArray).toArray(char[][]::new);
        bad[0][2] = '5';                                         // duplicate 5 in row 0
        assertFalse(SudokuSolver.solve(bad));
    }
}
