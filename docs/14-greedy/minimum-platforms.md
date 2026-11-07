# Minimum platforms

<span class="badge hard">Hard</span> <span class="badge tag">Sweep line</span> <span class="badge tag">Two pointers</span>

## Problem
Given the arrival and departure times of trains on one day, find the minimum number of platforms so no train waits.
A train arriving at the same minute another departs needs its **own** platform (the GfG convention).
arr `[900,940,950,1100,1500,1800]`, dep `[910,1200,1120,1130,1900,2000]` → **3**.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each train, count the trains present at its arrival time. The maximum is the answer. O(n²).
2. **Insight**: the answer is the **maximum number of overlapping intervals** at any moment. It changes only at arrivals (+1)
   and departures (−1).
3. **You don't need to know which train is which**, only the event times. So sort the arrivals and departures **separately**
   and merge them like merge sort. An arrival `≤` the next departure means +1 (the `≤` makes simultaneous events overlap).
   Otherwise −1.
4. The maximum of the running count is the answer.

</div>

## Sweep
| Event | Time | Platforms |
|-------|------|-----------|
| arr | 900 | 1 |
| dep | 910 | 0 |
| arr | 940 | 1 |
| arr | 950 | 2 |
| arr | 1100 | **3** |
| dep | 1120 | 2 |
| … | | |

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/MinimumPlatforms.java"
```

## Complexity
Brute force O(n²) · sweep O(n log n), O(n) for the copies.

## Same problem, other names
Meeting rooms II (LeetCode 253) · maximum concurrent sessions · car pooling. A min-heap of end times solves it too, also in O(n log n).
