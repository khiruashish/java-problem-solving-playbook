---
hide:
  - navigation
  - toc
---

<div class="pb-hero" markdown>

# Java Problem-Solving Playbook

From **"I don't know where to start"** to **expert-level** answers. Every problem is solved the way a
15-year interviewer expects: think aloud, start with brute force, find the waste, reach the optimal
pattern. You get **recursive and iterative/normal** versions and a **Java 8 Streams** take where one fits.

[Start with Foundations :material-rocket-launch:](00-foundations/index.md){ .md-button .md-button--primary }
[See the Roadmap :material-map:](roadmap.md){ .md-button }

</div>

## Pick a track

**156 problems + ~70 Java 8 questions across 17 topics**, every approach compiled and verified by **160 JUnit tests** (most of them cross-check against a brute force on hundreds of random inputs).

<div class="grid cards" markdown>

-   :material-school:{ .lg .middle } **00 · Foundations**

    ---

    UMPIRE problem-solving framework, Big-O, recursion mental model, Java toolbox.

    [:octicons-arrow-right-24: 4 guides](00-foundations/index.md)

-   :material-format-text:{ .lg .middle } **01 · Strings**

    ---

    Reverse, palindromes, anagrams, frequency, sliding window, KMP, Manacher, minimum window.

    [:octicons-arrow-right-24: 16 problems](01-strings/index.md)

-   :material-code-array:{ .lg .middle } **02 · Arrays**

    ---

    Two pointers, Kadane, prefix products, intervals, next permutation, trapping rain water.

    [:octicons-arrow-right-24: 14 problems](02-arrays/index.md)

-   :material-calculator-variant:{ .lg .middle } **03 · Logical & Math**

    ---

    FizzBuzz, digits & overflow, primes & sieve, GCD/LCM, base conversion, patterns.

    [:octicons-arrow-right-24: 10 problems](03-logical/index.md)

-   :material-source-branch:{ .lg .middle } **04 · Recursion & Backtracking**

    ---

    Factorial → Fibonacci memo → Hanoi → subsets → combination sum → N-Queens → Sudoku.

    [:octicons-arrow-right-24: 13 problems](04-recursion/index.md)

-   :material-language-java:{ .lg .middle } **05 · Java 8+ Streams**

    ---

    Numbers, strings, the Employee round, Optional, custom collectors, parallel pitfalls.

    [:octicons-arrow-right-24: ~70 questions](05-java8/index.md)

-   :material-link-variant:{ .lg .middle } **06 · Linked List**

    ---

    Reversal, slow/fast pointers, Floyd's cycle, k-group reversal, LRU cache, merge K lists.

    [:octicons-arrow-right-24: 12 problems](06-linked-list/index.md)

-   :material-layers-triple:{ .lg .middle } **07 · Stack & Queue**

    ---

    Monotonic stack & deque, min stack, RPN, shunting-yard, histogram, a full calculator.

    [:octicons-arrow-right-24: 10 problems](07-stack-queue/index.md)

-   :material-pound:{ .lg .middle } **08 · Hashing**

    ---

    Prefix sum + map, longest consecutive, top K, meet in the middle, HashMap from scratch.

    [:octicons-arrow-right-24: 8 problems](08-hashing/index.md)

-   :material-sort-ascending:{ .lg .middle } **09 · Searching & Sorting**

    ---

    Binary search templates, all classic sorts, Quickselect, median of two arrays, search on answer.

    [:octicons-arrow-right-24: 11 problems](09-searching-sorting/index.md)

-   :material-file-tree:{ .lg .middle } **10 · Trees & BST**

    ---

    Traversals incl. Morris, views, LCA, validate BST, build & serialize, max path sum.

    [:octicons-arrow-right-24: 11 problems](10-trees/index.md)

-   :material-triangle-outline:{ .lg .middle } **11 · Heap**

    ---

    Top-K patterns, merge K arrays, task scheduler, two-heap median, heap from scratch.

    [:octicons-arrow-right-24: 7 problems](11-heap/index.md)

-   :material-graph:{ .lg .middle } **12 · Graphs**

    ---

    BFS/DFS, islands, topological sort, Dijkstra, Union-Find, MST, Bellman-Ford, Floyd-Warshall.

    [:octicons-arrow-right-24: 11 problems](12-graphs/index.md)

-   :material-table-large:{ .lg .middle } **13 · Dynamic Programming**

    ---

    Recursion → memo → table → O(1): knapsack, LIS, LCS, edit distance, interval DP.

    [:octicons-arrow-right-24: 12 problems](13-dynamic-programming/index.md)

-   :material-lightning-bolt:{ .lg .middle } **14 · Greedy**

    ---

    Activity selection, jump game, gas station, platforms, Huffman — each proven vs brute force.

    [:octicons-arrow-right-24: 7 problems](14-greedy/index.md)

-   :material-chip:{ .lg .middle } **15 · Bit Manipulation**

    ---

    Masks, Kernighan, single number I/II/III, XOR tricks, bitmask subsets, max-XOR trie.

    [:octicons-arrow-right-24: 7 problems](15-bit-manipulation/index.md)

-   :material-family-tree:{ .lg .middle } **16 · Tries & Advanced**

    ---

    Trie, word search II, autocomplete, segment tree, Fenwick tree, LFU cache.

    [:octicons-arrow-right-24: 7 problems](16-tries-advanced/index.md)

</div>

## How every problem page is built

<div class="steps" markdown>

1. **Problem + examples + clarifying questions**: what to ask before you write a single line.
2. **Thinking process**: restate the problem, try brute force, spot the waste, name the pattern.
3. **Approaches, from brute force to optimal**: each one with numbered steps and its complexity.
4. **Recursive and Java 8 versions** wherever they are natural.
5. **Dry run table**: trace the optimal solution by hand.
6. **Code**: embedded from the real, **compiled and JUnit-tested** source in `src/main/java`.
7. **Edge cases, follow-ups, related problems**: what the interviewer asks next.

</div>

## Difficulty legend

| Badge | Meaning |
|-------|---------|
| <span class="badge easy">Easy</span> | Single loop or direct API usage |
| <span class="badge medium">Medium</span> | One well-known pattern (two pointers, hashing, sliding window…) |
| <span class="badge hard">Hard</span> | A combination of patterns, or an invariant that isn't obvious |
| <span class="badge expert">Expert</span> | An advanced algorithm (KMP, Manacher, monotonic stack, backtracking with pruning) |

!!! tip "Run the code yourself"
    ```bash
    mvn test            # every approach of every problem is verified by JUnit 5
    mkdocs serve        # live-reload this site at http://127.0.0.1:8000
    ```
