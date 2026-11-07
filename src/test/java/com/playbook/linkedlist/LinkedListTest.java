package com.playbook.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.playbook.linkedlist.ListNode.of;
import static com.playbook.linkedlist.ListNode.toList;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @Test void reverse() {
        List<Function<ListNode, ListNode>> impls =
                List.of(ReverseLinkedList::withStack, ReverseLinkedList::iterative, ReverseLinkedList::recursive);
        for (Function<ListNode, ListNode> f : impls) {
            assertEquals(List.of(5, 4, 3, 2, 1), toList(f.apply(of(1, 2, 3, 4, 5))));
            assertEquals(List.of(1), toList(f.apply(of(1))));
            assertNull(f.apply(null));
        }
        assertEquals(List.of(1, 4, 3, 2, 5), toList(ReverseLinkedList.reverseBetween(of(1, 2, 3, 4, 5), 2, 4)));
        assertEquals(List.of(3, 2, 1), toList(ReverseLinkedList.reverseBetween(of(1, 2, 3), 1, 3)));
    }

    @Test void middle() {
        assertEquals(3, MiddleNode.slowFast(of(1, 2, 3, 4, 5)).val);
        assertEquals(4, MiddleNode.slowFast(of(1, 2, 3, 4, 5, 6)).val);
        assertEquals(4, MiddleNode.twoPass(of(1, 2, 3, 4, 5, 6)).val);
        assertEquals(3, MiddleNode.firstMiddle(of(1, 2, 3, 4, 5, 6)).val);
        assertEquals(1, MiddleNode.slowFast(of(1)).val);
        assertNull(MiddleNode.slowFast(null));
    }

    @Test void mergeTwo() {
        assertEquals(List.of(1, 1, 2, 3, 4, 4), toList(MergeTwoSortedLists.iterative(of(1, 2, 4), of(1, 3, 4))));
        assertEquals(List.of(1, 1, 2, 3, 4, 4), toList(MergeTwoSortedLists.recursive(of(1, 2, 4), of(1, 3, 4))));
        assertEquals(List.of(0), toList(MergeTwoSortedLists.iterative(null, of(0))));
        assertNull(MergeTwoSortedLists.recursive(null, null));
    }

    /** Builds 1→2→3→4→5 with the tail pointing back to the node at index pos (-1 = no cycle). */
    private static ListNode withCycle(int pos, ListNode[] entry) {
        ListNode head = of(1, 2, 3, 4, 5), tail = head, target = null;
        for (int i = 0; ; i++, tail = tail.next) {
            if (i == pos) target = tail;
            if (tail.next == null) break;
        }
        tail.next = target;
        entry[0] = target;
        return head;
    }

    @Test void cycle() {
        for (int pos = -1; pos < 5; pos++) {
            ListNode[] entry = new ListNode[1];
            ListNode head = withCycle(pos, entry);
            assertEquals(pos >= 0, LinkedListCycle.hasCycle(head), "pos=" + pos);
            assertEquals(pos >= 0, LinkedListCycle.hasCycleWithSet(head), "pos=" + pos);
            assertSame(entry[0], LinkedListCycle.cycleStart(head), "pos=" + pos);
            assertEquals(pos >= 0 ? 5 - pos : 0, LinkedListCycle.cycleLength(head));
        }
        assertFalse(LinkedListCycle.hasCycle(null));
    }

    @Test void removeNth() {
        for (int n = 1; n <= 5; n++) {
            List<Integer> expected = new java.util.ArrayList<>(List.of(1, 2, 3, 4, 5));
            expected.remove(5 - n);
            assertEquals(expected, toList(RemoveNthFromEnd.twoPass(of(1, 2, 3, 4, 5), n)));
            assertEquals(expected, toList(RemoveNthFromEnd.onePass(of(1, 2, 3, 4, 5), n)));
            assertEquals(expected, toList(RemoveNthFromEnd.recursive(of(1, 2, 3, 4, 5), n)));
        }
        assertNull(RemoveNthFromEnd.onePass(of(1), 1));
    }

    @Test void palindrome() {
        for (int[] yes : new int[][]{{}, {1}, {1, 2, 1}, {1, 2, 2, 1}}) {
            ListNode h = of(yes);
            assertTrue(PalindromeLinkedList.copyToList(h));
            assertTrue(PalindromeLinkedList.reverseHalf(h));
            assertTrue(PalindromeLinkedList.recursive(h));
        }
        for (int[] no : new int[][]{{1, 2}, {1, 2, 3}, {1, 2, 3, 1}}) {
            ListNode h = of(no);
            assertFalse(PalindromeLinkedList.copyToList(h));
            assertFalse(PalindromeLinkedList.reverseHalf(h));
            assertFalse(PalindromeLinkedList.recursive(h));
            assertArrayEquals(no, toList(h).stream().mapToInt(Integer::intValue).toArray(), "input restored");
        }
    }

    @Test void intersection() {
        ListNode common = of(8, 4, 5);
        ListNode a = new ListNode(4, new ListNode(1, common));
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(1, common)));
        assertSame(common, IntersectionOfTwoLists.withSet(a, b));
        assertSame(common, IntersectionOfTwoLists.lengthDiff(a, b));
        assertSame(common, IntersectionOfTwoLists.switchHeads(a, b));
        assertNull(IntersectionOfTwoLists.switchHeads(of(1, 2), of(1, 2)));     // equal values, different nodes
        assertNull(IntersectionOfTwoLists.lengthDiff(of(1), null));
    }

    @Test void addTwoNumbers() {
        assertEquals(List.of(7, 0, 8), toList(AddTwoNumbers.iterative(of(2, 4, 3), of(5, 6, 4))));
        assertEquals(List.of(7, 0, 8), toList(AddTwoNumbers.recursive(of(2, 4, 3), of(5, 6, 4))));
        assertEquals(List.of(8, 9, 9, 9, 0, 0, 0, 1),
                toList(AddTwoNumbers.iterative(of(9, 9, 9, 9, 9, 9, 9), of(9, 9, 9, 9))));
        assertEquals(List.of(0), toList(AddTwoNumbers.recursive(of(0), of(0))));
        assertEquals(List.of(7, 8, 0, 7), toList(AddTwoNumbers.forwardOrder(of(7, 2, 4, 3), of(5, 6, 4))));
    }

    @Test void reverseKGroup() {
        int[][] expected = {{1, 2, 3, 4, 5}, {2, 1, 4, 3, 5}, {3, 2, 1, 4, 5}, {4, 3, 2, 1, 5}, {5, 4, 3, 2, 1}};
        for (int k = 1; k <= 5; k++) {
            List<Integer> exp = java.util.Arrays.stream(expected[k - 1]).boxed().toList();
            assertEquals(exp, toList(ReverseKGroup.recursive(of(1, 2, 3, 4, 5), k)), "k=" + k);
            assertEquals(exp, toList(ReverseKGroup.iterative(of(1, 2, 3, 4, 5), k)), "k=" + k);
        }
        assertEquals(List.of(2, 1, 4, 3), toList(ReverseKGroup.iterative(of(1, 2, 3, 4), 2)));
    }

    @Test void copyRandom() {
        for (Function<CopyRandomList.Node, CopyRandomList.Node> f :
                List.<Function<CopyRandomList.Node, CopyRandomList.Node>>of(CopyRandomList::withMap, CopyRandomList::interleave)) {
            CopyRandomList.Node[] n = new CopyRandomList.Node[5];
            for (int i = 0; i < 5; i++) n[i] = new CopyRandomList.Node(i * 10);
            for (int i = 0; i < 4; i++) n[i].next = n[i + 1];
            int[] randomIdx = {-1, 0, 4, 2, 0};
            for (int i = 0; i < 5; i++) n[i].random = randomIdx[i] < 0 ? null : n[randomIdx[i]];

            CopyRandomList.Node c = f.apply(n[0]);
            CopyRandomList.Node[] copies = new CopyRandomList.Node[5];
            for (int i = 0; i < 5; i++, c = c.next) {
                copies[i] = c;
                assertNotSame(n[i], c);
                assertEquals(i * 10, c.val);
            }
            assertNull(c);
            for (int i = 0; i < 5; i++) {
                assertSame(randomIdx[i] < 0 ? null : copies[randomIdx[i]], copies[i].random);
                assertSame(i < 4 ? n[i + 1] : null, n[i].next, "original restored");
            }
            assertNull(f.apply(null));
        }
    }

    @Test void lru() {
        LruCache c = new LruCache(2);
        c.put(1, 1);
        c.put(2, 2);
        assertEquals(1, c.get(1));      // 1 is now most recent
        c.put(3, 3);                    // evicts 2
        assertEquals(-1, c.get(2));
        c.put(4, 4);                    // evicts 1
        assertEquals(-1, c.get(1));
        assertEquals(3, c.get(3));
        assertEquals(4, c.get(4));
        c.put(3, 30);                   // update keeps size
        assertEquals(30, c.get(3));
        assertEquals(2, c.size());
        assertThrows(IllegalArgumentException.class, () -> new LruCache(0));

        Map<Integer, Integer> m = LruCache.withLinkedHashMap(2);
        m.put(1, 1);
        m.put(2, 2);
        m.get(1);
        m.put(3, 3);
        assertEquals(java.util.Set.of(1, 3), m.keySet());
    }

    @Test void mergeK() {
        for (Function<ListNode[], ListNode> f : List.<Function<ListNode[], ListNode>>of(
                MergeKSortedLists::collectAndSort, MergeKSortedLists::sequential,
                MergeKSortedLists::heap, MergeKSortedLists::divideAndConquer)) {
            assertEquals(List.of(1, 1, 2, 3, 4, 4, 5, 6),
                    toList(f.apply(new ListNode[]{of(1, 4, 5), of(1, 3, 4), of(2, 6)})));
            assertEquals(List.of(), toList(f.apply(new ListNode[0])));
            assertEquals(List.of(), toList(f.apply(new ListNode[]{null})));
            assertEquals(List.of(1, 2), toList(f.apply(new ListNode[]{null, of(2), of(1)})));
        }
    }
}
