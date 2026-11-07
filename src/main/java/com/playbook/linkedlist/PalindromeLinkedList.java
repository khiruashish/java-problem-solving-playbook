package com.playbook.linkedlist;

import java.util.ArrayList;
import java.util.List;

/** Is the list a palindrome? 1→2→2→1 yes, 1→2 no. */
public final class PalindromeLinkedList {
    private PalindromeLinkedList() {}

    /** Copy values into a list, then two pointers. O(n) time, O(n) space. */
    public static boolean copyToList(ListNode head) {
        List<Integer> v = ListNode.toList(head);
        for (int l = 0, r = v.size() - 1; l < r; l++, r--) if (!v.get(l).equals(v.get(r))) return false;
        return true;
    }

    /**
     * Optimal: find the middle, reverse the second half, compare, then reverse it back so the caller's
     * list is unchanged. O(n) time, O(1) space.
     */
    public static boolean reverseHalf(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode firstEnd = MiddleNode.firstMiddle(head);
        ListNode second = ReverseLinkedList.iterative(firstEnd.next);
        boolean ok = true;
        for (ListNode p = head, q = second; ok && q != null; p = p.next, q = q.next) ok = p.val == q.val;
        firstEnd.next = ReverseLinkedList.iterative(second); // restore input
        return ok;
    }

    /** Recursive: recursion unwinds from the tail while a "front" pointer walks forward. O(n) stack. */
    public static boolean recursive(ListNode head) {
        ListNode[] front = {head};
        return check(head, front);
    }

    private static boolean check(ListNode node, ListNode[] front) {
        if (node == null) return true;
        if (!check(node.next, front)) return false;          // compare deepest (tail) first
        boolean same = node.val == front[0].val;
        front[0] = front[0].next;
        return same;
    }
}
