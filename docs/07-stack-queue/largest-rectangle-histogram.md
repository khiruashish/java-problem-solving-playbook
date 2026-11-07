# Largest rectangle in a histogram

<span class="badge expert">Expert</span> <span class="badge tag">Monotonic stack</span> <span class="badge tag">Previous / next smaller</span>

## Problem
Given bar heights (each 1 wide), return the area of the largest rectangle under the histogram.
`[2,1,5,6,2,3]` → **10** (height 5 across the bars 5 and 6).

```
        █
      █ █
      █ █   █
      █ █ █ █
  █   █ █ █ █
  █ █ █ █ █ █
  2 1 5 6 2 3
```

## Thinking process
<div class="steps" markdown>

1. Every optimal rectangle is limited by its **shortest bar**. So for each bar i, ask: "what is the widest rectangle in
   which bar i is the shortest?"
2. That rectangle extends left until the **previous smaller** bar and right until the **next smaller** bar.
   Its area is `h[i] × (nextSmaller − prevSmaller − 1)`.
3. **Brute force**: expand left and right for each bar. That's O(n²).
4. **Two monotonic stacks** compute every previous-smaller and next-smaller index in O(n). See
   [next greater element](next-greater-element.md).
5. **One pass**: keep a stack of indexes with **increasing** heights. When a lower bar arrives at i, every taller bar popped
   has just found its right boundary (i), and its left boundary is the element **below it on the stack**. A sentinel bar of
   height 0 at the end flushes the stack.

</div>

## Dry run (one pass)
| i | h | Pops (height × width) | Best |
|---|---|-----------------------|------|
| 0 | 2 | | 0 |
| 1 | 1 | 2 × 1 = 2 | 2 |
| 2 | 5 | | 2 |
| 3 | 6 | | 2 |
| 4 | 2 | 6 × 1 = 6, 5 × 2 = **10** | 10 |
| 5 | 3 | | 10 |
| 6 | 0 (sentinel) | 3 × 1, 2 × 4 = 8, 1 × 6 = 6 | **10** |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/LargestRectangleHistogram.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| Nearest smaller (two stacks) | O(n) | O(n) |
| One pass | O(n) | O(n) |

## Follow-up: maximal rectangle of 1s in a matrix
Treat each row as the base of a histogram: `height[c]` = the number of consecutive 1s ending at this row. Run the one-pass
algorithm on every row, for O(rows × cols). It's included above and tested.
