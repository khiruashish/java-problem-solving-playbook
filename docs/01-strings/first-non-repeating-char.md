# First non-repeating character

<span class="badge easy">Easy</span> <span class="badge tag">Frequency counting</span> <span class="badge tag">Streams</span>

## Problem
Return the first character that appears exactly once, or `'\0'` if none does. The index variant returns its position, or −1.

## Examples
| Input | Output |
|-------|--------|
| `"leetcode"` | `l` |
| `"loveleetcode"` | `v` (index 2) |
| `"aabb"` | none |

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each i, scan all j to see whether `s[i]` repeats. That's O(n²).
2. **Insight**: we need the *count* of each char and the *original order*.
3. **Two passes**: pass 1 counts, pass 2 walks `s` in order and returns the first char with count 1. That's O(n).

</div>

## Approaches
| Approach | Time | Space |
|----------|------|-------|
| Nested loop | O(n²) | O(1) |
| `indexOf(c) == lastIndexOf(c)` | O(n²) | O(1) |
| Two-pass count (optimal) | O(n) | O(1), fixed alphabet |
| Streams: `LinkedHashMap` + `counting()` + `filter(v == 1)` | O(n) | O(k) |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/FirstNonRepeatingChar.java"
```

## Follow-ups
- **Stream of characters** (first unique so far after each char): keep counts plus a queue, and pop from the head while the head's count > 1.
- **First repeating character**: track a `Set` and return the first char where `add` returns false.
