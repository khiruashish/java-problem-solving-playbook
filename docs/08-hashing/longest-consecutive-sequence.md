# Longest consecutive sequence

<span class="badge medium">Medium</span> <span class="badge tag">HashSet</span>

## Problem
Return the length of the longest run of consecutive integers in an **unsorted** array, in **O(n)**.
`[100,4,200,1,3,2]` → 4 (`1,2,3,4`). Duplicates don't break or extend a run.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each x, look for x+1, x+2, … with a linear search each time. O(n³) in the worst case.
2. **Sort**: runs become contiguous. Skip duplicates, extend when `c[i] == c[i−1] + 1`, otherwise reset. O(n log n).
   That's good, but the problem asks for O(n).
3. **HashSet makes "is x+1 present?" O(1)**. But counting upward from *every* x is still O(n²) on `1..n`,
   because each run is re-walked from each of its elements.
4. **Key insight**: only start counting at a **sequence start**, an x where **x − 1 is absent**. Each run is then walked exactly
   once, so the total work is O(n), even though the loop looks nested.

</div>

## Dry run (`{100, 4, 200, 1, 3, 2}`)
| x | x−1 present? | Action | Length |
|---|--------------|--------|--------|
| 100 | no | count 100 | 1 |
| 4 | yes (3) | skip | |
| 200 | no | count 200 | 1 |
| 1 | no | count 1,2,3,4 | **4** |
| 3, 2 | yes | skip | |

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/LongestConsecutiveSequence.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n³) | O(1) |
| Sort | O(n log n) | O(n) copy |
| HashSet (starts only) | O(n) | O(n) |

## Pitfalls
`x − 1` and `y + 1` overflow at `Integer.MIN_VALUE` and `MAX_VALUE`, so the code guards them. Iterating the original array
instead of the set re-processes duplicates, which is still correct but wasteful.

## Follow-up
Union-Find also solves it in near-O(n): union x with x+1 when both exist, and the answer is the largest component.
