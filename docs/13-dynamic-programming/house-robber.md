# House robber

<span class="badge easy">Easy</span> <span class="badge tag">1D DP</span> <span class="badge tag">Take / skip</span>

## Problem
Houses in a row hold money, and you can't rob two **adjacent** houses. Maximise the total.
`[2,7,9,3,1]` → 12 (2 + 9 + 1). **Variant**: the houses are in a **circle** (LeetCode 213).

## Thinking process
<div class="steps" markdown>

1. **Last decision** at house i: **skip** it (best up to i−1), or **take** it (`a[i]` plus the best up to i−2, since i−1 is now forbidden).
   `f(i) = max(f(i−1), a[i] + f(i−2))`.
2. Recursion → memo → table, then only two values are needed, so use O(1) space.
3. **Circle**: the first and last houses are now adjacent, so you can't take both. Solve two straight-line problems,
   **without the last** and **without the first**, and take the larger.

</div>

## Dry run (`[2,7,9,3,1]`)
| i | a[i] | skip = prev1 | take = prev2 + a[i] | best |
|---|------|--------------|---------------------|------|
| 0 | 2 | 0 | 2 | 2 |
| 1 | 7 | 2 | 7 | 7 |
| 2 | 9 | 7 | 2 + 9 = 11 | 11 |
| 3 | 3 | 11 | 7 + 3 = 10 | 11 |
| 4 | 1 | 11 | 11 + 1 = **12** | 12 |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/house-robber.html"

## Code
```java
--8<-- "src/main/java/com/playbook/dp/HouseRobber.java"
```

## Complexity
Recursive O(2ⁿ) · memo O(n)/O(n) · optimal O(n)/O(1).

## Family
House robber III (binary tree: each node returns `(robbed, notRobbed)`) · Delete and earn (bucket the values, then house robber) ·
Maximum sum of non-adjacent elements (GfG).
