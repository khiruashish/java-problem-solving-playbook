# Subsets via bitmask, submasks & Gray code

<span class="badge hard">Hard</span> <span class="badge tag">Bitmask enumeration</span>

## Problem
1. Generate all 2ⁿ subsets of n items using bitmasks.
2. Enumerate all **submasks** of a given mask.
3. Generate the n-bit **Gray code**, a sequence where consecutive values differ in exactly one bit.

## Thinking process
<div class="steps" markdown>

1. **Subsets**: the integers `0 … 2ⁿ − 1` are exactly the n-bit patterns, so each mask *is* a subset (bit j ⇔ item j). No recursion needed.
   Compare with the recursive version in [Subsets](../04-recursion/subsets.md).
2. **Submasks**: `sub = (sub − 1) & mask`. Subtracting 1 turns the lowest 1 into 0 and every bit below it into 1. ANDing with
   the mask keeps only the allowed bits. That gives the next smaller submask, ending at 0.
3. **Cost of all submasks of all masks**: each item is "not in mask", "in mask but not in sub", or "in both", which gives **3ⁿ**.
   This is the bound behind subset DP (for example, the travelling salesman problem in O(2ⁿ·n²)).
4. **Gray code**: `g(i) = i ^ (i >> 1)`. Going from i to i+1 flips a run of trailing bits, and XOR with the shifted value
   collapses that run into a single change.

</div>

## Submasks of 101
```
101 → (100) & 101 = 100 → (011) & 101 = 001 → (000) & 101 = 000
```

## Code
```java
--8<-- "src/main/java/com/playbook/bits/BitmaskSubsets.java"
```

## Complexity
Subsets O(n·2ⁿ) · submasks of one mask O(2^popcount) · all submasks of all masks O(3ⁿ) · Gray code O(2ⁿ).

## Testing note
For every mask under 64, the submask count must equal 2^popcount. The tests also check that consecutive Gray codes differ by exactly one bit.
