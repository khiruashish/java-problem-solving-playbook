# Longest substring without repeating characters

<span class="badge hard">Hard</span> <span class="badge tag">Sliding window</span> <span class="badge tag">Hashing</span>

## Problem
Return the length of the longest substring that has all distinct characters.

| Input | Output | Why |
|-------|--------|-----|
| `"abcabcbb"` | 3 | `"abc"` |
| `"bbbbb"` | 1 | `"b"` |
| `"pwwkew"` | 3 | `"wke"` (not `"pwke"`, which is a subsequence) |
| `"abba"` | 2 | catches the "left must not move back" bug |

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each start, extend while the chars stay unique. That's O(n²).
2. **Waste**: when a duplicate appears at `right`, every window starting before the duplicate's previous
   position is also invalid, so restarting from `start + 1` repeats work.
3. **Sliding window**: keep `[left, right]` valid. Expand `right`, and when a duplicate appears, shrink from `left`.
4. **Better**: store the **last index** of each char and **jump** `left` to `last + 1` immediately.
   Use `left = max(left, last + 1)` so `left` never moves backwards (the `"abba"` case).

</div>

## Approach 1 — Brute force
O(n²) time, O(k) space.

## Approach 2 — Sliding window + Set
Each char is added and removed at most once, so O(2n) = O(n).

## Approach 3 — Last-index jump (optimal)
<div class="steps" markdown>

1. `lastSeen[c]` stores `index + 1` (0 means never seen).
2. For each `right`: `left = max(left, lastSeen[s[right]])`.
3. `best = max(best, right - left + 1)`, then `lastSeen[s[right]] = right + 1`.

</div>

## Dry run (`"abba"`)
| right | char | lastSeen before | left | window | best |
|-------|------|-----------------|------|--------|------|
| 0 | a | 0 | 0 | a | 1 |
| 1 | b | 0 | 0 | ab | 2 |
| 2 | b | 2 | **2** | b | 2 |
| 3 | a | 1 | max(2,1) = **2** | ba | 2 |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/LongestSubstringWithoutRepeating.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(k) |
| Window + Set | O(n) | O(k) |
| Last-index jump | O(n) | O(1), fixed alphabet |

## The sliding-window template (memorise it)
```java
for (int right = 0; right < n; right++) {
    add(s[right]);
    while (windowInvalid()) remove(s[left++]);
    best = Math.max(best, right - left + 1);
}
```
It also solves: longest substring with at most K distinct chars, longest repeating-character replacement,
and [minimum window substring](minimum-window-substring.md) (where the shrink step records the minimum instead).
