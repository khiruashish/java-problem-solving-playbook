# 02 · Arrays

## Concept primer

- Contiguous memory gives O(1) index access. Inserting or deleting in the middle is O(n) because elements shift.
- An "in place" answer usually means **O(1) extra space**, using read/write pointers or swaps.
- Most array problems are one of: **two pointers**, **prefix/suffix accumulation**, **Kadane**, **hashing**,
  **sorting first**, **reversal tricks**, or a **monotonic stack**.

## Pattern map

| Pattern | Problems |
|---------|----------|
| Linear scan | Max/Min, Second largest |
| Two pointers | Reverse, Move zeros, Remove duplicates, Two sum (sorted), Trapping rain water |
| Math / XOR | Missing number |
| Hashing | Two sum |
| Reversal trick | Rotate array, Next permutation |
| Kadane / DP | Maximum subarray, Stock buy/sell |
| Prefix / suffix | Product except self, Trapping rain water |
| Sort + sweep | Merge intervals |
| Monotonic stack | Trapping rain water |

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Find max & min](max-min.md) | <span class="badge easy">Easy</span> | One scan, or 1.5n pairwise comparisons |
| 2 | [Reverse an array](reverse-array.md) | <span class="badge easy">Easy</span> | Swap ends |
| 3 | [Second largest element](second-largest.md) | <span class="badge easy">Easy</span> | Track two maxima |
| 4 | [Move zeros to end](move-zeros.md) | <span class="badge easy">Easy</span> | Write pointer |
| 5 | [Remove duplicates from sorted array](remove-duplicates-sorted.md) | <span class="badge easy">Easy</span> | Slow/fast pointers |
| 6 | [Missing number](missing-number.md) | <span class="badge easy">Easy</span> | Sum formula / XOR |
| 7 | [Two sum](two-sum.md) | <span class="badge medium">Medium</span> | Complement lookup |
| 8 | [Rotate array by k](rotate-array.md) | <span class="badge medium">Medium</span> | Three reversals |
| 9 | [Maximum subarray sum (Kadane)](max-subarray-sum.md) | <span class="badge medium">Medium</span> | Extend or restart |
| 10 | [Best time to buy & sell stock](buy-sell-stock.md) | <span class="badge medium">Medium</span> | Min so far |
| 11 | [Product of array except self](product-except-self.md) | <span class="badge hard">Hard</span> | Prefix × suffix |
| 12 | [Merge intervals](merge-intervals.md) | <span class="badge hard">Hard</span> | Sort by start |
| 13 | [Next permutation](next-permutation.md) | <span class="badge hard">Hard</span> | Pivot, swap, reverse |
| 14 | [Trapping rain water](trapping-rain-water.md) | <span class="badge expert">Expert</span> | Two pointers / monotonic stack |
