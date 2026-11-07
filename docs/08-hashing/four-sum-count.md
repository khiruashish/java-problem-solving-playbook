# 4Sum II (count zero-sum tuples)

<span class="badge hard">Hard</span> <span class="badge tag">Meet in the middle</span> <span class="badge tag">HashMap</span>

## Problem
Given four arrays of length n, count the tuples `(i,j,k,l)` with `a[i] + b[j] + c[k] + d[l] = 0`.
`a=[1,2] b=[−2,−1] c=[−1,2] d=[0,2]` → 2.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: four nested loops. O(n⁴). With n = 200 that's 1.6 billion iterations, which is too slow.
2. **Hash one array**: three loops plus a lookup of `−(x+y+z)` in a count map of d. O(n³).
3. **Meet in the middle**: split the four arrays into two pairs. Count every sum `a[i] + b[j]` in a map (n² entries). Then for
   every `c[k] + d[l]`, add `map[−(c[k] + d[l])]`. **O(n²)** time and space.
4. **General principle**: when you combine m independent choices, hash half of them and enumerate the other half.
   That turns O(Nᵐ) into O(N^(m/2)).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/FourSumCount.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Four loops | O(n⁴) | O(1) |
| Three loops + map | O(n³) | O(n) |
| Meet in the middle | O(n²) | O(n²) |

## Contrast with 4Sum (one array, list the unique quadruplets)
That variant asks for *distinct value* tuples from a **single** array. Sort it, fix two indexes, and run two pointers,
for O(n³) with duplicate skipping. The hashing trick above fits *counting across separate arrays*.
