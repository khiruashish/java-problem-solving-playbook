# Jump game I & II

<span class="badge medium">Medium</span> <span class="badge tag">Furthest reach</span> <span class="badge tag">Implicit BFS</span>

## Problem
`a[i]` = the maximum jump length from index i.
1. **Can** you reach the last index? `[2,3,1,1,4]` → yes · `[3,2,1,0,4]` → no.
2. The **minimum** number of jumps. `[2,3,1,1,4]` → 2 (0 → 1 → 4).

## Thinking process
<div class="steps" markdown>

1. **DP** O(n²): index i is reachable if some reachable j < i can jump to it. Min jumps is `dp[i] = 1 + min dp[j]`.
2. **Greedy for I**: keep `furthest` = the furthest index reachable so far. Scan i. If `i > furthest`, you're stuck.
   Otherwise extend `furthest = max(furthest, i + a[i])`.
3. **Greedy for II, as an implicit BFS**: every index reachable with k jumps forms a **contiguous window**
   `[start, currentEnd]`. While scanning that window, track the furthest reach. When i passes `currentEnd`, you **must** jump
   again, and the next window ends at `furthest`.

</div>

## BFS windows for `[2,3,1,1,4]`
| Jumps | Window | Furthest from the window |
|-------|--------|--------------------------|
| 0 | [0, 0] | 2 |
| 1 | [1, 2] | 1 + 3 = 4 ≥ last |
| 2 | reached | **2 jumps** |

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/JumpGame.java"
```

## Complexity
DP O(n²) · greedy O(n), O(1).

## Testing note
400 random arrays, including zeros that make the end unreachable. Greedy must match DP for both questions.
