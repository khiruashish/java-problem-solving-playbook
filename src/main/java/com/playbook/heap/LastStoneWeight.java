package com.playbook.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Smash the two heaviest stones x ≤ y each turn: equal → both vanish; otherwise a stone of weight y − x remains.
 * Return the last stone's weight, or 0 if none remain.
 */
public final class LastStoneWeight {
    private LastStoneWeight() {}

    /** Brute force: re-sort the list every turn. O(n² log n). */
    public static int sorting(int[] stones) {
        List<Integer> s = new ArrayList<>();
        for (int x : stones) s.add(x);
        while (s.size() > 1) {
            Collections.sort(s);
            int y = s.remove(s.size() - 1), x = s.remove(s.size() - 1);
            if (y != x) s.add(y - x);
        }
        return s.isEmpty() ? 0 : s.get(0);
    }

    /** Max-heap: poll the two largest, push the difference. O(n log n). */
    public static int maxHeap(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int x : stones) pq.offer(x);
        while (pq.size() > 1) {
            int y = pq.poll(), x = pq.poll();            // y ≥ x
            if (y != x) pq.offer(y - x);
        }
        return pq.isEmpty() ? 0 : pq.peek();
    }
}
