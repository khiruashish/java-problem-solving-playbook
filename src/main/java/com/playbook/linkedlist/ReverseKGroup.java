package com.playbook.linkedlist;

/** Reverse nodes k at a time; a final group shorter than k stays as is. 1..5, k=2 gives 2→1→4→3→5. */
public final class ReverseKGroup {
    private ReverseKGroup() {}

    /** Recursive: check k nodes exist, reverse them, attach the recursively solved rest. O(n), O(n/k) stack. */
    public static ListNode recursive(ListNode head, int k) {
        ListNode probe = head;
        for (int i = 0; i < k; i++) {
            if (probe == null) return head;                  // fewer than k left: leave untouched
            probe = probe.next;
        }
        ListNode prev = recursive(probe, k), curr = head;    // prev starts as the solved remainder
        for (int i = 0; i < k; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /** Iterative: for each full group, reverse in place and stitch between groupPrev and the next group. O(1) space. */
    public static ListNode iterative(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head), groupPrev = dummy;
        while (true) {
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) kth = kth.next;
            if (kth == null) break;                          // not a full group
            ListNode groupNext = kth.next, prev = groupNext, curr = groupPrev.next;
            while (curr != groupNext) {                      // reverse, pointing the first node at groupNext
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            ListNode oldFirst = groupPrev.next;              // becomes the group's tail
            groupPrev.next = kth;
            groupPrev = oldFirst;
        }
        return dummy.next;
    }
}
