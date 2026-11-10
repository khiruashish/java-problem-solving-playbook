# Two sum

<span class="badge medium">Medium</span> <span class="badge tag">Hashing</span> <span class="badge tag">Two pointers</span>

## Problem
Return the indices of the two numbers that add up to `target` (exactly one solution). `[2,7,11,15]`, 9 → `[0,1]`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: check every pair. That's O(n²).
2. **Waste**: for each `x`, we *search* for `target − x` with a linear scan.
3. **Hashing**: remember the values we've seen, mapped to their indices. Each lookup is then O(1), for O(n) total.
4. **Look up before inserting**, so an element never pairs with itself (`[3,3]`, 6 works, `[3]`, 6 doesn't).
5. **If the array is sorted** (or you're allowed to sort), use two pointers, which needs O(1) extra space.

</div>

## Dry run (hash map, `[2,7,11,15]`, 9)
| i | a[i] | need | seen | Result |
|---|------|------|------|--------|
| 0 | 2 | 7 | {} → {2:0} | |
| 1 | 7 | 2 | found 2 → idx 0 | `[0,1]` ✅ |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/two-sum.html"

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/TwoSum.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| HashMap (optimal) | O(n) | O(n) |
| Sorted + two pointers | O(n) | O(1) |
| Sort indices + two pointers | O(n log n) | O(n) |

## The family
3Sum (sort, then fix one element and run two pointers on the rest, O(n²)) · 4Sum · Two sum in a BST · Count pairs with sum k.
