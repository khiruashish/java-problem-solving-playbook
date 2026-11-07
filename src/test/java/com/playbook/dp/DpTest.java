package com.playbook.dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DpTest {
    private final Random rnd = new Random(1313);

    private String randomString(int maxLen, String alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = rnd.nextInt(maxLen + 1); i > 0; i--) sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        return sb.toString();
    }

    @Test void climbingStairs() {
        long[] expected = {1, 1, 2, 3, 5, 8, 13, 21};
        for (int n = 0; n < expected.length; n++) {
            assertEquals(expected[n], ClimbingStairs.recursive(n));
            assertEquals(expected[n], ClimbingStairs.memo(n));
            assertEquals(expected[n], ClimbingStairs.table(n));
            assertEquals(expected[n], ClimbingStairs.optimal(n));
            assertEquals(expected[n], ClimbingStairs.withSteps(n, new int[]{1, 2}));
        }
        assertEquals(ClimbingStairs.memo(80), ClimbingStairs.optimal(80));
        assertEquals(3, ClimbingStairs.withSteps(4, new int[]{1, 3}));   // 1111, 13, 31
    }

    @Test void houseRobber() {
        assertEquals(12, HouseRobber.optimal(new int[]{2, 7, 9, 3, 1}));
        assertEquals(4, HouseRobber.optimal(new int[]{1, 2, 3, 1}));
        assertEquals(3, HouseRobber.circular(new int[]{2, 3, 2}));
        assertEquals(0, HouseRobber.optimal(new int[0]));
        for (int t = 0; t < 300; t++) {
            int[] a = rnd.ints(rnd.nextInt(15), 0, 50).toArray();
            int expected = HouseRobber.recursive(a);
            assertEquals(expected, HouseRobber.memo(a));
            assertEquals(expected, HouseRobber.optimal(a));
        }
    }

    @Test void coinChange() {
        assertEquals(3, CoinChange.minCoins(new int[]{1, 2, 5}, 11));
        assertEquals(-1, CoinChange.minCoins(new int[]{2}, 3));
        assertEquals(0, CoinChange.minCoins(new int[]{1}, 0));
        assertEquals(4, CoinChange.combinations(new int[]{1, 2, 5}, 5));
        assertEquals(9, CoinChange.permutations(new int[]{1, 2, 5}, 5));
        for (int t = 0; t < 200; t++) {
            int[] coins = rnd.ints(1 + rnd.nextInt(3), 1, 9).distinct().toArray();
            int amount = rnd.nextInt(25);
            int expected = CoinChange.minCoinsRecursive(coins, amount);
            assertEquals(expected, CoinChange.minCoinsMemo(coins, amount));
            assertEquals(expected, CoinChange.minCoins(coins, amount));
        }
    }

    @Test void lis() {
        assertEquals(4, LongestIncreasingSubsequence.patience(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        assertEquals(1, LongestIncreasingSubsequence.patience(new int[]{7, 7, 7, 7}));
        assertEquals(0, LongestIncreasingSubsequence.patience(new int[0]));
        for (int t = 0; t < 300; t++) {
            int[] a = rnd.ints(rnd.nextInt(14), 0, 10).toArray();
            int expected = LongestIncreasingSubsequence.recursive(a);
            assertEquals(expected, LongestIncreasingSubsequence.quadratic(a));
            assertEquals(expected, LongestIncreasingSubsequence.patience(a));
        }
    }

    @Test void uniquePaths() {
        assertEquals(28, UniquePaths.table(3, 7));
        assertEquals(2, UniquePaths.withObstacles(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}));
        assertEquals(0, UniquePaths.withObstacles(new int[][]{{1}}));
        for (int m = 1; m <= 9; m++)
            for (int n = 1; n <= 9; n++) {
                long expected = UniquePaths.recursive(m, n);
                assertEquals(expected, UniquePaths.memo(m, n));
                assertEquals(expected, UniquePaths.table(m, n));
                assertEquals(expected, UniquePaths.rolling(m, n));
                assertEquals(expected, UniquePaths.combinatorics(m, n));
            }
        assertEquals(UniquePaths.table(30, 30), UniquePaths.combinatorics(30, 30));
    }

    @Test void knapsack() {
        assertEquals(220, Knapsack01.optimal(new int[]{10, 20, 30}, new int[]{60, 100, 120}, 50));
        assertEquals(300, Knapsack01.unbounded(new int[]{10, 20, 30}, new int[]{60, 100, 120}, 50));
        for (int t = 0; t < 300; t++) {
            int n = rnd.nextInt(10);
            int[] wt = rnd.ints(n, 1, 12).toArray(), val = rnd.ints(n, 1, 30).toArray();
            int cap = rnd.nextInt(30);
            int expected = Knapsack01.recursive(wt, val, cap);
            assertEquals(expected, Knapsack01.memo(wt, val, cap));
            assertEquals(expected, Knapsack01.table(wt, val, cap));
            assertEquals(expected, Knapsack01.optimal(wt, val, cap));
            assertTrue(Knapsack01.unbounded(wt, val, cap) >= expected);
        }
    }

    @Test void lcs() {
        assertEquals(3, LongestCommonSubsequence.length("abcde", "ace"));
        assertEquals("ace", LongestCommonSubsequence.sequence("abcde", "ace"));
        assertEquals(0, LongestCommonSubsequence.length("abc", "def"));
        for (int t = 0; t < 300; t++) {
            String a = randomString(9, "abc"), b = randomString(9, "abc");
            int expected = LongestCommonSubsequence.recursive(a, b);
            assertEquals(expected, LongestCommonSubsequence.memo(a, b));
            assertEquals(expected, LongestCommonSubsequence.length(a, b));
            assertEquals(expected, LongestCommonSubsequence.twoRows(a, b));
            String seq = LongestCommonSubsequence.sequence(a, b);
            assertEquals(expected, seq.length());
            assertTrue(isSubsequence(seq, a) && isSubsequence(seq, b));
        }
    }

    private static boolean isSubsequence(String s, String t) {
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) if (s.charAt(i) == t.charAt(j)) i++;
        return i == s.length();
    }

    @Test void editDistance() {
        assertEquals(3, EditDistance.table("horse", "ros"));
        assertEquals(5, EditDistance.oneRow("intention", "execution"));
        assertEquals(3, EditDistance.table("", "abc"));
        for (int t = 0; t < 300; t++) {
            String a = randomString(6, "abc"), b = randomString(6, "abc");
            int expected = EditDistance.recursive(a, b);
            assertEquals(expected, EditDistance.table(a, b));
            assertEquals(expected, EditDistance.oneRow(a, b));
        }
    }

    @Test void partition() {
        assertTrue(PartitionEqualSubset.dp(new int[]{1, 5, 11, 5}));
        assertFalse(PartitionEqualSubset.dp(new int[]{1, 2, 3, 5}));
        for (int t = 0; t < 300; t++) {
            int[] a = rnd.ints(1 + rnd.nextInt(12), 1, 20).toArray();
            boolean expected = PartitionEqualSubset.recursive(a);
            assertEquals(expected, PartitionEqualSubset.dp(a));
            assertEquals(expected, PartitionEqualSubset.bitset(a));
        }
    }

    @Test void wordBreak() {
        assertTrue(WordBreak.dp("leetcode", List.of("leet", "code")));
        assertTrue(WordBreak.dp("applepenapple", List.of("apple", "pen")));
        assertFalse(WordBreak.dp("catsandog", List.of("cats", "dog", "sand", "and", "cat")));
        String bad = "a".repeat(40) + "b";                        // exponential for plain recursion, instant with memo
        assertFalse(WordBreak.memo(bad, List.of("a", "aa", "aaa", "aaaa")));
        assertFalse(WordBreak.dp(bad, List.of("a", "aa", "aaa", "aaaa")));
        for (int t = 0; t < 300; t++) {
            List<String> dict = new ArrayList<>();
            for (int i = 0; i < 1 + rnd.nextInt(4); i++) {
                String w = randomString(3, "ab");
                if (!w.isEmpty()) dict.add(w);
            }
            String s = randomString(10, "ab");
            boolean expected = WordBreak.recursive(s, dict);
            assertEquals(expected, WordBreak.memo(s, dict));
            assertEquals(expected, WordBreak.dp(s, dict));
        }
    }

    @Test void matrixChain() {
        assertEquals(4500, MatrixChainMultiplication.table(new int[]{10, 30, 5, 60}));
        assertEquals(26000, MatrixChainMultiplication.table(new int[]{40, 20, 30, 10, 30}));
        assertEquals(0, MatrixChainMultiplication.table(new int[]{10, 20}));
        for (int t = 0; t < 200; t++) {
            int[] dims = rnd.ints(2 + rnd.nextInt(7), 1, 30).toArray();
            long expected = MatrixChainMultiplication.recursive(dims);
            assertEquals(expected, MatrixChainMultiplication.memo(dims));
            assertEquals(expected, MatrixChainMultiplication.table(dims));
        }
    }

    @Test void burstBalloons() {
        assertEquals(167, BurstBalloons.intervalDp(new int[]{3, 1, 5, 8}));
        assertEquals(10, BurstBalloons.intervalDp(new int[]{1, 5}));
        assertEquals(0, BurstBalloons.intervalDp(new int[0]));
        for (int t = 0; t < 200; t++) {
            int[] a = rnd.ints(rnd.nextInt(7), 0, 10).toArray();
            assertEquals(BurstBalloons.bruteForce(a), BurstBalloons.intervalDp(a));
        }
    }
}
