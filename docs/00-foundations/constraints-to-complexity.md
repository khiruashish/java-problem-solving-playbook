# Constraints → complexity

<span class="badge easy">Guide</span> <span class="badge tag">Big-O</span> <span class="badge tag">Interview script</span>

The constraint block (`1 ≤ n ≤ 10⁵`) is the most underused hint in a problem statement. A Java judge or a
reasonable production budget does roughly **10⁸ simple operations per second**. Divide that by the
work your algorithm does for the largest n, and you know whether the idea will pass *before* you write it.

## The budget table

| Largest n | Target complexity | Typical techniques |
|-----------|-------------------|--------------------|
| n ≤ 10–12 | O(n!) | All permutations, brute-force search |
| n ≤ 20–25 | O(2ⁿ) or O(2ⁿ · n) | Subsets, bitmask DP, meet in the middle |
| n ≤ 100–500 | O(n³) | Floyd-Warshall, interval DP (matrix chain, burst balloons) |
| n ≤ 2,000–5,000 | O(n²) | 2D DP (LCS, edit distance), all pairs |
| n ≤ 10⁵–10⁶ | O(n log n) | Sorting, heaps, binary search, segment / Fenwick trees |
| n ≤ 10⁷–10⁸ | O(n) | One pass, two pointers, sliding window, prefix sums, sieve |
| n > 10⁸ or "very large" | O(log n) or O(1) | Binary search on the answer, math formulas, fast power |

## How to use it in the interview

<div class="steps" markdown>

1. **Read the constraint out loud.** "n is up to 10⁵, so O(n²) is 10¹⁰ operations: too slow. I need O(n log n) or better."
2. **State the brute force anyway**, with its complexity. It proves you understand the problem and gives you a test oracle.
3. **Pick the technique from the row**, then check the second dimension: memory. 10⁶ `int`s is about 4 MB, 10⁸ is 400 MB.
4. **Watch the values, not just n.** `values ≤ 10⁹` means sums can overflow an `int`: use `long`. Small values (≤ 10⁵) allow counting sort or an index-based array instead of a map.

</div>

## Worked examples

| Problem and constraint | Brute force | Too slow? | Target | Technique |
|------------------------|-------------|-----------|--------|-----------|
| [3Sum](../02-arrays/three-sum.md), n ≤ 3,000 | O(n³) = 2.7·10¹⁰ | yes | O(n²) = 9·10⁶ | sort + two pointers |
| [Koko eating bananas](../09-searching-sorting/koko-eating-bananas.md), piles ≤ 10⁴, pile ≤ 10⁹ | try every speed: 10⁹ · 10⁴ | yes | O(n log max) ≈ 3·10⁵ | binary search on the answer |
| [Egg drop](../13-dynamic-programming/egg-drop.md), k ≤ 100, n ≤ 10⁴ | O(k · n²) = 10¹⁰ | yes | O(k · log n) moves table | DP on "moves" instead of floors |
| [Sparse table RMQ](../16-tries-advanced/sparse-table.md), n, q ≤ 10⁵ | O(n) per query = 10¹⁰ | yes | O(1) per query | precompute powers of two |
| [Combinations](../04-recursion/combinations.md), n ≤ 20 | C(20, 10) = 184,756 | no | output size | backtracking is optimal |

## The hidden constant

Big-O hides constants, but Java makes some of them visible:

- **Boxing** (`List<Integer>`, `Map<Integer, Integer>`) is 3–10× slower than `int[]`. Prefer primitive arrays in hot loops.
- **Recursion depth** in Java is a few thousand frames by default. A DFS on a 10⁵-node path needs an explicit stack.
- **String concatenation in a loop** is O(n²). Use `StringBuilder`.
- **`HashMap` with poor hashes** degrades towards O(log n) per operation (tree bins), not O(1).

## Pitfalls

- Treating `10⁵` and `10⁶` the same: an O(n log n) with a heavy constant may pass one and fail the other.
- Forgetting that the **output** can be the bottleneck: listing all subsets of 20 items is 2²⁰ · 20 numbers no matter how clever you are.
- Ignoring **multiple test cases**: "t ≤ 10⁴ test cases, total n ≤ 2·10⁵" means you budget for the total, not each case.
