package com.playbook.linkedlist;

import java.util.ArrayList;
import java.util.List;

/** Singly linked list node used by every problem in this package, plus test/demo helpers. */
public final class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /** Build a list from values; empty array → null. */
    public static ListNode of(int... values) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : values) tail = tail.next = new ListNode(v);
        return dummy.next;
    }

    /** Values from head to tail. Stops after 10_000 nodes so an accidental cycle can't hang a test. */
    public static List<Integer> toList(ListNode head) {
        List<Integer> out = new ArrayList<>();
        for (ListNode n = head; n != null && out.size() < 10_000; n = n.next) out.add(n.val);
        return out;
    }

    @Override
    public String toString() {
        return toList(this).toString();
    }
}
