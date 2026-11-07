package com.playbook.linkedlist;

import java.util.HashSet;
import java.util.Set;

/** Does the list loop back on itself? If so, where does the loop start, and how long is it? */
public final class LinkedListCycle {
    private LinkedListCycle() {}

    /** HashSet of visited nodes (identity, not value). O(n) time, O(n) space. */
    public static boolean hasCycleWithSet(ListNode head) {
        Set<ListNode> seen = new HashSet<>();                // ListNode has no equals(): identity semantics
        for (ListNode n = head; n != null; n = n.next) {
            if (!seen.add(n)) return true;
        }
        return false;
    }

    /** Floyd: fast gains one step per round on slow; inside a loop it must land on slow. O(n), O(1). */
    public static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    /**
     * Start of the cycle. After slow and fast meet, a pointer from head and a pointer from the meeting
     * point, both moving 1 step, meet exactly at the cycle start (distance head→start = meet→start mod loop).
     */
    public static ListNode cycleStart(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode p = head;
                while (p != slow) {
                    p = p.next;
                    slow = slow.next;
                }
                return p;
            }
        }
        return null;
    }

    /** Length of the cycle (0 if none): from the meeting point walk once around. */
    public static int cycleLength(ListNode head) {
        ListNode start = cycleStart(head);
        if (start == null) return 0;
        int len = 1;
        for (ListNode n = start.next; n != start; n = n.next) len++;
        return len;
    }
}
