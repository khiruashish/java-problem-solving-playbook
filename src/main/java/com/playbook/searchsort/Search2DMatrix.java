package com.playbook.searchsort;

/**
 * Two classic matrix searches.
 * I  (fully sorted): each row sorted, and each row's first value > the previous row's last → one sorted array.
 * II (row & column sorted): rows sorted left→right, columns sorted top→bottom (rows may overlap).
 */
public final class Search2DMatrix {
    private Search2DMatrix() {}

    /** Brute force for both kinds: scan every cell. O(m·n). */
    public static boolean scan(int[][] m, int target) {
        for (int[] row : m) for (int x : row) if (x == target) return true;
        return false;
    }

    /**
     * I: treat the m×n matrix as a virtual sorted array of length m·n; index k is cell (k / n, k % n).
     * One binary search: O(log(m·n)).
     */
    public static boolean fullySorted(int[][] m, int target) {
        if (m.length == 0 || m[0].length == 0) return false;
        int rows = m.length, cols = m[0].length;
        int lo = 0, hi = rows * cols - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int v = m[mid / cols][mid % cols];          // virtual index → (row, col)
            if (v == target) return true;
            if (v < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return false;
    }

    /**
     * II: staircase search from the TOP-RIGHT corner. Everything left of it in the row is smaller, everything below it
     * in the column is larger — so each comparison discards a whole row or a whole column. O(m + n).
     */
    public static boolean staircase(int[][] m, int target) {
        if (m.length == 0 || m[0].length == 0) return false;
        int r = 0, c = m[0].length - 1;
        while (r < m.length && c >= 0) {
            int v = m[r][c];
            if (v == target) return true;
            if (v > target) c--;                         // whole column c is ≥ v > target: drop it
            else r++;                                    // whole row r (left part) is ≤ v < target: drop it
        }
        return false;
    }

    /** II alternative: binary search each row. O(m log n) — better than staircase only when m is tiny. */
    public static boolean binarySearchEachRow(int[][] m, int target) {
        for (int[] row : m) {
            if (row.length == 0 || row[0] > target || row[row.length - 1] < target) continue;   // row can't contain it
            if (java.util.Arrays.binarySearch(row, target) >= 0) return true;
        }
        return false;
    }
}
