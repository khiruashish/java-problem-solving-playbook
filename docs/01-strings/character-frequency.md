# Character frequency & duplicate characters

<span class="badge easy">Easy</span> <span class="badge tag">Hashing</span> <span class="badge tag">Frequency array</span>

## Problem
Count the occurrences of each character, keeping first-occurrence order. Also list the characters that occur more than once.

## Examples
`"programming"` → `{p=1, r=2, o=1, g=2, a=1, m=2, i=1, n=1}`, duplicates `[r, g, m]`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each char, count it with an inner loop. That's O(n²).
2. **Waste**: we rescan the string for every char.
3. **Insight**: one pass, incrementing a counter per char, gives O(n).
4. **Container choice**: `LinkedHashMap` keeps order. `int[26]` is fastest when the input is only `a–z`.

</div>

## Approaches
| Approach | How | Time | Space |
|----------|-----|------|-------|
| Brute force | nested loop | O(n²) | O(k) |
| HashMap | `freq.merge(c, 1, Integer::sum)` | O(n) | O(k) |
| `int[26]` | `counts[c - 'a']++` | O(n) | O(1) |
| Streams | `groupingBy(identity(), LinkedHashMap::new, counting())` | O(n) | O(k) |

`k` = number of distinct characters.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/CharacterFrequency.java"
```

## Follow-ups
- Sort characters by frequency (LeetCode 451). Use bucket sort on the counts.
- Find the max-occurring character. Track the max while counting.
- Count words instead of chars. See the [Java 8 string bank](../05-java8/string-streams.md).
