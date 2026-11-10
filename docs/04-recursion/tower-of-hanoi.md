# Tower of Hanoi

<span class="badge medium">Medium</span> <span class="badge tag">Tree recursion</span> <span class="badge tag">Bit tricks</span>

## Problem
Move n disks from peg A to peg C using B as a helper. Move one disk at a time, and never put a larger disk on a smaller one. List the moves.

## Thinking process
<div class="steps" markdown>

1. **Trust the recursion**: assume you can move n−1 disks.
2. To move n disks from A to C:
    1. Move n−1 disks from A to **B**, using C as the helper.
    2. Move disk n from A to **C**.
    3. Move n−1 disks from B to **C**, using A as the helper.
3. **Base case**: n = 0, so do nothing.
4. `T(n) = 2T(n−1) + 1`, which solves to **2ⁿ − 1** moves. That is provably the minimum.

</div>

## n = 3 (7 moves)
```
1: A→C  2: A→B  1: C→B  3: A→C  1: B→A  2: B→C  1: A→C
```

## Iterative (normal) solution
Move number `m` (1-based) moves disk `trailingZeros(m) + 1`, from peg `(m & (m−1)) % 3` to peg `((m | (m−1)) + 1) % 3`.
The peg labels are A,B,C when n is odd and A,C,B when n is even.

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/tower-of-hanoi.html"

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/TowerOfHanoi.java"
```

## Complexity
O(2ⁿ) time, which is the size of the output. Recursion depth is O(n).
