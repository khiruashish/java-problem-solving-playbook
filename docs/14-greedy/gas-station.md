# Gas station

<span class="badge medium">Medium</span> <span class="badge tag">Running total</span> <span class="badge tag">Restart</span>

## Problem
Stations sit on a circle. `gas[i]` is the fuel at station i, and `cost[i]` is the fuel to drive from i to i+1. Find the start
index from which you can drive around once, or −1. The answer is unique if it exists.
gas `[1,2,3,4,5]`, cost `[3,4,5,1,2]` → **3**.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: simulate from every start. O(n²).
2. **Feasibility**: if total gas < total cost, no start can work.
3. **Key insight**: suppose you start at s and run out of fuel arriving after station i. Then **no start between s and i works
   either**. Each of them would arrive at i with *at most* the fuel you had, because you reached each of them with ≥ 0 fuel
   in the tank. So skip them all and restart at i+1.
4. With total ≥ total cost, the last restart point is the answer. This is the same "drop a negative prefix" idea as
   [Kadane's algorithm](../02-arrays/max-subarray-sum.md).

</div>

## Dry run
| i | gas − cost | tank | Action |
|---|------------|------|--------|
| 0 | −2 | −2 | restart at 1 |
| 1 | −2 | −2 | restart at 2 |
| 2 | −2 | −2 | restart at 3 |
| 3 | +3 | 3 | |
| 4 | +3 | 6 | total = 0 ≥ 0 → **3** |

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/GasStation.java"
```

## Complexity
Brute force O(n²) · greedy O(n), O(1).

## Testing note
The tests check more than "both say possible". They rotate the arrays so greedy's start becomes index 0 and confirm that
the brute force accepts it.
