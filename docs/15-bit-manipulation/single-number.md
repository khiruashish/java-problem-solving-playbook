# Single number I, II, III

<span class="badge medium">Medium</span> <span class="badge tag">XOR</span> <span class="badge tag">Bit counting</span> <span class="badge tag">State machine</span>

## Problems
- **I**: every element appears **twice** except one. Find it. `[4,1,2,1,2]` → 4.
- **II**: every element appears **three times** except one. `[0,1,0,1,0,1,99]` → 99.
- **III**: every element appears twice except **two**. Find both. `[1,2,1,3,2,5]` → `[3,5]`.

All in O(n) time and **O(1) space**. A HashMap count is the baseline, but it uses O(n) space.

## Thinking process
<div class="steps" markdown>

1. **I, XOR**: `x ^ x = 0`, `x ^ 0 = x`, and XOR is commutative. XOR everything, the pairs cancel, and the single number remains.
2. **II, why XOR fails**: three copies XOR to x, not 0. **Count per bit instead**: for each of the 32 positions, count how many
   numbers have a 1 there. Triples contribute multiples of 3, so `count % 3` is the single number's bit.
3. **II, expert**: track the bits seen once (`ones`) and twice (`twos`) with
   `ones = (ones ^ x) & ~twos` and `twos = (twos ^ x) & ~ones`. A third occurrence clears both.
4. **III**: XOR everything to get `p ^ q`, which is non-zero. Pick **any set bit**, `(p ^ q) & −(p ^ q)`. p and q differ there,
   so splitting all numbers by that bit separates p from q, and pairs stay together. XOR each group.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/bits/SingleNumber.java"
```

## Complexity
| Version | Time | Space |
|---------|------|-------|
| HashMap | O(n) | O(n) |
| I (XOR) | O(n) | O(1) |
| II (bit counts) | O(32n) | O(1) |
| II (state machine) | O(n) | O(1) |
| III (split by bit) | O(n) | O(1) |

## Testing note
300 random shuffled arrays per variant, including negative numbers (which exercise the sign bit).
