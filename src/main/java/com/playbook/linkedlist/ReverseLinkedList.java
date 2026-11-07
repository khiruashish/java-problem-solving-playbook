package com.playbook.linkedlist;

import java.util.ArrayDeque;
import java.util.Deque;

/** Reverse a singly linked list and return the new head. 1→2→3 becomes 3→2→1. */
public final class ReverseLinkedList {
    private ReverseLinkedList() {}

    /** Brute force: push nodes on a stack, pop to relink. O(n) time, O(n) space. */
    public static ListNode withStack(ListNode head) {
        if (head == null) return null;
        Deque<ListNode> stack = new ArrayDeque<>();
        for (ListNode n = head; n != null; n = n.next) stack.push(n);
        ListNode newHead = stack.pop(), tail = newHead;
        while (!stack.isEmpty()) tail = tail.next = stack.pop();
        tail.next = null;                                    // old head must now point nowhere
        return newHead;
    }

    /** Optimal: three pointers (prev, curr, next). Flip one arrow per step. O(n) time, O(1) space. */
    public static ListNode iterative(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next;                       // 1. save the rest
            curr.next = prev;                                // 2. flip the arrow
            prev = curr;                                     // 3. advance prev
            curr = next;                                     // 4. advance curr
        }
        return prev;
    }

    /** Recursive: reverse the rest, then hook the old next back to me. O(n) time, O(n) stack. */
    public static ListNode recursive(ListNode head) {
        if (head == null || head.next == null) return head; // base case: 0 or 1 node
        ListNode newHead = recursive(head.next);
        head.next.next = head;                               // the node after me now points back to me
        head.next = null;
        return newHead;
    }

    /** Follow-up: reverse only positions left..right (1-based). One pass. */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head), before = dummy;
        for (int i = 1; i < left; i++) before = before.next;
        ListNode curr = before.next;
        for (int i = 0; i < right - left; i++) {             // move curr.next to the front of the segment
            ListNode moved = curr.next;
            curr.next = moved.next;
            moved.next = before.next;
            before.next = moved;
        }
        return dummy.next;
    }
}
