# 14 · Greedy

## Concept primer

A greedy algorithm makes the **locally best choice** at each step and **never reconsiders** it. When it works, it's usually
the simplest and fastest solution (often just a sort plus one pass). When it doesn't, it's silently wrong.

## How to know greedy is safe

<div class="steps" markdown>

1. **Try to break it**: look for a small counter-example. Greedy coin change fails on `[1,3,4]` for amount 6 (4+1+1 vs 3+3),
   and greedy-by-ratio fails on the 0/1 knapsack.
2. **Exchange argument**: take any optimal solution that differs from the greedy one at the first choice, swap in the greedy
   choice, and show it's **no worse**. If you can, greedy is optimal.
3. **If the exchange fails**, the problem needs [DP](../13-dynamic-programming/index.md).

</div>

!!! tip "The most common greedy shapes"
    **Sort by end time** (intervals), **sort both lists and match** (cookies, boats), **track the furthest reach** (jump game),
    **reset when a running total goes negative** (gas station, Kadane), **always combine the two smallest** (Huffman, ropes).

## Problems (Easy → Expert)

| # | Problem | Level | Greedy choice |
|---|---------|-------|---------------|
| 1 | [Assign cookies](assign-cookies.md) | <span class="badge easy">Easy</span> | Smallest cookie that satisfies the least greedy child |
| 2 | [Activity selection / non-overlapping intervals](activity-selection.md) | <span class="badge medium">Medium</span> | Earliest finish first |
| 3 | [Jump game I & II](jump-game.md) | <span class="badge medium">Medium</span> | Furthest reach |
| 4 | [Gas station](gas-station.md) | <span class="badge medium">Medium</span> | Restart after a deficit |
| 5 | [Minimum platforms](minimum-platforms.md) | <span class="badge hard">Hard</span> | Sweep sorted arrivals and departures |
| 6 | [Fractional knapsack](fractional-knapsack.md) | <span class="badge hard">Hard</span> | Best value/weight ratio |
| 7 | [Huffman coding](huffman-coding.md) | <span class="badge expert">Expert</span> | Merge the two rarest |

Every greedy solution here is tested against a **brute force or DP** on random inputs. That's the honest way to trust a greedy algorithm.

Also greedy elsewhere: [Buy & sell stock II](../02-arrays/buy-sell-stock.md) · [Merge intervals](../02-arrays/merge-intervals.md) ·
[Task scheduler](../11-heap/task-scheduler.md) · [Dijkstra](../12-graphs/dijkstra.md) · [Kruskal/Prim](../12-graphs/minimum-spanning-tree.md).
