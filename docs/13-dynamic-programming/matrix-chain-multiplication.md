# Matrix chain multiplication

<span class="badge expert">Expert</span> <span class="badge tag">Interval DP</span>

## Problem
Matrices A₁…Aₙ, where Aᵢ has dimensions `dims[i−1] × dims[i]`. Multiplication is associative, so choose the parenthesisation that
minimises scalar multiplications. `dims = [10,30,5,60]` → **4500**, from `(A₁A₂)A₃`, versus 27,000 for `A₁(A₂A₃)`.

## Thinking process
<div class="steps" markdown>

1. Multiplying a p×q matrix by a q×r one costs `p·q·r`.
2. **Last decision**: the final multiplication splits the chain `i..j` at some k: `(Aᵢ…Aₖ)(Aₖ₊₁…Aⱼ)`.
   The cost is `f(i,k) + f(k+1,j) + dims[i−1]·dims[k]·dims[j]`. Try every k.
3. **State** = the interval `(i, j)`: O(n²) states with O(n) choices of k each, so **O(n³)**.
4. **Fill order**: a chain needs **shorter** chains first, so loop over **length**, then the start. That's the signature of
   **interval DP**.

</div>

## Interval DP fill order
```
len 2: (1,2) (2,3) …          ← pairs first
len 3: (1,3) (2,4) …          ← uses len-2 results
…
len n: (1,n)                  ← the answer
```

## Code
```java
--8<-- "src/main/java/com/playbook/dp/MatrixChainMultiplication.java"
```

## Complexity
Recursion: exponential (Catalan) · memo/table: O(n³) time, O(n²) space.

## The interval-DP family
[Burst balloons](burst-balloons.md) · palindrome partitioning II · minimum cost to cut a stick · optimal BST · boolean
parenthesisation (GfG). All of them use the same loops: length outer, start, then the split point.
