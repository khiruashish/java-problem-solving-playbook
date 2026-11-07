# Burst balloons

<span class="badge expert">Expert</span> <span class="badge tag">Interval DP</span> <span class="badge tag">Think backwards</span>

## Problem
Burst balloons one at a time. Bursting balloon i earns `left × nums[i] × right`, where left and right are its **current**
neighbours (1 beyond the ends). Maximise the total. `[3,1,5,8]` → **167**.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every order, n! of them. The tests use this as the reference for small n.
2. **Why "first to burst" fails as a DP state**: bursting balloon k first makes its neighbours **adjacent**, so the left and right
   sides **aren't independent** anymore.
3. **The flip**: choose the balloon to burst **last** in an interval. While everything else inside (i, j) bursts, k is still
   standing, so the left part and right part never touch each other. When k finally bursts, its neighbours are exactly the
   **interval boundaries** i and j.
4. So `dp[i][j] = max over k in (i, j) of dp[i][k] + a[i]·a[k]·a[j] + dp[k][j]`, over an array padded with a 1 at each end.
5. Fill by increasing interval length, the [interval-DP](matrix-chain-multiplication.md) pattern again. That's **O(n³)**.

</div>

## Why it's "Expert"
The algorithm is ordinary interval DP. The insight, **reverse the time direction to make subproblems independent**, is not.
Spotting when "first" breaks independence and "last" restores it is a reusable senior-level trick.
It also appears in "minimum cost to cut a stick".

## Code
```java
--8<-- "src/main/java/com/playbook/dp/BurstBalloons.java"
```

## Complexity
Brute force O(n!) · interval DP O(n³) time, O(n²) space.

## Testing note
200 random arrays of up to 6 balloons (including zeros), where the interval DP must match the brute force over all orders.
