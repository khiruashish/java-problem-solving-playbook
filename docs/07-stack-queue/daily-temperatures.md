# Daily temperatures

<span class="badge medium">Medium</span> <span class="badge tag">Monotonic stack</span>

## Problem
For each day, return how many days you wait for a warmer temperature, or 0 if none comes.
`[73,74,75,71,69,72,76,73]` → `[1,1,4,2,1,1,0,0]`.

## Thinking process
<div class="steps" markdown>

1. This is [next greater element](next-greater-element.md), except the answer is a **distance**. So the stack stores
   **indexes**, not values.
2. Keep days that are **still waiting** on the stack. Their temperatures are decreasing from bottom to top, because a
   warmer day would already have resolved them.
3. When today is warmer than the top, pop it and record `today − thatDay`. Repeat, then push today.
4. **O(1) extra space trick**: walk from right to left. To find day i's answer, start at `j = i + 1` and, while `t[j] ≤ t[i]`,
   jump `j += out[j]`, which skips days already known to be colder.

</div>

## Dry run (stack of indexes)
| i | t[i] | Pops → answer | Stack (temps) |
|---|------|---------------|---------------|
| 0 | 73 | | 73 |
| 1 | 74 | 0 → 1 | 74 |
| 2 | 75 | 1 → 1 | 75 |
| 3 | 71 | | 75 71 |
| 4 | 69 | | 75 71 69 |
| 5 | 72 | 4 → 1, 3 → 2 | 75 72 |
| 6 | 76 | 5 → 1, 2 → 4 | 76 |
| 7 | 73 | | 76 73 (never resolved → 0) |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/DailyTemperatures.java"
```

## Complexity
| Approach | Time | Extra space |
|----------|------|-------------|
| Brute force | O(n²) | O(1) |
| Monotonic stack | O(n) | O(n) |
| Backward jumps | O(n) amortised | O(1) |

## Related
Online stock span (previous greater, counting the span) · Car fleet · Next warmer day in a circular year.
