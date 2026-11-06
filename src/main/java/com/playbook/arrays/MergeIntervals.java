package com.playbook.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/** Merge overlapping intervals. [[1,3],[2,6],[8,10],[15,18]] → [[1,6],[8,10],[15,18]]. Touching ([1,4],[4,5]) merges. */
public final class MergeIntervals {
    private MergeIntervals() {}

    /** Optimal: sort by start, then extend or append. O(n log n) time, O(n) output. */
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][];
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, Comparator.comparingInt(iv -> iv[0]));
        List<int[]> out = new ArrayList<>();
        int[] current = sorted[0].clone();
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] <= current[1]) {                // overlaps → extend
                current[1] = Math.max(current[1], sorted[i][1]);
            } else {                                          // gap → close current
                out.add(current);
                current = sorted[i].clone();
            }
        }
        out.add(current);
        return out.toArray(new int[0][]);
    }

    /** Follow-up: insert a new interval into a sorted, non-overlapping list. O(n). */
    public static int[][] insert(int[][] intervals, int[] add) {
        List<int[]> out = new ArrayList<>();
        int i = 0, n = intervals.length;
        int[] merged = add.clone();
        while (i < n && intervals[i][1] < add[0]) out.add(intervals[i++]);          // fully left
        while (i < n && intervals[i][0] <= merged[1]) {                              // overlapping
            merged[0] = Math.min(merged[0], intervals[i][0]);
            merged[1] = Math.max(merged[1], intervals[i][1]);
            i++;
        }
        out.add(merged);
        while (i < n) out.add(intervals[i++]);                                       // fully right
        return out.toArray(new int[0][]);
    }
}
