package com.playbook.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Smallest range [a, b] that contains at least one number from each of k sorted lists.
 * "Smaller" = smaller b − a; ties → smaller a. Returns {a, b}.
 */
public final class SmallestRange {
    private SmallestRange() {}

    /**
     * Brute force: every candidate lower bound a (each value), then for each list the smallest value ≥ a; b is the max
     * of those. O(N · k · log m) with binary search, N = total values.
     */
    public static int[] bruteForce(List<List<Integer>> lists) {
        int[] best = null;
        for (List<Integer> l : lists)
            for (int a : l) {
                int b = Integer.MIN_VALUE;
                boolean ok = true;
                for (List<Integer> other : lists) {
                    int idx = lowerBound(other, a);
                    if (idx == other.size()) { ok = false; break; }   // nothing ≥ a in this list
                    b = Math.max(b, other.get(idx));
                }
                if (ok && better(a, b, best)) best = new int[]{a, b};
            }
        return best;
    }

    /**
     * Optimal: a min-heap holding ONE pointer per list (k-way merge). The heap min and the tracked max form a valid
     * range. To possibly shrink it we must raise the minimum: advance the list that owns it. Stop when that list runs
     * out — no range without it is valid. O(N log k).
     */
    public static int[] minHeap(List<List<Integer>> lists) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e[0]));   // {value, list, index}
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).isEmpty()) throw new IllegalArgumentException("list " + i + " is empty");
            int v = lists.get(i).get(0);
            pq.offer(new int[]{v, i, 0});
            max = Math.max(max, v);
        }
        int[] best = null;
        while (true) {
            int[] min = pq.poll();
            if (better(min[0], max, best)) best = new int[]{min[0], max};
            List<Integer> owner = lists.get(min[1]);
            int next = min[2] + 1;
            if (next == owner.size()) return best;        // this list is exhausted: can't raise the min any further
            int v = owner.get(next);
            pq.offer(new int[]{v, min[1], next});
            max = Math.max(max, v);                      // the max only ever grows
        }
    }

    /**
     * Alternative: merge everything into (value, listId) pairs sorted by value, then the classic "minimum window
     * containing all k colours" sliding window. O(N log N).
     */
    public static int[] slidingWindow(List<List<Integer>> lists) {
        List<int[]> all = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) for (int v : lists.get(i)) all.add(new int[]{v, i});
        all.sort(Comparator.comparingInt(e -> e[0]));
        int k = lists.size(), covered = 0;
        int[] inWindow = new int[k];
        int[] best = null;
        for (int r = 0, l = 0; r < all.size(); r++) {
            if (inWindow[all.get(r)[1]]++ == 0) covered++;
            while (covered == k) {                       // shrink from the left while still covering every list
                if (better(all.get(l)[0], all.get(r)[0], best)) best = new int[]{all.get(l)[0], all.get(r)[0]};
                if (--inWindow[all.get(l)[1]] == 0) covered--;
                l++;
            }
        }
        return best;
    }

    private static boolean better(int a, int b, int[] best) {
        if (best == null) return true;
        long w = (long) b - a, bw = (long) best[1] - best[0];
        return w < bw || (w == bw && a < best[0]);
    }

    private static int lowerBound(List<Integer> l, int x) {
        int lo = 0, hi = l.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (l.get(mid) < x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
