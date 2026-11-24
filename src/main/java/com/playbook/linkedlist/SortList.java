package com.playbook.linkedlist;

import java.util.ArrayList;
import java.util.List;

/** Sort a linked list in O(n log n). Linked lists favour MERGE sort: merging needs no extra array, only relinking. */
public final class SortList {
    private SortList() {}

    /** Brute force: copy values out, sort the array, write them back. O(n log n) time, O(n) space. */
    public static ListNode viaArray(ListNode head) {
        List<Integer> vals = new ArrayList<>();
        for (ListNode n = head; n != null; n = n.next) vals.add(n.val);
        vals.sort(null);
        ListNode n = head;
        for (int v : vals) { n.val = v; n = n.next; }
        return head;
    }

    /** Insertion sort by relinking. O(n²) worst case, O(1) space, stable. Good for nearly sorted lists. */
    public static ListNode insertionSort(ListNode head) {
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        while (head != null) {
            ListNode next = head.next, p = dummy;
            while (p.next != null && p.next.val <= head.val) p = p.next;   // "<=" keeps it stable
            head.next = p.next;
            p.next = head;
            head = next;
        }
        return dummy.next;
    }

    /** Top-down merge sort: split at the middle (slow/fast), sort halves recursively, merge. O(n log n), O(log n) stack. */
    public static ListNode mergeSortTopDown(ListNode head) {
        if (head == null || head.next == null) return head;      // base case: 0 or 1 node is sorted
        ListNode slow = head, fast = head.next;                   // fast starts one ahead → slow ends at left-middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = slow.next;
        slow.next = null;                                         // cut into two lists
        return merge(mergeSortTopDown(head), mergeSortTopDown(right));
    }

    /**
     * Bottom-up merge sort: merge runs of size 1, 2, 4, ... iteratively. O(n log n) time and TRUE O(1) extra space —
     * the answer to "can you do it without recursion?".
     */
    public static ListNode mergeSortBottomUp(ListNode head) {
        int n = 0;
        for (ListNode x = head; x != null; x = x.next) n++;
        ListNode dummy = new ListNode(0, head);
        for (int size = 1; size < n; size *= 2) {
            ListNode prevTail = dummy, cur = dummy.next;
            while (cur != null) {
                ListNode left = cur;
                ListNode right = split(left, size);               // left has ≤ size nodes
                cur = split(right, size);                         // right has ≤ size nodes; cur = rest
                prevTail.next = merge(left, right);
                while (prevTail.next != null) prevTail = prevTail.next;
            }
        }
        return dummy.next;
    }

    /** Cut after `size` nodes; return the head of the remainder. */
    private static ListNode split(ListNode head, int size) {
        for (int i = 1; head != null && i < size; i++) head = head.next;
        if (head == null) return null;
        ListNode rest = head.next;
        head.next = null;
        return rest;
    }

    /** Merge two sorted lists; ties take from `a` first, which keeps the sort stable. */
    private static ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), t = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) { t.next = a; a = a.next; }
            else { t.next = b; b = b.next; }
            t = t.next;
        }
        t.next = a != null ? a : b;
        return dummy.next;
    }
}
