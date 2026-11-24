package com.playbook.linkedlist;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** Reorder L0→L1→…→Ln into L0→Ln→L1→Ln-1→L2→… in place (relink nodes, don't change values). */
public final class ReorderList {
    private ReorderList() {}

    /** Brute force: copy nodes into a list, then relink with two indexes from both ends. O(n) time, O(n) space. */
    public static void withArray(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode n = head; n != null; n = n.next) nodes.add(n);
        int i = 0, j = nodes.size() - 1;
        while (i < j) {
            nodes.get(i).next = nodes.get(j);
            i++;
            if (i == j) break;
            nodes.get(j).next = nodes.get(i);
            j--;
        }
        if (!nodes.isEmpty()) nodes.get(i).next = null;      // the middle node becomes the new tail
    }

    /** Stack of the second half: pop to interleave from the back. O(n) time, O(n) space. */
    public static void withStack(ListNode head) {
        if (head == null) return;
        Deque<ListNode> stack = new ArrayDeque<>();
        int n = 0;
        for (ListNode x = head; x != null; x = x.next) { stack.push(x); n++; }
        ListNode cur = head;
        for (int k = 0; k < n / 2; k++) {                    // n/2 back nodes get inserted
            ListNode back = stack.pop(), next = cur.next;
            cur.next = back;
            back.next = next;
            cur = next;
        }
        cur.next = null;
    }

    /**
     * Optimal, O(1) space — three classic steps:
     * 1. find the middle (slow/fast), 2. reverse the second half, 3. merge the halves alternately.
     */
    public static void inPlace(ListNode head) {
        if (head == null || head.next == null) return;
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {  // slow stops at the end of the first half
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = reverse(slow.next);
        slow.next = null;                                    // cut: first half is never shorter
        ListNode first = head;
        while (second != null) {                             // weave: first, second, first, second...
            ListNode n1 = first.next, n2 = second.next;
            first.next = second;
            second.next = n1;
            first = n1;
            second = n2;
        }
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
