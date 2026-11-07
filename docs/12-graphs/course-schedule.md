# Course schedule (can finish? / find an order)

<span class="badge hard">Hard</span> <span class="badge tag">Topological sort</span> <span class="badge tag">Cycle detection</span>

## Problem
There are `numCourses` courses. `prerequisites[i] = {a, b}` means you must take b **before** a.
1. Can you finish them all? (LeetCode 207)
2. Return a valid order, or an empty array. (LeetCode 210)

`2, [[1,0]]` → true, order `[0,1]` · `2, [[1,0],[0,1]]` → false.

## Thinking process
<div class="steps" markdown>

1. **Model it**: courses are nodes and prerequisites are directed edges. **Watch the direction**: `{a, b}` means b must come first,
   so the edge is **b → a**. Getting this backwards is the most common bug.
2. "Can finish everything" ⇔ there's **no cycle**, because a cycle means courses waiting on each other forever.
3. "Find an order" is a **topological sort**. Kahn's algorithm gives the order and cycle detection in one pass: if fewer than
   n courses come out, return `[]`.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/CourseSchedule.java"
```
(It reuses [TopologicalSort.kahn](topological-sort.md), so the interview answer is "reverse the edges, run Kahn, check the count".)

## Complexity
O(V + E) time and space.

## Why it's rated "Hard" here
The algorithm is standard. The difficulty is **modelling**: spotting that it's a graph problem, choosing the edge direction,
and connecting "impossible" with "cycle". Interviewers often follow up with *"what's the minimum number of semesters?"*
(the number of BFS levels in Kahn's algorithm).
