package com.playbook.linkedlist;

/** Remove the n-th node from the end (1-based; n is valid). 1→2→3→4→5, n=2 gives 1→2→3→5. */
public final class RemoveNthFromEnd {
    private RemoveNthFromEnd() {}

    /** Two passes: length L, then delete node number L-n (0-based) from the front. */
    public static ListNode twoPass(ListNode head, int n) {
        int len = 0;
        for (ListNode x = head; x != null; x = x.next) len++;
        ListNode dummy = new ListNode(0, head), prev = dummy;
        for (int i = 0; i < len - n; i++) prev = prev.next;
        prev.next = prev.next.next;
        return dummy.next;
    }

    /** One pass: keep a gap of n nodes between fast and slow; when fast hits the end, slow is before the target. */
    public static ListNode onePass(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head), slow = dummy, fast = dummy;
        for (int i = 0; i <= n; i++) fast = fast.next;       // gap of n+1 so slow stops BEFORE the target
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;                                   // dummy handles "remove the head" too
    }

    /** Recursive: count positions on the way back up; unlink when the count equals n. */
    public static ListNode recursive(ListNode head, int n) {
        int[] fromEnd = {0};
        return remove(head, n, fromEnd);
    }

    private static ListNode remove(ListNode node, int n, int[] fromEnd) {
        if (node == null) return null;
        node.next = remove(node.next, n, fromEnd);
        return ++fromEnd[0] == n ? node.next : node;         // skip myself if I am the n-th from the end
    }
}
