# Majority element (Boyer–Moore voting)

<span class="badge easy">Easy</span> <span class="badge tag">Voting</span> <span class="badge tag">Hashing</span> <span class="badge tag">O(1) space</span>

## Problem
Return the value that appears **more than ⌊n/2⌋ times**. The classic version guarantees that one exists.
The follow-up asks you to report "none" when it doesn't.

## Examples
| Input | Output |
|-------|--------|
| `[3, 2, 3]` | 3 |
| `[2, 2, 1, 1, 1, 2, 2]` | 2 |
| `[1, 2, 3]` | none (follow-up) |
| `[1, 1, 2, 2]` | none: exactly n/2 is **not** a majority |

## Clarifying questions
- Is a majority guaranteed? If not, you need a second pass to verify.
- "More than n/2" or "at least n/2"? It changes the `[1,1,2,2]` case.
- Can I modify or sort the array?

## Thinking process
<div class="steps" markdown>

1. **Brute force**: count each value with a nested loop. O(n²).
2. **Hash map**: count once. O(n) time but O(n) space. The interviewer then asks for O(1) space.
3. **Sorting insight**: a value covering more than half the array must cover the **middle index**. O(n log n), no counting.
4. **Voting insight**: pair each occurrence of the majority with a *different* value and cancel both. The majority has more copies
   than everything else combined, so something of it always survives. Keep one `candidate` and a `votes` counter:
   +1 for a match, −1 otherwise, and pick a new candidate whenever votes hits 0.

</div>

## Dry run: `[2, 2, 1, 1, 1, 2, 2]`
| x | candidate | votes |
|---|-----------|-------|
| 2 | 2 | 1 |
| 2 | 2 | 2 |
| 1 | 2 | 1 |
| 1 | 2 | 0 |
| 1 | **1** (restart) | 1 |
| 2 | 1 | 0 |
| 2 | **2** (restart) | 1 |

Result: **2**. Note that the candidate changed twice. Voting only promises the right answer *at the end*, and only when a majority exists.

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/MajorityElement.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| Hash map / streams | O(n) | O(n) |
| Sorting | O(n log n) | O(n) for the copy |
| Boyer–Moore (+ verify pass) | **O(n)** | **O(1)** |

## Edge cases & pitfalls
- Without a guaranteed majority, Boyer–Moore still returns *some* candidate. Always run the verification pass.
- Empty array: there is no candidate.
- Sorting the caller's array in place is a side effect. The code sorts a clone.

## Follow-ups
**Majority II** (values appearing more than n/3 times): at most two can exist, so keep **two** candidates and two counters, then verify both.
In general, more than n/k needs k − 1 candidates.

## Related
[Top K frequent](../08-hashing/top-k-frequent.md) · [Single number](../15-bit-manipulation/single-number.md) (another "cancel pairs" trick, with XOR).
