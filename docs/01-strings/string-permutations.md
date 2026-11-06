# All permutations of a string

<span class="badge medium">Medium</span> <span class="badge tag">Recursion</span> <span class="badge tag">Backtracking</span>

## Problem
Return every arrangement of the characters. `"abc"` gives 6: `abc acb bac bca cab cba`. With duplicates
(`"aab"`), return only the distinct ones.

## Thinking process
<div class="steps" markdown>

1. For the first position, try **each** character. Then solve the same problem for the remaining characters. That's recursion.
2. **Base case**: no characters remain, so the prefix is a complete permutation.
3. There are n choices, then n−1, and so on, which gives **n!** leaves. You can't beat O(n · n!) because that is the output size.
4. To avoid creating substrings, **swap in place** on a `char[]` (choose → explore → un-choose).

</div>

## Recursion tree for `"abc"` (prefix | rest)
```
                  "" | abc
        /             |             \
    a | bc         b | ac         c | ab
    /    \         /    \         /    \
 ab|c   ac|b    ba|c   bc|a    ca|b   cb|a
   |      |       |      |       |      |
  abc    acb     bac    bca     cab    cba
```

## Approach 1 — Prefix + remaining (recursive)
`permute(prefix + rest[i], rest without i)` for each i.

## Approach 2 — Swap backtracking
For position k, swap each `i ≥ k` into slot k, recurse on k+1, then swap back. Collect into a `TreeSet` to dedupe.

## Approach 3 — Iterative (normal solution)
Sort the chars, then repeatedly apply [next permutation](../02-arrays/next-permutation.md) until it returns false.
This **naturally skips duplicates** and produces lexicographic order.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/StringPermutations.java"
```

## Complexity
O(n · n!) time and O(n) recursion depth (plus the output).

## Follow-ups
- Is one string a permutation of another's substring? Sliding window with counts (LeetCode 567).
- Kth permutation without generating all of them. Factorial number system, O(n²).
