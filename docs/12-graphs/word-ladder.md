# Word ladder

<span class="badge expert">Expert</span> <span class="badge tag">BFS</span> <span class="badge tag">Implicit graph</span> <span class="badge tag">Bidirectional BFS</span>

## Problem
Transform `begin` into `end` by changing **one letter at a time**, where every intermediate word must be in the dictionary.
Return the number of words in the shortest sequence, or 0.
`hit → hot → dot → dog → cog` = **5**.

## Thinking process
<div class="steps" markdown>

1. **Model**: words are nodes, and two words are connected if they differ in exactly one letter. You want the **fewest steps**
   in an unweighted graph, so use **BFS**.
2. **Don't build the graph**: comparing every pair of words is O(N²·L). Instead, **generate** each word's neighbours on the fly:
   try all 26 letters at each of its L positions and keep the candidates that are in the dictionary set. That's O(26·L) per word.
3. **Visited** = remove the word from the dictionary set when you enqueue it.
4. **Expert speed-up, bidirectional BFS**: grow one frontier from `begin` and one from `end`, always expanding the **smaller**
   frontier, and stop as soon as a generated word is in the other frontier. With branching factor b and distance d, the work
   drops from about b^d to about 2·b^(d/2).

</div>

## BFS levels
| Level | Words |
|-------|-------|
| 1 | hit |
| 2 | hot |
| 3 | dot, lot |
| 4 | dog, log |
| 5 | **cog** ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/WordLadder.java"
```

## Complexity
O(N · L · 26) time and O(N · L) space, where N = dictionary size and L = word length. The bidirectional version has the same
worst case but is much faster in practice.

## Why it's "Expert"
You have to see that it's a graph problem with no graph given, choose implicit neighbour generation over pairwise comparison,
and optionally reach for bidirectional BFS. Word Ladder II (return **all** shortest paths) adds parent tracking and
backtracking on top.

## Testing note
200 random small dictionaries: plain BFS and bidirectional BFS must agree.
