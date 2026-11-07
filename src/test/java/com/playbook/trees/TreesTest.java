package com.playbook.trees;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.playbook.trees.TreeNode.of;
import static org.junit.jupiter.api.Assertions.*;

class TreesTest {
    private final Random rnd = new Random(1010);

    /** Random tree with values in [lo, hi). */
    private TreeNode randomTree(int maxNodes, int lo, int hi) {
        int n = rnd.nextInt(maxNodes + 1);
        TreeNode root = null;
        for (int i = 0; i < n; i++) root = randomInsert(root, lo + rnd.nextInt(hi - lo));
        return root;
    }

    private TreeNode randomInsert(TreeNode n, int v) {
        if (n == null) return new TreeNode(v);
        if (rnd.nextBoolean()) n.left = randomInsert(n.left, v);
        else n.right = randomInsert(n.right, v);
        return n;
    }

    @Test void builderRoundTrip() {
        Integer[] lo = {3, 9, 20, null, null, 15, 7};
        assertEquals(Arrays.asList(lo), TreeNode.toLevelOrder(of(lo)));
        assertNull(of());
    }

    @Test void traversals() {
        TreeNode t = of(1, 2, 3, 4, 5, null, 6);
        assertEquals(List.of(1, 2, 4, 5, 3, 6), Traversals.preorderRecursive(t));
        assertEquals(List.of(4, 2, 5, 1, 3, 6), Traversals.inorderRecursive(t));
        assertEquals(List.of(4, 5, 2, 6, 3, 1), Traversals.postorderRecursive(t));
        for (int i = 0; i < 300; i++) {
            TreeNode r = randomTree(25, 0, 50);
            List<Integer> before = TreeNode.toLevelOrder(r);
            assertEquals(Traversals.preorderRecursive(r), Traversals.preorderIterative(r));
            assertEquals(Traversals.inorderRecursive(r), Traversals.inorderIterative(r));
            assertEquals(Traversals.inorderRecursive(r), Traversals.inorderMorris(r));
            assertEquals(before, TreeNode.toLevelOrder(r), "Morris must restore the tree");
            assertEquals(Traversals.postorderRecursive(r), Traversals.postorderIterative(r));
        }
    }

    @Test void height() {
        TreeNode t = of(3, 9, 20, null, null, 15, 7);
        assertEquals(3, TreeHeight.maxDepthRecursive(t));
        assertEquals(3, TreeHeight.maxDepthBfs(t));
        assertEquals(2, TreeHeight.minDepth(t));
        assertEquals(5, TreeHeight.minDepth(of(2, null, 3, null, 4, null, 5, null, 6)));   // the one-child pitfall
        assertTrue(TreeHeight.isBalanced(t));
        assertFalse(TreeHeight.isBalanced(of(1, 2, 2, 3, 3, null, null, 4, 4)));
        assertEquals(0, TreeHeight.maxDepthBfs(null));
        for (int i = 0; i < 200; i++) {
            TreeNode r = randomTree(25, 0, 9);
            assertEquals(TreeHeight.maxDepthRecursive(r), TreeHeight.maxDepthBfs(r));
        }
    }

    @Test void levelOrder() {
        TreeNode t = of(3, 9, 20, null, null, 15, 7);
        assertEquals(List.of(List.of(3), List.of(9, 20), List.of(15, 7)), LevelOrder.bfs(t));
        assertEquals(List.of(List.of(3), List.of(20, 9), List.of(15, 7)), LevelOrder.zigzag(t));
        for (int i = 0; i < 200; i++) {
            TreeNode r = randomTree(25, 0, 9);
            assertEquals(LevelOrder.bfs(r), LevelOrder.dfs(r));
            assertEquals(LevelOrder.zigzag(r), LevelOrder.zigzagTwoStacks(r));
        }
    }

    @Test void diameter() {
        assertEquals(3, Diameter.optimal(of(1, 2, 3, 4, 5)));
        assertEquals(0, Diameter.optimal(of(1)));
        assertEquals(0, Diameter.optimal(null));
        for (int i = 0; i < 200; i++) {
            TreeNode r = randomTree(30, 0, 9);
            assertEquals(Diameter.bruteForce(r), Diameter.optimal(r));
        }
    }

    @Test void validateBst() {
        assertTrue(ValidateBst.range(of(2, 1, 3)));
        TreeNode tricky = of(5, 1, 6, null, null, 3, 7);           // 3 is in 5's RIGHT subtree
        assertTrue(ValidateBst.naiveChildrenOnly(tricky), "the naive check is fooled");
        assertFalse(ValidateBst.range(tricky));
        assertFalse(ValidateBst.inorder(tricky));
        assertFalse(ValidateBst.range(of(1, 1)));                  // duplicates not allowed
        assertTrue(ValidateBst.range(of(Integer.MIN_VALUE, null, Integer.MAX_VALUE)));
        for (int i = 0; i < 200; i++) {
            TreeNode bst = null;
            for (int k = 0; k < 20; k++) bst = ValidateBst.insert(bst, rnd.nextInt(100));
            assertTrue(ValidateBst.range(bst));
            assertTrue(ValidateBst.inorder(bst));
            TreeNode r = randomTree(10, 0, 20);
            assertEquals(ValidateBst.inorder(r), ValidateBst.range(r));
        }
    }

