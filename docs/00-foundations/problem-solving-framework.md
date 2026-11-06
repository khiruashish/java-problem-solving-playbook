# Problem-solving framework (UMPIRE+)

Use this script on **every** problem. Say each step out loud in an interview.

<div class="steps" markdown>

1. **Understand.** Restate the problem in one sentence. Work through 2–3 examples by hand, including an edge case.
2. **Clarify.** Ask about the input range, null/empty, duplicates, negatives, case sensitivity, Unicode, whether you may modify the input, and the output format.
3. **Match.** Which pattern does it look like? See the table below.
4. **Brute force first.** State it, give its complexity, and **don't code it** unless asked. It proves you understand the problem.
5. **Find the waste.** What does brute force recompute or rescan? That waste is your optimisation.
6. **Implement.** Use clean names, handle edge cases at the top, and add a comment for every non-obvious invariant.
7. **Review.** Dry-run on your example, check the edge cases, state time and space, and suggest follow-ups.

</div>

## Pattern recognition table

| If the problem says… | Think… |
|----------------------|--------|
| "sorted array", "pair with sum" | Two pointers, binary search |
| "longest/shortest substring/subarray with…" | Sliding window |
| "count / frequency / seen before" | HashMap / int[26] |
| "subarray sum equals k" | Prefix sum + HashMap |
| "maximum subarray" | Kadane |
| "next greater / smaller", "histogram" | Monotonic stack |
| "all combinations / permutations / subsets" | Backtracking (recursion) |
| "overlapping subproblems", "number of ways", "min cost" | Dynamic programming |
| "top K", "Kth largest", "median of stream" | Heap |
| "intervals" | Sort by start, then merge/sweep |
| "shortest path in unweighted grid" | BFS |
| "dependencies / ordering" | Topological sort |
| "find pattern in text" | KMP / Rabin–Karp / Z |
| "XOR", "single number", "power of two" | Bit manipulation |

## What a senior answer sounds like

> "Brute force is O(n²): for each element I scan the rest. The waste is that I keep re-scanning for
> something I've already seen, so I'll store what I've seen in a HashMap. That gives O(n) time and
> O(n) space. If memory is tight and I'm allowed to sort, two pointers gives O(n log n) time and O(1)
> extra space."
