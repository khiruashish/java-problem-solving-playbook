# Merge sort & quick sort

<span class="badge medium">Medium</span> <span class="badge tag">Divide & conquer</span> <span class="badge tag">Recursion</span>

## Problem
Implement both O(n log n) divide-and-conquer sorts, and explain when you'd choose each.

## Thinking process
<div class="steps" markdown>

1. **Divide & conquer**: split the problem, solve the parts recursively, and combine.
2. **Merge sort** splits **blindly** (at the middle) and does its work in the **combine** step, merging two sorted halves
   with two pointers. It's always O(n log n), stable, and needs an O(n) buffer.
3. **Quick sort** does its work in the **split** step: **partition** around a pivot so the smaller elements go left and the
   larger ones right. Then there's nothing to combine. It sorts in place and is very fast in practice, but a bad pivot gives O(n²).
4. **Defending quick sort**:
    - **Random pivot** makes the worst case astronomically unlikely, even on sorted input.
    - **Recurse on the smaller side and loop on the larger** keeps the stack at O(log n).
    - **Hoare partition** (two pointers moving inward) handles many duplicates gracefully, where Lomuto degrades to O(n²).
5. **Bottom-up merge sort** merges runs of width 1, 2, 4, … with no recursion at all.

</div>

## Merge sort tree for `[5,2,4,1]`
```
          [5 2 4 1]
         /         \
     [5 2]         [4 1]
     /   \         /   \
   [5]   [2]     [4]   [1]
     \   /         \   /
     [2 5]         [1 4]
         \         /
          [1 2 4 5]
```

## Lomuto partition (pivot = last element)
`i` marks the end of the "< pivot" zone. Scan with `j`, and when `a[j] < pivot`, swap it into the zone. At the end,
swap the pivot to `i`, which is its final position.

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/quicksort-partition.html"

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/MergeQuickSort.java"
```

## Complexity
| | Merge sort | Quick sort |
|-|------------|------------|
| Time | O(n log n) always | O(n log n) expected, O(n²) worst |
| Extra space | O(n) | O(log n) stack |
| Stable | ✅ | ❌ |
| Best for | linked lists, external sorting, when stability is needed | in-memory arrays of primitives |

## Tested how?
The tests compare all four implementations against `Arrays.sort` on 300 random arrays. They also run quicksort on
**50,000 already-sorted** elements (a fixed pivot would overflow the stack) and on **50,000 equal** elements (which breaks
Lomuto, but not Hoare).
