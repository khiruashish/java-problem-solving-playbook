# Reorganize string

<span class="badge medium">Medium</span> <span class="badge tag">Greedy + max-heap</span> <span class="badge tag">Counting</span>

## Problem
Rearrange the letters of `s` so that **no two adjacent characters are equal**. Return any valid arrangement, or `""` if none exists.

## Examples
| s | output |
|---|--------|
| `aab` | `aba` |
| `aaab` | `""` (impossible) |
| `vvvlo` | `vlvov` (or any other valid answer) |

## Clarifying questions
- Any valid answer, or a specific one? Any. That is why the tests **validate** the output instead of comparing strings.
- Alphabet? Lowercase a–z here.

## Thinking process
<div class="steps" markdown>

1. **When is it impossible?** The most frequent character needs a different character between each pair of its copies. With n positions it can
   take at most every other slot: **count ≤ ⌈n/2⌉ = (n + 1) / 2**. Any more and it is impossible, regardless of strategy.
2. **Greedy with a heap**: at each step, place the character with the **most remaining** copies that differs from the one just placed.
   The most frequent characters are the dangerous ones, so use them up first. Keep the just-used character **out of the heap for one step**, then put it back.
3. **Even/odd fill (no heap)**: put the most frequent character at indexes 0, 2, 4, …, then continue the other characters on the even indexes
   and wrap to 1, 3, 5, … Copies of the same character land at least 2 apart. O(n).

</div>

## Dry run (heap): `aaabbc`
| heap (count, char) | place | held back |
|--------------------|-------|-----------|
| (3,a) (2,b) (1,c) | a | (2,a) |
| (2,b) (1,c) | b | (1,b); a returns |
| (2,a) (1,c) | a | (1,a); b returns |
| (1,b) (1,c) | b | (0,b); a returns |
| (1,a) (1,c) | a | (0,a) |
| (1,c) | c | done → `ababac` |

## Code
```java
--8<-- "src/main/java/com/playbook/heap/ReorganizeString.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Max-heap greedy | O(n log σ), σ ≤ 26 | O(σ) |
| Even/odd fill | **O(n + σ)** | O(n) for the output |
| All permutations (test oracle) | O(n!) | O(n) |

## Edge cases & pitfalls
- Putting the just-used character straight back in the heap lets it be chosen again immediately.
- In the even/odd fill, place the **most frequent** character first. Otherwise another character can fill the even slots and leave too few gaps.
- The empty string is trivially valid.

## Related
[Task scheduler](task-scheduler.md) (the same "most frequent first, with a cooldown") · [Top K frequent words](top-k-frequent-words.md).
