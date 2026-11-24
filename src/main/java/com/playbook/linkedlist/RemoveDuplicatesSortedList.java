package com.playbook.linkedlist;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Sorted linked list.
 * I: keep one copy of each value (1→1→2→3→3 ⇒ 1→2→3).
 * II: drop EVERY value that repeats (1→2→3→3→4→4→5 ⇒ 1→2→5).
 */
public final class RemoveDuplicatesSortedList {
    private RemoveDuplicatesSortedList() {}

    /** I, iterative: duplicates are adjacent, so unlink next while it equals current. O(n), O(1). */
    public static ListNode keepOne(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.next.val == cur.val) cur.next = cur.next.next;   // skip the duplicate, stay on cur
            else cur = cur.next;
        }
        return head;
    }

    /** I, recursive: fix the tail first, then decide whether head duplicates the new tail's head. O(n) stack. */
    public static ListNode keepOneRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        head.next = keepOneRecursive(head.next);
        return head.val == head.next.val ? head.next : head;
    }

    /** I, with a set (works even when the list is NOT sorted). O(n) time, O(n) space. Builds a new list. */
    public static ListNode keepOneWithSet(ListNode head) {
        Set<Integer> seen = new LinkedHashSet<>();
        for (ListNode n = head; n != null; n = n.next) seen.add(n.val);
        return ListNode.of(seen.stream().mapToInt(Integer::intValue).toArray());
    }

    /**
     * II: a dummy node lets us delete the head itself. prev is the last node known to be unique; when a run of equal
     * values starts after prev, skip the whole run. O(n), O(1).
     */
    public static ListNode dropAllRepeated(ListNode head) {
        ListNode dummy = new ListNode(0, head), prev = dummy;
        while (prev.next != null) {
            ListNode cur = prev.next;
            if (cur.next != null && cur.next.val == cur.val) {
                int v = cur.val;
                while (cur != null && cur.val == v) cur = cur.next;   // skip the entire run of v
                prev.next = cur;                                     // prev stays: cur may start another run
            } else {
                prev = cur;                                          // cur is unique: keep it
            }
        }
        return dummy.next;
    }
}
