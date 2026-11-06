# Longest common prefix

<span class="badge medium">Medium</span> <span class="badge tag">Scanning</span> <span class="badge tag">Divide & conquer</span>

## Problem
Return the longest prefix shared by all strings. `["flower","flow","flight"]` → `"fl"`; `["dog","racecar","car"]` → `""`.

## Thinking process
<div class="steps" markdown>

1. The answer can't be longer than the **shortest** string.
2. **Vertical scan**: compare column 0 across all strings, then column 1, and so on. Stop at the first mismatch or when any string ends.
3. **Horizontal scan**: start with prefix = `strs[0]` and shrink it until each string `startsWith` it.
4. **Sort trick**: after sorting lexicographically, the first and last strings are the *most different*, so their common prefix is everyone's.
5. **Divide & conquer**: `LCP(all) = LCP(LCP(left half), LCP(right half))`.

</div>

## Dry run (vertical scan)
| col | flower | flow | flight | Result |
|-----|--------|------|--------|--------|
| 0 | f | f | f | ✅ |
| 1 | l | l | l | ✅ |
| 2 | o | o | **i** | stop → `"fl"` |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/LongestCommonPrefix.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Vertical / horizontal | O(S), S = total chars | O(1) |
| Sort trick | O(S log n) | O(n) copy |
| Divide & conquer | O(S) | O(m log n) recursion |

## Follow-up
Many queries against a fixed set? Build a **Trie** and walk down while a node has exactly one child.
