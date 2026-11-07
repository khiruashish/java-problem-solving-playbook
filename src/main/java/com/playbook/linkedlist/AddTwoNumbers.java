package com.playbook.linkedlist;

/** Numbers stored as digit lists in REVERSE order (342 = 2→4→3). Return their sum in the same form. */
public final class AddTwoNumbers {
    private AddTwoNumbers() {}

    /** Iterative: grade-school addition with a carry. O(max(m,n)). Works for any length — no overflow. */
    public static ListNode iterative(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;
        int carry = 0;
        while (a != null || b != null || carry != 0) {       // carry != 0 emits the final extra digit
            int sum = carry + (a != null ? a.val : 0) + (b != null ? b.val : 0);
            tail = tail.next = new ListNode(sum % 10);
            carry = sum / 10;
            if (a != null) a = a.next;
            if (b != null) b = b.next;
        }
        return dummy.next;
    }

    /** Recursive: add this digit pair plus carry, recurse on the rest with the new carry. */
    public static ListNode recursive(ListNode a, ListNode b) {
        return add(a, b, 0);
    }

    private static ListNode add(ListNode a, ListNode b, int carry) {
        if (a == null && b == null && carry == 0) return null;
        int sum = carry + (a != null ? a.val : 0) + (b != null ? b.val : 0);
        return new ListNode(sum % 10, add(a != null ? a.next : null, b != null ? b.next : null, sum / 10));
    }

    /** Follow-up (LeetCode 445): digits in FORWARD order. Reverse both, add, reverse the result. */
    public static ListNode forwardOrder(ListNode a, ListNode b) {
        return ReverseLinkedList.iterative(iterative(ReverseLinkedList.iterative(a), ReverseLinkedList.iterative(b)));
    }
}
