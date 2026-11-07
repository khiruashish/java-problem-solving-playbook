# Contains duplicate

<span class="badge easy">Easy</span> <span class="badge tag">HashSet</span> <span class="badge tag">Sorting</span> <span class="badge tag">Streams</span>

## Problem
Return true if any value appears at least twice. `[1,2,3,1]` → true, `[1,2,3,4]` → false.
**Variant** (LeetCode 219): two equal values whose indexes are at most k apart.

## Clarifying questions
Can I modify the array (sorting in place saves memory)? How large is n? Do I need the duplicate itself or just yes/no?

## Thinking process
<div class="steps" markdown>

1. **Brute force**: compare every pair. O(n²) time, O(1) space.
2. **Sort**: equal values become **neighbours**, so one scan is enough. O(n log n) time, and O(1) extra if sorting in place is allowed.
3. **HashSet**: remember what you've seen. `Set.add` already returns `false` for a repeat, so there's no separate `contains` call.
   O(n) time, O(n) space, and it **exits early** on the first duplicate.
4. **Within distance k**: store the **last index** of each value. `map.put` returns the previous index, so you get it for free.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/ContainsDuplicate.java"
```

## Complexity
| Approach | Time | Space | Early exit? |
|----------|------|-------|-------------|
| Brute force | O(n²) | O(1) | yes |
| Sort | O(n log n) | O(1)–O(n) | yes |
| HashSet | O(n) | O(n) | yes |
| Streams `distinct().count()` | O(n) | O(n) | **no**: it always scans everything |

## Talking point
The three approaches are a clean demonstration of the **time vs. space trade-off**: O(n²)/O(1), O(n log n)/O(1) and O(n)/O(n).
Say this out loud.
