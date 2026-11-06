# Missing number

<span class="badge easy">Easy</span> <span class="badge tag">Math</span> <span class="badge tag">XOR</span>

## Problem
An array holds `n` distinct numbers from `0..n`, with exactly one missing. Find it. `[3,0,1]` → 2.

## Thinking process
<div class="steps" markdown>

1. **Sort**, then find the first `i` where `a[i] != i`. That's O(n log n).
2. **HashSet**: insert everything, then check `0..n`. That's O(n) time but O(n) space.
3. **Math**: the expected sum is `n(n+1)/2`. Subtract the actual sum. That's O(1) space. Use `long` to avoid overflow.
4. **XOR**: since `x ^ x = 0`, XOR-ing all the indexes `0..n` and all the values leaves only the missing number. It can't overflow.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/MissingNumber.java"
```

## Variations
- 1..n with one missing and one duplicated: use sum and sum-of-squares equations, or XOR partitioning.
- First missing **positive** (Hard): cyclic sort, putting each value v at index v−1.
