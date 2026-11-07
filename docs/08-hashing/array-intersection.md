# Intersection of two arrays

<span class="badge easy">Easy</span> <span class="badge tag">HashSet</span> <span class="badge tag">Count map</span> <span class="badge tag">Two pointers</span>

## Problem
1. **Unique** (LeetCode 349): each common value once. `[4,9,5]` ∩ `[9,4,9,8,4]` → `[4,9]`.
2. **With counts** (LeetCode 350): each value as many times as it appears in **both**. `[1,2,2,1]` ∩ `[2,2]` → `[2,2]`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each value of a, search b. O(n·m).
2. **Unique**: put a in a `HashSet`, then keep the values of b that are in it, collecting them in another set to remove repeats. O(n + m).
   `Set.retainAll` is the library version.
3. **With counts**: a set loses the counts, so use a **count map** for a. While walking b, take a value only if its count
   is still > 0, then decrement.
4. **Both arrays sorted**: two pointers, advancing the smaller side and taking the value when they're equal. O(1) extra space.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/ArrayIntersection.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n·m) | O(1) |
| HashSet / count map | O(n + m) | O(min(n, m)) if you hash the smaller array |
| Sorted + two pointers | O(n + m) | O(1) |

## Follow-ups interviewers ask
- *One array is tiny and the other huge?* Hash the small one.
- *b is on disk and doesn't fit in memory?* Hash a, then stream b in chunks.
- *Both arrays are sorted?* Two pointers.
