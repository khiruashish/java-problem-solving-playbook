package com.playbook.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListBatch3Test {

    @Test void removeDuplicates() {
        assertEquals(List.of(1, 2, 3), ListNode.toList(RemoveDuplicatesSortedList.keepOne(ListNode.of(1, 1, 2, 3, 3))));
        assertEquals(List.of(1, 2, 5), ListNode.toList(RemoveDuplicatesSortedList.dropAllRepeated(ListNode.of(1, 2, 3, 3, 4, 4, 5))));
        assertEquals(List.of(2, 3), ListNode.toList(RemoveDuplicatesSortedList.dropAllRepeated(ListNode.of(1, 1, 1, 2, 3))));
        assertNull(RemoveDuplicatesSortedList.dropAllRepeated(ListNode.of(1, 1)));
        assertNull(RemoveDuplicatesSortedList.keepOne(null));
        Random rnd = new Random(31);
        for (int t = 0; t < 600; t++) {
            int[] a = sortedRandom(rnd, rnd.nextInt(10), 4);
            Map<Integer, Integer> counts = new LinkedHashMap<>();
            for (int x : a) counts.merge(x, 1, Integer::sum);
            List<Integer> unique = new ArrayList<>(counts.keySet());
            List<Integer> singles = counts.entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).toList();
            assertEquals(unique, ListNode.toList(RemoveDuplicatesSortedList.keepOne(ListNode.of(a))));
            assertEquals(unique, ListNode.toList(RemoveDuplicatesSortedList.keepOneRecursive(ListNode.of(a))));
            assertEquals(unique, ListNode.toList(RemoveDuplicatesSortedList.keepOneWithSet(ListNode.of(a))));
            assertEquals(singles, ListNode.toList(RemoveDuplicatesSortedList.dropAllRepeated(ListNode.of(a))));
        }
    }

    @Test void reorderList() {
        List<Consumer<ListNode>> impls = List.of(ReorderList::inPlace, ReorderList::withArray, ReorderList::withStack);
        for (Consumer<ListNode> f : impls) {
            ListNode h = ListNode.of(1, 2, 3, 4);
            f.accept(h);
            assertEquals(List.of(1, 4, 2, 3), ListNode.toList(h));
            h = ListNode.of(1, 2, 3, 4, 5);
            f.accept(h);
            assertEquals(List.of(1, 5, 2, 4, 3), ListNode.toList(h));
            f.accept(null);                                                  // no exception
        }
        for (int n = 0; n <= 12; n++) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = i;
            List<Integer> want = new ArrayList<>();
            for (int i = 0, j = n - 1; i <= j; i++, j--) { want.add(i); if (i != j) want.add(j); }
            for (Consumer<ListNode> f : impls) {
                ListNode h = ListNode.of(a);
                Map<ListNode, Boolean> before = identities(h);
                f.accept(h);
                assertEquals(want, h == null ? List.of() : ListNode.toList(h), "n=" + n);
                assertEquals(before.keySet(), identities(h).keySet(), "must relink the same nodes");
            }
        }
    }

    @Test void sortList() {
        List<UnaryOperator<ListNode>> impls = List.of(SortList::mergeSortTopDown, SortList::mergeSortBottomUp,
                SortList::insertionSort, SortList::viaArray);
        for (UnaryOperator<ListNode> f : impls) {
            assertEquals(List.of(1, 2, 3, 4), ListNode.toList(f.apply(ListNode.of(4, 2, 1, 3))));
            assertEquals(List.of(-1, 0, 3, 4, 5), ListNode.toList(f.apply(ListNode.of(-1, 5, 3, 4, 0))));
            assertNull(f.apply(null));
        }
        Random rnd = new Random(32);
        for (int t = 0; t < 500; t++) {
            int[] a = new int[rnd.nextInt(40)];
            for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(10) - 5;
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            List<Integer> want = Arrays.stream(sorted).boxed().toList();
            for (UnaryOperator<ListNode> f : impls) assertEquals(want, ListNode.toList(f.apply(ListNode.of(a))));
        }
        // stability of the merge sorts: equal values keep their original node order
        ListNode h = ListNode.of(2, 1, 2, 1);
        ListNode firstTwo = h, secondTwo = h.next.next;
        ListNode s = SortList.mergeSortBottomUp(h);
        assertSame(firstTwo, s.next.next);
        assertSame(secondTwo, s.next.next.next);
    }

    private static int[] sortedRandom(Random rnd, int n, int range) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(range);
        Arrays.sort(a);
        return a;
    }

    private static Map<ListNode, Boolean> identities(ListNode h) {
        Map<ListNode, Boolean> m = new IdentityHashMap<>();
        for (ListNode n = h; n != null; n = n.next) m.put(n, true);
        return m;
    }
}
