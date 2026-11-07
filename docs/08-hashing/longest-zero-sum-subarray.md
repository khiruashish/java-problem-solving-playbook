# Longest subarray with sum 0 / equal 0s and 1s

<span class="badge hard">Hard</span> <span class="badge tag">Prefix sum</span> <span class="badge tag">First-index map</span>

## Problem
1. **Longest subarray with sum 0** (GfG): `[15,−2,2,−8,1,7,10,23]` → 5 (`−2,2,−8,1,7`).
2. **Equal 0s and 1s** (LeetCode 525): `[0,1,1,1,1,1,0,0,0]` → 6.

The code solves the general case, the longest subarray with sum **k**, which covers both.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: every start with a running sum. O(n²).
2. **Prefix-sum view**: `a[i+1..j]` sums to k ⇔ `prefix[j] − prefix[i] = k`.
3. To make that subarray **longest** for a fixed j, you want the **smallest** i, so store the **first index** at which each
   prefix value appears, and **never overwrite it** (`putIfAbsent`).
4. **Seed** `{0: −1}`, so a subarray starting at index 0 has length `j − (−1) = j + 1`.
5. **Equal 0s and 1s**: replace 0 with −1. Equal counts then means the sum is 0, so it's the same problem.

</div>

## Dry run (sum 0 on `[15,−2,2,−8,1,7,10,23]`)
| j | a[j] | prefix | First index of prefix | Length |
|---|------|--------|-----------------------|--------|
| 0 | 15 | 15 | new → 0 | |
| 1 | −2 | 13 | new → 1 | |
| 2 | 2 | 15 | seen at 0 | 2 |
| 3 | −8 | 7 | new → 3 | |
| 4 | 1 | 8 | new → 4 | |
| 5 | 7 | 15 | seen at 0 | **5** |

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/LongestZeroSumSubarray.java"
```

## Complexity
O(n) time and O(n) space with the map, versus O(n²) and O(1) for brute force.

## Count vs. longest: the one-line difference
| Goal | Map stores | Update |
|------|------------|--------|
| [Count subarrays](subarray-sum-equals-k.md) | prefix → **occurrences** | `merge(prefix, 1, sum)` |
| Longest subarray | prefix → **first index** | `putIfAbsent(prefix, j)` |
| Shortest subarray | prefix → **last index** | `put(prefix, j)` |
