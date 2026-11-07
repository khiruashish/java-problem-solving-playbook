package com.playbook.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/** addNum(x) and findMedian() over a growing stream (LeetCode 295). */
public final class MedianOfStream {

    /** Two heaps: lower half in a MAX-heap, upper half in a MIN-heap; sizes differ by at most 1 (lower may have +1). */
    private final PriorityQueue<Integer> lower = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> upper = new PriorityQueue<>();

    /** O(log n). Always route through lower then rebalance, so the ordering invariant (max(lower) ≤ min(upper)) holds. */
    public void addNum(int x) {
        lower.offer(x);
        upper.offer(lower.poll());                           // the largest of the lower half moves up
        if (upper.size() > lower.size()) lower.offer(upper.poll());   // keep lower.size() ∈ {upper.size(), upper.size()+1}
    }

    /** O(1). */
    public double findMedian() {
        if (lower.isEmpty()) throw new IllegalStateException("no numbers yet");
        if (lower.size() > upper.size()) return lower.peek();
        return (lower.peek() + (double) upper.peek()) / 2;
    }

    /** Brute force reference: keep a sorted list (binary-search insert). add O(n), median O(1). */
    public static final class SortedList {
        private final List<Integer> data = new ArrayList<>();

        public void addNum(int x) {
            int i = Collections.binarySearch(data, x);
            data.add(i < 0 ? -i - 1 : i, x);                 // insertion point; ArrayList shift makes this O(n)
        }

        public double findMedian() {
            int n = data.size();
            if (n == 0) throw new IllegalStateException("no numbers yet");
            return n % 2 == 1 ? data.get(n / 2) : (data.get(n / 2 - 1) + (double) data.get(n / 2)) / 2;
        }
    }
}
