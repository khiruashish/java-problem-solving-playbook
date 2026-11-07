# 06 · Linked List

## Concept primer

- A node holds a value and a `next` reference. There is **no index access**: reaching node k takes O(k).
- Inserting or deleting at a node you already hold is **O(1)**. That's the reason to use a list over an array.
- Every problem is about **rewiring references safely**. Draw boxes and arrows, and **save `next` before you overwrite it**.

```java
--8<-- "src/main/java/com/playbook/linkedlist/ListNode.java"
```

## The four techniques that solve almost everything

| Technique | Why it works | Problems |
|-----------|--------------|----------|
| **Dummy head** | Removes the "is this the head?" special case | Merge, Remove Nth, Add two numbers, Reverse k-group |
| **Slow / fast pointers** | Speeds 1 and 2 find the middle and detect loops; a gap of n finds the n-th from the end | Middle, Cycle, Remove Nth, Palindrome |
| **In-place reversal** | Three pointers flip one arrow per step | Reverse, Palindrome, Reverse k-group, Add numbers II |
| **Hash map of nodes** | Maps by *identity* (originals → copies, visited set) | Cycle (brute force), Intersection, Copy random, LRU |

!!! warning "Identity, not value"
    Two nodes with the same `val` are different nodes. Compare nodes with `==`, and use them as
    `HashSet`/`HashMap` keys only because `ListNode` doesn't override `equals`.

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Reverse a linked list](reverse-linked-list.md) | <span class="badge easy">Easy</span> | prev / curr / next |
| 2 | [Middle of the list](middle-node.md) | <span class="badge easy">Easy</span> | Slow / fast |
| 3 | [Merge two sorted lists](merge-two-sorted-lists.md) | <span class="badge easy">Easy</span> | Dummy head |
| 4 | [Detect a cycle & find its start](linked-list-cycle.md) | <span class="badge medium">Medium</span> | Floyd |
| 5 | [Remove Nth node from end](remove-nth-from-end.md) | <span class="badge medium">Medium</span> | Gap of n |
| 6 | [Palindrome linked list](palindrome-linked-list.md) | <span class="badge medium">Medium</span> | Reverse second half |
| 7 | [Intersection of two lists](intersection-of-two-lists.md) | <span class="badge medium">Medium</span> | Switch heads |
| 8 | [Add two numbers](add-two-numbers.md) | <span class="badge hard">Hard</span> | Carry |
| 9 | [Reverse nodes in k-group](reverse-k-group.md) | <span class="badge hard">Hard</span> | Reverse + stitch |
| 10 | [Copy list with random pointer](copy-random-list.md) | <span class="badge hard">Hard</span> | Interleave copies |
| 11 | [LRU cache](lru-cache.md) | <span class="badge expert">Expert</span> | HashMap + doubly linked list |
| 12 | [Merge K sorted lists](merge-k-sorted-lists.md) | <span class="badge expert">Expert</span> | Min-heap / divide & conquer |
