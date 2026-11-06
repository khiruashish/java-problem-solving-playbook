# Minimum window substring

<span class="badge expert">Expert</span> <span class="badge tag">Sliding window</span> <span class="badge tag">Frequency counting</span>

## Problem
Given `s` and `t`, return the smallest substring of `s` that contains every char of `t`, **with multiplicity**.
`s = "ADOBECODEBANC"`, `t = "ABC"` → `"BANC"`. Return `""` if it's impossible.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each start, extend until the window covers `t`. That's O(n²).
2. **Observation**: once `[left, right]` covers `t`, moving `right` further only makes the window longer.
   The improvement comes from moving **left**.
3. **Variable window**: expand `right` until the window is valid, then shrink `left` while it stays valid,
   recording the minimum each time.
4. **O(1) validity check**: keep `need[c]` (how many of c we still need) and one integer `missing`.
   When `need[c]` goes from positive to lower while expanding, `missing--`. When it goes from 0 to positive while shrinking, `missing++`.

</div>

## Dry run (start)
| right | char | missing | Action |
|-------|------|---------|--------|
| 0 | A | 2 | useful |
| 3 | B | 1 | useful |
| 5 | C | **0** | valid `ADOBEC` (6). Shrink: drop A → missing 1 |
| 10 | A | 0 | valid `DOBECODEBA`… shrink… |
| 12 | C | 0 | … shrink until `BANC` (4) ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/MinimumWindowSubstring.java"
```

## Complexity
O(|s| + |t|) time, because each index enters and leaves the window once. O(1) space for the fixed alphabet.

## Why it's "Expert"
Three things must be right at the same time: (1) multiplicity through `need[]` going negative for surplus chars,
(2) the `missing` counter that avoids O(alphabet) checks, and (3) recording the answer **inside** the shrink loop.

## Related
Find all anagrams in a string · Permutation in string · Smallest window containing all distinct chars of itself (GfG).
