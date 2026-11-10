# Trapping rain water

<span class="badge expert">Expert</span> <span class="badge tag">Two pointers</span> <span class="badge tag">Prefix max</span> <span class="badge tag">Monotonic stack</span>

## Problem
Given bar heights, compute how much water is trapped after rain. `[0,1,0,2,1,0,1,3,2,1,2,1]` → 6.

```
                 █
         █ ≈ ≈ ≈ █ █ ≈ █
     █ ≈ █ █ ≈ █ █ █ █ █ █
   ───────────────────────
h: 0 1 0 2 1 0 1 3 2 1 2 1      (≈ = water, total 6)
```

## Thinking process
<div class="steps" markdown>

1. **Key formula**: water above bar i = `min(maxLeft(i), maxRight(i)) − h[i]`.
2. **Brute force**: compute maxLeft and maxRight by scanning for each i. That's O(n²).
3. **Waste**: those maxima get recomputed every time. Precompute them as **prefix-max** and **suffix-max** arrays, for O(n) time and O(n) space.
4. **Two-pointer insight**: if `h[l] < h[r]`, then `maxRight(l) ≥ h[r] > h[l]`, so the water at `l` depends **only on leftMax**.
   Process `l` and move it. Mirror the logic for `r`. That's O(n) time and **O(1)** space.
5. **Alternative view**: a monotonic decreasing stack fills water **layer by layer** whenever a taller bar closes a basin.

</div>

## Dry run (two pointers, first steps)
| l | r | h[l] | h[r] | Side | leftMax / rightMax | +water |
|---|---|------|------|------|--------------------|--------|
| 0 | 11 | 0 | 1 | left | L = 0 | 0 |
| 1 | 11 | 1 | 1 | right | R = 1 | 0 |
| 1 | 10 | 1 | 2 | left | L = 1 | 0 |
| 2 | 10 | 0 | 2 | left | L = 1 | **1** |
| 3 | 10 | 2 | 2 | right | R = 2 | 0 |
| … | … | | | | | total **6** |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/trapping-rain-water.html"

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/TrappingRainWater.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| Prefix/suffix max | O(n) | O(n) |
| Two pointers | O(n) | O(1) |
| Monotonic stack | O(n) | O(n) |

## Why it's "Expert"
Writing the code is easy. *Justifying* the two-pointer move, and why the smaller side is safe to settle, is what interviewers look for.

## Related
Container with most water (two pointers, but area instead of volume) · Largest rectangle in a histogram (monotonic stack) · Trapping rain water II (a 2D grid with a min-heap).
