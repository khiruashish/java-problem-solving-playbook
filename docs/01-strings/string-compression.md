# String compression (run-length encoding)

<span class="badge medium">Medium</span> <span class="badge tag">Scanning</span> <span class="badge tag">Recursion</span>

## Problem
Compress runs: `"aaabbc"` → `"a3b2c1"`. The CTCI variant returns the original string if the
compressed form isn't shorter. Also write `decode`, which must support multi-digit counts (`"a12b3"`).

## Thinking process
<div class="steps" markdown>

1. Equal characters are **adjacent**, so you only ever compare with the previous char.
2. Keep a `run` counter. When the char changes **or the string ends**, flush `char + run`.
3. **Trick**: loop `i` up to `n` *inclusive* so the final run is flushed in the same branch.

</div>

## Approach — Single pass (optimal)
| i | s[i-1] | s[i] | Action | Output |
|---|--------|------|--------|--------|
| 1 | a | a | run=2 | |
| 2 | a | a | run=3 | |
| 3 | a | b | flush a3 | a3 |
| 4 | b | b | run=2 | a3 |
| 5 | b | c | flush b2 | a3b2 |
| 6 | c | end | flush c1 | a3b2c1 |

## Recursive
Measure the first run, emit `char + run`, and recurse on `s.substring(run)`.

## Decode
Read a char, then read digits while `isDigit` (`count = count*10 + d`), then append `c.repeat(count)`.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/StringCompression.java"
```

## Complexity
O(n) time and O(n) output.

## Pitfalls
Using `String +=` in the loop (O(n²)). Forgetting to flush the last run. Single-digit-only decoding.

## Follow-up
LeetCode 443: compress **in place** in a `char[]` with read/write pointers, and omit the count when it's 1.
