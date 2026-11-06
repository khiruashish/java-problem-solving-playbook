# Reverse an array

<span class="badge easy">Easy</span> <span class="badge tag">Two pointers</span> <span class="badge tag">Recursion</span>

## Problem
Reverse `a` in place. `[1,2,3,4,5]` → `[5,4,3,2,1]`.

## Thinking process
<div class="steps" markdown>

1. Element `i` belongs at `n-1-i`.
2. Swapping the pair `(l, r)` fixes two positions at once, so you only need n/2 swaps.
3. Stop when `l >= r`. The middle element of an odd-length array stays where it is.

</div>

## Approaches
- **Iterative two pointers**: O(n) time, O(1) space.
- **Recursive**: swap `(l, r)`, then recurse on `(l+1, r-1)`. Base case `l >= r`. O(n) stack.
- **Streams** (new array): `IntStream.range(0,n).map(i -> a[n-1-i]).toArray()`.

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/ReverseArray.java"
```

## Follow-up
Reversing a sub-range `[l, r]` is the building block of [Rotate array](rotate-array.md) and [Next permutation](next-permutation.md).
