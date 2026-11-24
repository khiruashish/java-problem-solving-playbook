package com.playbook.trees;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class TreesBatch3Test {

    @Test void symmetricTree() {
        TreeNode yes = TreeNode.of(1, 2, 2, 3, 4, 4, 3);
        TreeNode no = TreeNode.of(1, 2, 2, null, 3, null, 3);
        for (TreeNode t : new TreeNode[]{yes, null, TreeNode.of(7)}) {
            assertTrue(SymmetricTree.recursive(t));
            assertTrue(SymmetricTree.iterative(t));
            assertTrue(SymmetricTree.levelPalindromes(t));
            assertTrue(SymmetricTree.viaMirrorCopy(t));
        }
        assertFalse(SymmetricTree.recursive(no));
        assertFalse(SymmetricTree.iterative(no));
        assertFalse(SymmetricTree.levelPalindromes(no));
        Random rnd = new Random(71);
        for (int t = 0; t < 1500; t++) {
            TreeNode half = randomTree(rnd, 3, 2);
            TreeNode tree = rnd.nextInt(3) == 0
                    ? randomTree(rnd, 4, 2)                                          // usually asymmetric
                    : new TreeNode(0, half, SymmetricTree.mirrorCopy(half));         // symmetric by construction
            if (rnd.nextInt(4) == 0 && tree != null && tree.left != null) tree.left.val += 1;      // break a symmetric one
            boolean want = SymmetricTree.viaMirrorCopy(tree);
            assertEquals(want, SymmetricTree.recursive(tree), String.valueOf(tree));
            assertEquals(want, SymmetricTree.iterative(tree), String.valueOf(tree));
            assertEquals(want, SymmetricTree.levelPalindromes(tree), String.valueOf(tree));
        }
    }

    @Test void bstIterator() {
        TreeNode root = TreeNode.of(7, 3, 15, null, null, 9, 20);
        assertEquals(List.of(3, 7, 9, 15, 20), BstIterator.drain(new BstIterator.Stack(root)));
        assertEquals(List.of(3, 7, 9, 15, 20), BstIterator.drain(new BstIterator.Morris(root)));
        assertEquals(java.util.Arrays.asList(7, 3, 15, null, null, 9, 20), TreeNode.toLevelOrder(root), "Morris must restore the tree");
        BstIterator.IntIterator empty = new BstIterator.Stack(null);
        assertFalse(empty.hasNext());
        assertThrows(NoSuchElementException.class, empty::next);
        Random rnd = new Random(72);
        for (int t = 0; t < 500; t++) {
            TreeSet<Integer> keys = new TreeSet<>();
            int n = rnd.nextInt(20);
            TreeNode bst = null;
            for (int i = 0; i < n; i++) {
                int k = rnd.nextInt(100);
                if (keys.add(k)) bst = insert(bst, k);
            }
            List<Integer> want = new ArrayList<>(keys);
            String shape = String.valueOf(TreeNode.toLevelOrder(bst));
            assertEquals(want, BstIterator.drain(new BstIterator.Flattened(bst)));
            assertEquals(want, BstIterator.drain(new BstIterator.Stack(bst)));
            assertEquals(want, BstIterator.drain(new BstIterator.Morris(bst)));
            assertEquals(shape, String.valueOf(TreeNode.toLevelOrder(bst)));
        }
    }

    @Test void verticalOrder() {
        TreeNode t = TreeNode.of(3, 9, 8, 4, 0, 1, 7);
        List<List<Integer>> want = List.of(List.of(4), List.of(9), List.of(3, 0, 1), List.of(8), List.of(7));
        assertEquals(want, VerticalOrder.bfs(t));
        assertEquals(want, VerticalOrder.dfsWithRowSort(t));
        // LeetCode 987: 5 and 6 share (row 2, col 0) → sorted by value
        TreeNode u = TreeNode.of(1, 2, 3, 4, 6, 5, 7);
        assertEquals(List.of(List.of(4), List.of(2), List.of(1, 5, 6), List.of(3), List.of(7)), VerticalOrder.sortedTies(u));
        assertEquals(List.of(List.of(4), List.of(2), List.of(1, 6, 5), List.of(3), List.of(7)), VerticalOrder.bfs(u));
        assertEquals(List.of(), VerticalOrder.bfs(null));
        Random rnd = new Random(73);
        for (int i = 0; i < 800; i++) {
            TreeNode r = randomTree(rnd, 5, 9);
            assertEquals(VerticalOrder.bfs(r), VerticalOrder.dfsWithRowSort(r), String.valueOf(r));
        }
    }

    private static TreeNode insert(TreeNode n, int k) {
        if (n == null) return new TreeNode(k);
        if (k < n.val) n.left = insert(n.left, k);
        else n.right = insert(n.right, k);
        return n;
    }

    private static TreeNode randomTree(Random rnd, int depth, int maxVal) {
        if (depth == 0 || rnd.nextInt(4) == 0) return null;
        return new TreeNode(rnd.nextInt(maxVal + 1), randomTree(rnd, depth - 1, maxVal), randomTree(rnd, depth - 1, maxVal));
    }
}
