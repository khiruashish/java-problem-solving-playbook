package com.playbook.linkedlist;

/** Middle node; for even length return the SECOND middle (1→2→3→4 gives 3). */
public final class MiddleNode {
    private MiddleNode() {}

    /** Two passes: count, then walk n/2. O(n). */
    public static ListNode twoPass(ListNode head) {
        int n = 0;
        for (ListNode x = head; x != null; x = x.next) n++;
        ListNode x = head;
        for (int i = 0; i < n / 2; i++) x = x.next;
        return x;
    }

    /** Optimal: slow moves 1, fast moves 2; when fast falls off, slow is in the middle. One pass, O(1). */
    public static ListNode slowFast(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /** Variant: FIRST middle for even length (needed when splitting a list for merge sort). */
    public static ListNode firstMiddle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
