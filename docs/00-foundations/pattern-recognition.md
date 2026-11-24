# Pattern recognition: from keywords to technique

<span class="badge easy">Guide</span> <span class="badge tag">Interview script</span> <span class="badge tag">Cheat sheet</span>

Most interview problems are a known pattern in disguise. The fastest way to the optimal answer is to
**read the problem for signals** (words in the statement, the shape of the input, the constraint on n)
and map those signals to a short list of candidate techniques. Then you only have to choose between
two or three ideas instead of inventing one.

## The signal → pattern table

| If the problem says / shows… | Reach for… | Why it fits | Try it on |
|-------------------------------|-----------|-------------|-----------|
| "sorted array", "find a pair / triplet" | **Two pointers** from both ends | Sorted order tells you which pointer to move | [Two sum](../02-arrays/two-sum.md), [3Sum](../02-arrays/three-sum.md) |
| "contiguous subarray / substring", "longest / shortest window" | **Sliding window** | Grow right, shrink left, each index enters and leaves once | [Longest substring](../01-strings/longest-substring-without-repeating.md), [Minimum window](../01-strings/minimum-window-substring.md) |
| "subarray sum equals k", "count subarrays" with negatives | **Prefix sum + hash map** | `sum(i..j) = pre[j] − pre[i−1]`, so look up the complement | [Subarray sum = K](../08-hashing/subarray-sum-equals-k.md), [Divisible by K](../08-hashing/subarrays-divisible-by-k.md) |
| "seen before?", "duplicate", "frequency", "anagram" | **HashMap / HashSet / int[26]** | O(1) membership instead of a nested loop | [Contains duplicate](../08-hashing/contains-duplicate.md), [Group anagrams](../01-strings/group-anagrams.md) |
| "next greater / smaller", "span", "until a taller bar" | **Monotonic stack** | Each element is pushed and popped once | [Next greater element](../07-stack-queue/next-greater-element.md), [Histogram](../07-stack-queue/largest-rectangle-histogram.md) |
| "max / min of every window of size k" | **Monotonic deque** | Front of the deque is always the window's answer | [Sliding window maximum](../07-stack-queue/sliding-window-maximum.md) |
| "k largest / smallest / most frequent", "merge k sorted" | **Heap** of size k | Keep only k candidates, each step is O(log k) | [Kth largest](../11-heap/kth-largest.md), [Merge K arrays](../11-heap/merge-k-sorted-arrays.md) |
| "minimum value that still works", "maximise the minimum" | **Binary search on the answer** | Feasibility is monotonic in the answer | [Koko eating bananas](../09-searching-sorting/koko-eating-bananas.md), [Split array](../09-searching-sorting/split-array-largest-sum.md) |
| "all subsets / permutations / combinations", n ≤ 20 | **Backtracking** | Choose, recurse, un-choose | [Subsets](../04-recursion/subsets.md), [Combinations](../04-recursion/combinations.md) |
| "number of ways", "min cost", "can we reach" + overlapping choices | **Dynamic programming** | Same sub-question is asked many times | [Climbing stairs](../13-dynamic-programming/climbing-stairs.md), [Coin change](../13-dynamic-programming/coin-change.md) |
| "grid", "connected", "islands", "shortest steps" | **BFS / DFS** on an implicit graph | Cells are nodes, neighbours are edges | [Number of islands](../12-graphs/number-of-islands.md), [Flood fill](../12-graphs/flood-fill.md) |
| "prerequisites", "order of tasks", "dependencies" | **Topological sort** | A DAG ordering, or a cycle means impossible | [Course schedule](../12-graphs/course-schedule.md) |
| "connected components" with merges over time | **Union-Find** | Near-O(1) union and find | [Union-Find](../12-graphs/union-find.md) |
| "prefix", "starts with", "dictionary of words" | **Trie** | Shared prefixes are stored once | [Trie](../16-tries-advanced/trie.md), [Autocomplete](../16-tries-advanced/autocomplete.md) |
| "range sum / min with updates" | **Segment tree / Fenwick tree** | O(log n) per query and per update | [Segment tree](../16-tries-advanced/segment-tree.md), [Lazy segment tree](../16-tries-advanced/lazy-segment-tree.md) |
| "appears once while others appear twice", "power of two" | **Bit tricks / XOR** | `x ^ x = 0`, `x & (x − 1)` drops the lowest set bit | [Single number](../15-bit-manipulation/single-number.md) |
| "intervals", "meetings", "overlap" | **Sort by start or end + sweep** | After sorting, only neighbours can overlap | [Merge intervals](../02-arrays/merge-intervals.md), [Non-overlapping intervals](../14-greedy/non-overlapping-intervals.md) |
| "linked list", "middle", "cycle", "k-th from end" | **Fast / slow pointers** | Speed difference finds the middle or the loop | [Middle node](../06-linked-list/middle-node.md), [Cycle](../06-linked-list/linked-list-cycle.md) |

## A 4-question routine before you code

<div class="steps" markdown>

1. **What is the input shape?** An array, a string, a grid, a tree, a graph, or a stream. The shape alone removes half the table.
2. **Is it sorted, or can I afford to sort it?** Sorting costs O(n log n) and unlocks two pointers, binary search and greedy sweeps.
3. **What does the constraint on n allow?** See [constraints → complexity](constraints-to-complexity.md). n ≤ 20 hints at exponential search, n ≤ 10⁵ needs O(n log n) or better.
4. **What is the brute force, and what does it repeat?** The repeated work names the pattern: a recomputed sum means prefix sums, a rescanned window means a sliding window, a re-solved sub-question means DP.

</div>

## Worked example: "longest subarray with sum k" (values can be negative)

| Signal | Rules in | Rules out |
|--------|----------|-----------|
| "subarray" (contiguous) | sliding window, prefix sum | subsets / backtracking |
| values can be **negative** | prefix sum + map | sliding window (the window sum is no longer monotonic) |
| "longest" | store the **first** index of each prefix sum | storing the last index |

So the answer is **prefix sum + first-occurrence map**, O(n). The same reasoning is used in
[Longest zero-sum subarray](../08-hashing/longest-zero-sum-subarray.md).

## Pitfalls

- **Matching on one word.** "Subarray" with negative numbers breaks the sliding window. Always check the second signal.
- **Forcing a pattern.** If no row fits, write the brute force first. The waste in it is the clue.
- **Forgetting the constraint.** An O(n²) DP is the right answer for n = 1,000 and the wrong one for n = 10⁶.
