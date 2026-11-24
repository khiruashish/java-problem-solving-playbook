# Candy

<span class="badge hard">Hard</span> <span class="badge tag">Two-pass greedy</span> <span class="badge tag">Slopes / mountains</span>

## Problem
Children stand in a line, each with a rating. Give each child **at least one candy**, and any child with a **higher rating than a neighbour**
must get **more candy than that neighbour**. Return the minimum total.

## Examples
| ratings | candies | total |
|---------|---------|-------|
| `[1, 0, 2]` | 2 1 2 | 5 |
| `[1, 2, 2]` | 1 2 1 | 4 (equal neighbours have no constraint) |
| `[1, 3, 4, 5, 2]` | 1 2 3 4 1 | 11 |

## Clarifying questions
- Equal ratings? No constraint between them, so they can get different amounts.
- Is the line circular? No.

## Thinking process
<div class="steps" markdown>

1. **Brute force (relaxation)**: give everyone 1, then repeatedly fix any child who should have more than a neighbour, until nothing changes. Correct, but O(n²).
2. **Split the constraints**: each child has a *left* rule (more than the left neighbour if rated higher) and a *right* rule.
3. **Left pass**: going left to right, `c[i] = c[i−1] + 1` on a rise. Every left rule now holds with the smallest values possible.
4. **Right pass**: going right to left, on a fall set `c[i] = max(c[i], c[i+1] + 1)`. The `max` keeps the left rule satisfied.
   Each child ends with the **larger of its two slope lengths**, which is the least that satisfies both rules.
5. **O(1) space**: walk the "mountains". An up-slope of length u costs 1 + 2 + … + u. A down-slope does the same counting down,
   and when the down-slope gets longer than the up-slope, the peak has to rise by one as well.

</div>

## Dry run (two passes): `[1, 0, 2]`
| pass | c |
|------|---|
| start | 1 1 1 |
| left → right (0 → 2 rises) | 1 1 2 |
| right → left (1 → 0 falls) | **2** 1 2 |

Total **5**.

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/Candy.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Relaxation (oracle) | O(n²) | O(n) |
| Two passes | **O(n)** | O(n) |
| One pass (slopes) | **O(n)** | **O(1)** |

## Edge cases & pitfalls
- In the right pass, overwriting `c[i] = c[i+1] + 1` without the `max` breaks the left-pass result at peaks.
- Equal neighbours reset everything: the next child can drop back to 1.
- The one-pass peak adjustment is the subtle part. The random tests cross-check it against relaxation on 3,000 arrays.

## Related
[Trapping rain water](../02-arrays/trapping-rain-water.md) (the same left-pass / right-pass idea) · [Product except self](../02-arrays/product-except-self.md).
