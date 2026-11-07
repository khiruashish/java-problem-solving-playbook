package com.playbook.linkedlist;

import java.util.HashSet;
import java.util.Set;

/** Return the node where two lists merge (by identity), or null. Lists are not modified. */
public final class IntersectionOfTwoLists {
    private IntersectionOfTwoLists() {}

    /** Store every node of A, walk B until one is found. O(m+n) time, O(m) space. */
    public static ListNode withSet(ListNode a, ListNode b) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode x = a; x != null; x = x.next) seen.add(x);
        for (ListNode y = b; y != null; y = y.next) if (seen.contains(y)) return y;
        return null;
    }

    /** Align by length: skip the longer list's extra head, then walk together. O(m+n), O(1). */
    public static ListNode lengthDiff(ListNode a, ListNode b) {
        int la = length(a), lb = length(b);
        for (; la > lb; la--) a = a.next;
        for (; lb > la; lb--) b = b.next;
        while (a != b) {
            a = a.next;
            b = b.next;
        }
        return a;
    }

    /**
     * Switch heads: each pointer walks its list then the other one. Both travel m+n, so they arrive at the
     * intersection together — or at null together when there is none. O(m+n), O(1), no length counting.
     */
    public static ListNode switchHeads(ListNode a, ListNode b) {
        ListNode p = a, q = b;
        while (p != q) {
            p = p == null ? b : p.next;
            q = q == null ? a : q.next;
        }
        return p;
    }

    private static int length(ListNode n) {
        int len = 0;
        for (; n != null; n = n.next) len++;
        return len;
    }
}
