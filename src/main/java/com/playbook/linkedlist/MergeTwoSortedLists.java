package com.playbook.linkedlist;

/** Merge two sorted lists by splicing nodes (no new nodes). 1→3→5 + 2→4 = 1→2→3→4→5. */
public final class MergeTwoSortedLists {
    private MergeTwoSortedLists() {}

    /** Iterative with a dummy head: always attach the smaller front node. O(n+m), O(1). */
    public static ListNode iterative(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;      // dummy removes the "is result empty?" special case
        while (a != null && b != null) {
            if (a.val <= b.val) {                            // <= keeps the merge stable
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a != null ? a : b;                       // append whatever is left in one go
        return dummy.next;
    }

    /** Recursive: the smaller head wins, its next is the merge of the rest. O(n+m) time and stack. */
    public static ListNode recursive(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;
        if (a.val <= b.val) {
            a.next = recursive(a.next, b);
            return a;
        }
        b.next = recursive(a, b.next);
        return b;
    }
}
