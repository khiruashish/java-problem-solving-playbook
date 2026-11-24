# Isomorphic strings

<span class="badge easy">Easy</span> <span class="badge tag">Bijection</span> <span class="badge tag">Two maps</span> <span class="badge tag">Canonical form</span>

## Problem
Two strings are **isomorphic** if you can replace characters of `s` to get `t`, where every occurrence of a character
is replaced by the same character, and **no two characters map to the same one**. A character may map to itself.

## Examples
| s | t | isomorphic? | Why |
|---|---|-------------|-----|
| `egg` | `add` | yes | e→a, g→d |
| `paper` | `title` | yes | p→t, a→i, e→l, r→e |
| `foo` | `bar` | no | o would need to map to both a and r |
| `badc` | `baba` | no | b→b and d→b: two letters onto one |

## Clarifying questions
- Same length required? Yes, otherwise the answer is false immediately.
- Character set? Any `char`. The `int[65536]` version covers all of them.

## Thinking process
<div class="steps" markdown>

1. **One map s→t is not enough.** `badc`/`baba` passes a one-way check, because each s-letter maps consistently, but b and d both map to b.
   The relation must be a **bijection**, so keep a second map t→s too.
2. **Structural view**: two strings are isomorphic iff they have the same **shape**. Replace each char by the index of its first
   occurrence (`paper` → `0 1 0 3 4`, `title` → `0 1 0 3 4`) and compare. Elegant, and it groups many strings at once.
3. **No maps at all**: walk both strings together and store, for each char, the position where it was last seen.
   At every index the two "last seen" values must agree, otherwise the histories differ.

</div>

## Dry run (two maps): `badc` / `baba`
| i | pair | s→t | t→s | conflict? |
|---|------|-----|-----|-----------|
| 0 | b, b | b→b | b→b | |
| 1 | a, a | a→a | a→a | |
| 2 | d, b | d→b | b is already →b, but now needs →d | **yes → false** |

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/IsomorphicStrings.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Two maps | O(n) | O(σ), with σ the alphabet size |
| Last-seen arrays | O(n) | O(σ), with no boxing |
| Canonical pattern | O(n) | O(n) |

## Edge cases & pitfalls
- Comparing `Character` objects with `!=` compares references. It only works by accident for cached values (code ≤ 127). Compare the unboxed `char`, as the code does.
- In the last-seen version, store `i + 1` so 0 keeps meaning "never seen".

## Follow-ups
**Word pattern** (`"abba"` vs `"dog cat cat dog"`): the same bijection, between letters and words. **Group isomorphic strings**: bucket by canonical pattern.

## Related
[Group anagrams](../01-strings/group-anagrams.md) (another "canonical key") · [Anagram check](../01-strings/anagram.md).
