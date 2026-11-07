# Partition equal subset sum

<span class="badge hard">Hard</span> <span class="badge tag">Subset sum</span> <span class="badge tag">0/1 knapsack</span> <span class="badge tag">Bitset</span>

## Problem
Can positive integers be split into two subsets with equal sums? `[1,5,11,5]` → true (`[11]` and `[1,5,5]`). `[1,2,3,5]` → false.

## Thinking process
<div class="steps" markdown>

1. **Reduce it**: an odd total can't be split. Otherwise the question becomes: *is there a subset summing to total/2?*
   That's the **subset-sum** problem.
2. **Recursion**: for each number, take it or skip it. O(2ⁿ).
3. **DP**: `can[s]` = "some subset seen so far sums to s". For each x, `can[s] |= can[s − x]`. Iterate s **downward** so each
   number is used at most once, exactly as in the [0/1 knapsack](knapsack.md).
4. **Bitset**: represent `can` as bits, so adding x becomes `bits |= bits << x`. That handles 64 sums per machine word,
   a constant-factor speed-up worth mentioning.

</div>

## DP trace (`[1,5,11,5]`, target 11)
| After | Reachable sums |
|-------|----------------|
| start | 0 |
| 1 | 0 1 |
| 5 | 0 1 5 6 |
| 11 | 0 1 5 6 11 ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/dp/PartitionEqualSubset.java"
```

## Complexity
Recursion O(2ⁿ) · DP O(n · sum) time, O(sum) space · bitset O(n · sum / 64).

## Family
Target sum (assign ± signs: `P − N = target`, so `P = (sum + target)/2`, a subset sum) · last stone weight II ·
minimum subset-sum difference · count subsets with a given sum.
