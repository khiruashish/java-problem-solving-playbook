package com.playbook.linkedlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/** Merge k sorted lists into one sorted list. N = total nodes. */
public final class MergeKSortedLists {
    private MergeKSortedLists() {}

    /** Brute force: collect all values, sort, rebuild. O(N log N) time, O(N) space. */
    public static ListNode collectAndSort(ListNode[] lists) {
        List<Integer> all = new ArrayList<>();
        for (ListNode l : lists) all.addAll(ListNode.toList(l));
        Collections.sort(all);
        return ListNode.of(all.stream().mapToInt(Integer::intValue).toArray());
    }

    /** Sequential: merge list 1 into 2, result into 3, … O(k·N) — the first list is re-walked k times. */
    public static ListNode sequential(ListNode[] lists) {
        ListNode result = null;
        for (ListNode l : lists) result = MergeTwoSortedLists.iterative(result, l);
        return result;
    }

    /** Min-heap of the k current heads: pop smallest, push its successor. O(N log k) time, O(k) space. */
    public static ListNode heap(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((x, y) -> Integer.compare(x.val, y.val));
        for (ListNode l : lists) if (l != null) pq.offer(l);
        ListNode dummy = new ListNode(0), tail = dummy;
        while (!pq.isEmpty()) {
            ListNode smallest = pq.poll();
            tail = tail.next = smallest;
            if (smallest.next != null) pq.offer(smallest.next);
        }
        return dummy.next;
    }

    /** Divide & conquer: merge pairs like merge sort. log k rounds, each O(N) → O(N log k), O(log k) stack. */
    public static ListNode divideAndConquer(ListNode[] lists) {
        if (lists.length == 0) return null;
        return merge(lists, 0, lists.length - 1);
    }

    private static ListNode merge(ListNode[] lists, int lo, int hi) {
        if (lo == hi) return lists[lo];
        int mid = (lo + hi) >>> 1;
        return MergeTwoSortedLists.iterative(merge(lists, lo, mid), merge(lists, mid + 1, hi));
    }
}