    @Test void lca() {
        TreeNode t = of(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        assertEquals(3, LowestCommonAncestor.recursive(t, 5, 1).val);
        assertEquals(5, LowestCommonAncestor.recursive(t, 5, 4).val);
        assertEquals(5, LowestCommonAncestor.viaPaths(t, 5, 4).val);
        assertEquals(2, LowestCommonAncestor.viaPaths(t, 7, 4).val);
        TreeNode bst = of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        assertEquals(6, LowestCommonAncestor.bst(bst, 2, 8).val);
        assertEquals(2, LowestCommonAncestor.bst(bst, 2, 4).val);
        assertEquals(4, LowestCommonAncestor.bst(bst, 3, 5).val);
        int[] vals = {0, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int p : vals) for (int q : vals) {
            int expected = LowestCommonAncestor.viaPaths(bst, p, q).val;
            assertEquals(expected, LowestCommonAncestor.recursive(bst, p, q).val);
            assertEquals(expected, LowestCommonAncestor.bst(bst, p, q).val);
        }
    }

    @Test void views() {
        TreeNode t = of(1, 2, 3, 4, 5, 6, 7, null, 8);
        assertEquals(List.of(1, 2, 4, 8), TreeViews.left(t));
        assertEquals(List.of(1, 3, 7, 8), TreeViews.right(t));
        assertEquals(List.of(1, 3, 7, 8), TreeViews.rightRecursive(t));
        assertEquals(List.of(4, 2, 1, 3, 7), TreeViews.top(t));
        assertEquals(List.of(4, 8, 6, 3, 7), TreeViews.bottom(t));   // hd 0 has 1,5,6 → deepest-last is 6
        for (int i = 0; i < 200; i++) {
            TreeNode r = randomTree(25, 0, 9);
            assertEquals(TreeViews.right(r), TreeViews.rightRecursive(r));
        }
    }

    @Test void buildTree() {
        TreeNode t = BuildTree.fromPreIn(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        assertEquals(Arrays.asList(3, 9, 20, null, null, 15, 7), TreeNode.toLevelOrder(t));
        for (int i = 0; i < 200; i++) {
            TreeNode r = randomTree(25, 0, 1);                       // shape only; relabel with distinct values
            int[] counter = {0};
            relabel(r, counter);
            int[] pre = Traversals.preorderRecursive(r).stream().mapToInt(Integer::intValue).toArray();
            int[] in = Traversals.inorderRecursive(r).stream().mapToInt(Integer::intValue).toArray();
            int[] post = Traversals.postorderRecursive(r).stream().mapToInt(Integer::intValue).toArray();
            assertTrue(SerializeTree.sameTree(r, BuildTree.fromPreIn(pre, in)));
            assertTrue(SerializeTree.sameTree(r, BuildTree.fromPostIn(post, in)));
        }
    }

    private void relabel(TreeNode n, int[] c) {
        if (n == null) return;
        n.val = c[0]++;
        relabel(n.left, c);
        relabel(n.right, c);
    }

    @Test void serialize() {
        TreeNode t = of(1, 2, 3, null, null, 4, 5);
        assertEquals("1,2,#,#,3,4,#,#,5,#,#", SerializeTree.serializePreorder(t));
        assertEquals("1,2,3,#,#,4,5", SerializeTree.serializeLevelOrder(t));
        assertEquals("", SerializeTree.serializeLevelOrder(null));
        assertNull(SerializeTree.deserializeLevelOrder(""));
        assertNull(SerializeTree.deserializePreorder("#"));
        for (int i = 0; i < 300; i++) {
            TreeNode r = randomTree(30, -50, 50);
            assertTrue(SerializeTree.sameTree(r, SerializeTree.deserializePreorder(SerializeTree.serializePreorder(r))));
            assertTrue(SerializeTree.sameTree(r, SerializeTree.deserializeLevelOrder(SerializeTree.serializeLevelOrder(r))));
        }
    }

    @Test void maxPathSum() {
        assertEquals(6, MaxPathSum.optimal(of(1, 2, 3)));
        assertEquals(42, MaxPathSum.optimal(of(-10, 9, 20, null, null, 15, 7)));
        assertEquals(-3, MaxPathSum.optimal(of(-3)));
        assertEquals(-1, MaxPathSum.optimal(of(-2, -1)));
        for (int i = 0; i < 300; i++) {
            TreeNode r = randomTree(25, -20, 20);
            if (r == null) continue;
            assertEquals(MaxPathSum.bruteForce(r), MaxPathSum.optimal(r));
        }
    }
}
