# Rotting oranges

<span class="badge medium">Medium</span> <span class="badge tag">Multi-source BFS</span>

## Problem
In a grid, `0` = empty, `1` = fresh and `2` = rotten. Every minute, rotten oranges rot their four neighbours. Return the minutes
until nothing fresh is left, or −1 if some orange can never rot.
```
2 1 1      minute 0 → 4
1 1 0      answer 4
0 1 1
```

## Thinking process
<div class="steps" markdown>

1. **Simulation**: each minute, scan the whole grid and rot the neighbours of rotten cells. Correct, but O((R·C)²).
2. Rot spreads **outward in rings**, one ring per minute. That's BFS levels.
3. **Multi-source**: put **every** initially rotten orange in the queue at time 0. BFS then expands from all of them at once,
   and each level is exactly one minute.
4. Count the fresh oranges first. Decrement as they rot, and if any remain at the end, return −1.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/RottingOranges.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Simulation | O((R·C)²) | O(R·C) |
| Multi-source BFS | O(R·C) | O(R·C) |

## Same pattern
Walls and gates (distance to the nearest gate) · 01-matrix (distance to the nearest 0) · Shortest bridge.
Whenever you have **many starting points** and want the distance from *the nearest one*, seed the BFS with all of them.
