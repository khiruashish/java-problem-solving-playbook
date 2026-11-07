# Next greater element

<span class="badge medium">Medium</span> <span class="badge tag">Monotonic stack</span>

## Problem
For every element, find the first element **to its right** that is strictly greater, or −1.
`[4, 5, 2, 25, 7, 8]` → `[5, 25, 25, −1, 8, −1]`.
**Variants**: circular array (LeetCode 503), and previous smaller element.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each i, scan to the right. That's O(n²).
2. **Waste**: scanning right from i, you pass elements that can **never** be anyone's answer.
   In `… 3, 9 …`, the 3 is hidden behind the 9 for every element to the left of both.
3. **Monotonic stack (right to left)**: keep only the useful candidates, which form a **decreasing** sequence.
   For `a[i]`, pop every candidate `≤ a[i]` (now useless), and the top is the answer. Then push `a[i]`.
4. **Equivalent left-to-right view**: the stack holds indexes **still waiting** for an answer. A new bigger value resolves
   every smaller waiting index.
5. **Circular**: loop `2n` times using `i % n`, and push only in the first lap.

</div>

## Dry run (right to left)
| i | a[i] | Pop (≤ a[i]) | Answer | Stack after (top on the right) |
|---|------|--------------|--------|--------------------------------|
| 5 | 8 | | −1 | 8 |
| 4 | 7 | | 8 | 8 7 |
| 3 | 25 | 7, 8 | −1 | 25 |
| 2 | 2 | | 25 | 25 2 |
| 1 | 5 | 2 | 25 | 25 5 |
| 0 | 4 | | 5 | 25 5 4 |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/NextGreaterElement.java"
```

## Complexity
O(n). The nested `while` looks quadratic, but each element is pushed once and popped at most once. O(n) space.

## Family (same template, flip the comparison or direction)
Next smaller · previous greater · previous smaller · [Daily temperatures](daily-temperatures.md) · stock span ·
[Largest rectangle](largest-rectangle-histogram.md) · [Trapping rain water](../02-arrays/trapping-rain-water.md).
