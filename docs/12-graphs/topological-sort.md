# Topological sort

<span class="badge medium">Medium</span> <span class="badge tag">Kahn's algorithm</span> <span class="badge tag">DFS</span>

## Problem
Order the nodes of a directed graph so that every edge u → v puts u **before** v. This only exists if there is no cycle (a DAG).
Edges `5→2, 5→0, 4→0, 4→1, 2→3, 3→1` → one valid order is `4 5 2 3 1 0`.

## Thinking process
<div class="steps" markdown>

1. A node with **in-degree 0** has no prerequisites, so it can go first.
2. **Kahn (BFS)**: queue every in-degree-0 node. Pop one, output it, and "delete" its outgoing edges by decrementing its
   neighbours' in-degrees. Any neighbour that reaches 0 joins the queue.
3. If the output has **fewer than V nodes**, the rest are stuck in a cycle. Kahn detects cycles for free.
4. **DFS**: a node finishes only after everything reachable from it. Push each node to the **front** of a list when it
   finishes, which reverses the finishing order. Use the three-colour state to detect cycles along the way.

</div>

## Kahn trace
| Ready queue | Output | In-degrees after |
|-------------|--------|------------------|
| 4, 5 | 4 | 0: 1, 1: 1 |
| 5 | 5 | 2: 0 → ready, 0: 0 → ready |
| 2, 0 | 2 | 3: 0 → ready |
| 0, 3 | 0 | |
| 3 | 3 | 1: 0 → ready |
| 1 | 1 | done (6 = V ✅) |

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/TopologicalSort.java"
```

## Complexity
O(V + E) time and O(V) space for both.

## Testing note
Orders aren't unique, so the tests don't compare with a fixed answer. They **validate** the result instead: it must be a
permutation, and every edge must point forward. On cyclic graphs, both methods must refuse.

## Used by
[Course schedule](course-schedule.md) · build systems (Gradle task graph) · spreadsheet recalculation · alien dictionary.
