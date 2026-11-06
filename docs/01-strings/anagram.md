# Anagram check

<span class="badge easy">Easy</span> <span class="badge tag">Frequency counting</span> <span class="badge tag">Sorting</span>

## Problem
Two strings are anagrams if they contain the same characters with the same counts. `"listen"` and `"silent"` → true.

## Clarifying questions
Case-sensitive? Ignore spaces (`"dormitory"` vs `"dirty room"`)? Unicode?

## Thinking process
<div class="steps" markdown>

1. **Quick reject**: different lengths can never be anagrams.
2. **Sorting idea**: sorted anagrams are identical. That's O(n log n).
3. **Counting idea**: one array, `+1` for each char of a and `−1` for each char of b. Every slot must end at 0. That's O(n).

</div>

## Approach 1 — Sort and compare
O(n log n) time, O(n) space (char arrays).

## Approach 2 — Single count array (optimal)
<div class="steps" markdown>

1. If the lengths differ, return false.
2. For each i: `count[a[i]]++` and `count[b[i]]--`.
3. Return true if every count is 0.

</div>

## Approach 3 — HashMap
Works for any alphabet without a 65536-slot array. Return false as soon as any count would drop below zero.

## Approach 4 — Streams
Compare the two `groupingBy(counting())` maps.

## Dry run (`"aab"` vs `"aba"`, counting a and b)
| i | a[i] | b[i] | count a | count b |
|---|------|------|---------|---------|
| 0 | a | a | 0 | 0 |
| 1 | a | b | 1 | −1 |
| 2 | b | a | 0 | 0 → all zero ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/Anagram.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Sort | O(n log n) | O(n) |
| Count array | O(n) | O(1), fixed alphabet |
| HashMap / Streams | O(n) | O(k) |

## Related
[Group anagrams](group-anagrams.md) · Find all anagrams in a string (sliding window, LeetCode 438).
