# Rotate array by k

<span class="badge medium">Medium</span> <span class="badge tag">Reversal trick</span>

## Problem
Rotate **right** by `k` in place. `[1,2,3,4,5,6,7]`, k = 3 → `[5,6,7,1,2,3,4]`. `k` may be larger than `n`.

## Thinking process
<div class="steps" markdown>

1. **Normalise**: `k %= n`, because rotating n times gives back the original.
2. **Brute force**: rotate by one, k times. That's O(n·k).
3. **Extra array**: `out[(i+k) % n] = a[i]`. That's O(n) time and O(n) space.
4. **Reversal insight**: the last k elements must come first. Reversing the whole array puts them at the front, but backwards.
   Reverse each part again to fix the order.

</div>

## Reversal, visualised
```
original      1 2 3 4 | 5 6 7
reverse all   7 6 5 | 4 3 2 1
reverse [0,k) 5 6 7 | 4 3 2 1
reverse [k,n) 5 6 7 | 1 2 3 4   ✅
```

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/RotateArray.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| One step at a time | O(n·k) | O(1) |
| Extra array | O(n) | O(n) |
| Three reversals | O(n) | O(1) |
| Juggling (GCD cycles) | O(n) | O(1) |

## Follow-up
Left rotation by d is right rotation by `n − d`. Searching a rotated sorted array is a [binary search variant](../planned/09-searching-sorting.md).
