# Group anagrams

<span class="badge medium">Medium</span> <span class="badge tag">Hashing</span> <span class="badge tag">Canonical key</span>

## Problem
Group words that are anagrams of each other.
`["eat","tea","tan","ate","nat","bat"]` → `[[eat,tea,ate],[tan,nat],[bat]]`

## Thinking process
<div class="steps" markdown>

1. Comparing every pair with an [anagram check](anagram.md) is O(n² · k). That's too slow.
2. **Insight**: give every word a **canonical key** that is identical for all its anagrams, then group with a HashMap.
3. **Key options**: the sorted chars (`"aet"`), at O(k log k), or the 26-count signature, at O(k).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/strings/GroupAnagrams.java"
```

## Complexity
| Key | Time | Space |
|-----|------|-------|
| Sorted chars | O(n · k log k) | O(n · k) |
| Count signature | O(n · k) | O(n · k) |

## Pitfall
Using `int[]` itself as a map key compares references. Convert it to a `String` (`Arrays.toString`) or a `List`.
