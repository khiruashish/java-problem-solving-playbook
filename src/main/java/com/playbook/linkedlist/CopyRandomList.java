package com.playbook.linkedlist;

import java.util.HashMap;
import java.util.Map;

/** Deep-copy a list whose nodes also have a {@code random} pointer to any node (or null). */
public final class CopyRandomList {
    private CopyRandomList() {}

    /** Node with an extra random pointer. */
    public static final class Node {
        public int val;
        public Node next, random;

        public Node(int val) {
            this.val = val;
        }
    }

    /** HashMap original → copy, then wire next/random in a second pass. O(n) time, O(n) space. */
    public static Node withMap(Node head) {
        Map<Node, Node> copy = new HashMap<>();
        for (Node n = head; n != null; n = n.next) copy.put(n, new Node(n.val));
        for (Node n = head; n != null; n = n.next) {
            copy.get(n).next = copy.get(n.next);             // get(null) → null, which is exactly right
            copy.get(n).random = copy.get(n.random);
        }
        return copy.get(head);
    }

    /**
     * Interleave: A→A'→B→B'→… so every copy sits right after its original (original.next IS the map).
     * Then set randoms, then unweave. O(n) time, O(1) extra space.
     */
    public static Node interleave(Node head) {
        if (head == null) return null;
        for (Node n = head; n != null; n = n.next.next) {    // 1. insert copies
            Node c = new Node(n.val);
            c.next = n.next;
            n.next = c;
        }
        for (Node n = head; n != null; n = n.next.next) {    // 2. copy.random = original.random's copy
            if (n.random != null) n.next.random = n.random.next;
        }
        Node copyHead = head.next;
        for (Node n = head; n != null; n = n.next) {         // 3. split the two lists, restoring the original
            Node c = n.next;
            n.next = c.next;
            c.next = c.next != null ? c.next.next : null;
        }
        return copyHead;
    }
}
