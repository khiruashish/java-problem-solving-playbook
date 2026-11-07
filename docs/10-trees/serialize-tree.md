# Serialize & deserialize a binary tree

<span class="badge expert">Expert</span> <span class="badge tag">Pre-order</span> <span class="badge tag">BFS</span> <span class="badge tag">Design</span>

## Problem
Design `serialize(root) → String` and `deserialize(String) → root` so that the round trip produces an identical tree.
Values can be negative and repeated.

## Thinking process
<div class="steps" markdown>

1. A single traversal **without markers** is ambiguous: pre-order `1,2` could mean 2 is the left child or the right child.
2. **Add null markers** (`#`). Now pre-order fully determines the shape:
   `1,2,#,#,3,4,#,#,5,#,#`.
3. **Decoding** mirrors encoding: read a token. If it's `#`, return null. Otherwise create the node, then recursively build the
   left subtree, **then** the right. An iterator over the tokens keeps track of the position automatically.
4. **Level-order format** (what LeetCode displays): BFS writing `#` for missing children, with the trailing `#`s trimmed.
   Decode with a queue, assigning two tokens as the children of each dequeued node.
5. Because markers encode the shape, **duplicates and negatives just work**. You never search for values.

</div>

## Example
```
      1
     / \          pre-order: 1,2,#,#,3,4,#,#,5,#,#
    2   3         level:     1,2,3,#,#,4,5
       / \
      4   5
```

## Code
```java
--8<-- "src/main/java/com/playbook/trees/SerializeTree.java"
```

## Complexity
O(n) time and O(n) space for both formats in both directions.

## Design discussion (what makes it "Expert")
- **Separator choice**: `,` is fine for ints. For strings you need escaping or length prefixes (`3:abc`).
- **Size**: about 2n tokens because of the markers. A BST can be serialized with **no markers** (pre-order plus the BST bounds
  rebuild it), a classic follow-up.
- **Recursion depth**: a skewed tree with 10⁵ nodes overflows the recursive decoder. The BFS version is iterative.

## Testing note
The tests round-trip 300 random trees (including negative values and duplicates) through both formats and compare the structure.
