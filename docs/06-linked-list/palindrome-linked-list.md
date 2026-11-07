# Palindrome linked list

<span class="badge medium">Medium</span> <span class="badge tag">Slow / fast</span> <span class="badge tag">In-place reversal</span> <span class="badge tag">Recursion</span>

## Problem
Return true if the values read the same forwards and backwards. `1→2→2→1` ✅, `1→2→3→1` ❌. Can you do it in O(1) space?

## Thinking process
<div class="steps" markdown>

1. **Brute force**: copy the values into an `ArrayList` and use two pointers. O(n) space.
2. **The problem**: a singly linked list can't walk backwards.
3. **Insight**: make the second half walk "forwards" by **reversing it**. Find the [first middle](middle-node.md),
   reverse everything after it, and compare it node by node with the first half.
4. **Senior touch**: reverse the second half **back** afterwards. A check function shouldn't mutate its input.
5. **Recursive trick**: the recursion visits nodes from the tail backwards while a shared `front` pointer moves
   forwards, so you compare the outermost pair first.

</div>

## Picture (`1→2→3→2→1`)
```
first half: 1 → 2 → 3          second half reversed: 1 → 2
            ↑ compare ↑                              ↑   ↑
```

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/PalindromeLinkedList.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Copy to list | O(n) | O(n) |
| Reverse half | O(n) | O(1) |
| Recursive | O(n) | O(n) stack |
