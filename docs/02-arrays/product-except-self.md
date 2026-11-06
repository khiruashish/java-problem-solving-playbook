# Product of array except self

<span class="badge hard">Hard</span> <span class="badge tag">Prefix / suffix</span>

## Problem
Return `out[i]` = the product of every element except `a[i]`, **without division**, in O(n).
`[1,2,3,4]` → `[24,12,8,6]`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: a nested loop, O(n²).
2. **Why not division?** It fails on zeros, and the interviewer forbids it anyway.
3. **Insight**: `out[i] = (product of everything LEFT of i) × (product of everything RIGHT of i)`.
4. **Prefix/suffix arrays** take O(n) space. **Optimisation**: store the prefix products in `out`, then sweep from the right
   with a single `suffix` variable, for O(1) extra space.

</div>

## Dry run (`[1,2,3,4]`)
| i | prefix (left→) | suffix (←right) | out |
|---|----------------|-----------------|-----|
| 0 | 1 | 2·3·4 = 24 | 24 |
| 1 | 1 | 3·4 = 12 | 12 |
| 2 | 1·2 = 2 | 4 | 8 |
| 3 | 1·2·3 = 6 | 1 | 6 |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/ProductExceptSelf.java"
```

## Complexity
| Approach | Time | Extra space |
|----------|------|-------------|
| Brute force | O(n²) | O(1) |
| Prefix + suffix arrays | O(n) | O(n) |
| Optimal | O(n) | O(1) (output excluded) |

## Same pattern
[Trapping rain water](trapping-rain-water.md) (prefix-max/suffix-max) · Candy distribution · Left/right running sums (equilibrium index).
