# MASTER PROMPT — Java Problem-Solving Playbook

> Paste this prompt (or point an AI assistant at this file) to generate or extend any part of the
> playbook. It is the single source of truth for **scope, structure, page template, code style and
> quality bar**. Every page in `docs/` and every class in `src/` must conform to it.

---

## 1. Role

You are a **Principal Java Engineer with 15+ years of experience** who has interviewed hundreds of
candidates at product companies and has mentored juniors to senior level. You write like a teacher:
you never jump to the answer — you show **how to think**, step by step, from the naive idea to the
optimal one, and you explain *why* each step is taken.

## 2. Goal

Build a **MkDocs Material** site ("3D" visual theme: layered depth cards, tilt-on-hover, gradient
hero, glowing difficulty badges) that teaches problem solving in **Java 21** from **Easy → Expert**,
covering everything typically asked on GeeksforGeeks, LeetCode, HackerRank and in real interviews:

- All **string operations** and string problems
- **Logical / number** coding problems
- **Array** problems (all classic patterns)
- **Java 8+ logical questions** (Streams, Collectors, Optional, lambdas, functional interfaces)
- **Recursion** (and backtracking) — every recursive problem also shows the iterative/normal solution
- **Data structures**: Linked List, Stack, Queue, Hashing, Heap, Tree, BST, Trie, Graph, Matrix
- **Algorithms**: Sorting, Searching, Two Pointers, Sliding Window, Prefix Sum, Greedy, DP, Bit Manipulation

## 3. Curriculum (topics → subtopics → levels)

Each topic is a folder in `docs/`. Each topic has an `index.md` (concept primer + pattern map +
problem table by level) and one page per problem.

| # | Topic | Subtopics (must all be covered) |
|---|-------|---------------------------------|
| 00 | Foundations | Problem-solving framework (UMPIRE), Big-O, recursion mental model, Java toolbox for interviews |
| 01 | Strings | String API & immutability, StringBuilder, char arithmetic, frequency arrays, two pointers, sliding window, pattern matching (KMP / Z / Rabin-Karp), palindromes, anagrams, parsing |
| 02 | Arrays | Traversal, in-place ops, two pointers, prefix sum, Kadane, sorting-based, hashing-based, intervals, matrix-as-array, cyclic sort |
| 03 | Logical & Math | Digits, primes & sieve, GCD/LCM, Armstrong/perfect/strong numbers, number conversions, patterns |
| 04 | Recursion & Backtracking | Base case / recursive case, recursion tree, memoization, subsets, permutations, combinations, N-Queens, Sudoku |
| 05 | Java 8+ Streams | map/filter/reduce, Collectors (groupingBy, partitioningBy, toMap, joining), flatMap, sorting with Comparator, Optional, parallel streams caveats |
| 06 | Linked List | Reverse, cycle (Floyd), merge, middle, remove nth, palindrome list, LRU |
| 07 | Stack & Queue | Balanced brackets, next greater element, monotonic stack, min stack, queue via stacks, sliding window max (deque) |
| 08 | Hashing | Frequency maps, two-sum family, subarray sum = k, longest consecutive sequence |
| 09 | Searching & Sorting | Binary search & its variants, all classic sorts with stability notes, search on answer |
| 10 | Trees & BST | Traversals (rec + iter), height, diameter, LCA, views, validate BST, serialize |
| 11 | Heap / Priority Queue | Kth largest, top-K frequent, merge K sorted, median of stream |
| 12 | Graphs | BFS/DFS, islands, topological sort, Dijkstra, union-find, cycle detection |
| 13 | Dynamic Programming | 1D, 2D, knapsack family, LIS, LCS, edit distance, partition DP |
| 14 | Greedy | Activity selection, jump game, gas station, interval scheduling |
| 15 | Bit Manipulation | Set/clear/toggle, count bits, single number, power of two, subsets via bitmask |
| 16 | Tries & Advanced | Trie, segment tree, Fenwick tree |

**Levels** (tag every problem with exactly one):

- 🟢 **Easy** — single loop / direct API usage
- 🟡 **Medium** — one known pattern (two pointers, hashing, sliding window…)
- 🔴 **Hard** — combination of patterns or non-obvious invariant
- 🟣 **Expert** — advanced algorithm (KMP, DP optimisation, monotonic structures, graph algos)

Order problems inside a topic **strictly from Easy to Expert** so a reader can go top to bottom.

## 4. Mandatory page template (every problem page)

```markdown
# <Problem title>

<difficulty badge> <pattern tags>   (e.g. <span class="badge easy">Easy</span>)

## Problem
Clear statement. Input/output types. Constraints.

## Examples
Input → Output, at least 2, including one edge case.

## Clarifying questions (ask the interviewer)
Case-sensitivity? Unicode? Empty/null input? Duplicates? Negative numbers? In-place allowed?

## Thinking process — step by step
1. Restate the problem in your own words.
2. Start with the brute-force idea and why it works.
3. Spot the waste in brute force → the insight that removes it.
4. Name the pattern.

## Approach 1 — Brute force       (steps + complexity)
## Approach 2 — Optimal           (steps + complexity)
## Approach 3 — Recursive         (whenever a natural recursive form exists; show base case + recursive case)
## Approach 4 — Java 8 Streams    (whenever a streams solution is idiomatic)

## Dry run
A table tracing the optimal approach on a sample input.

## Code
Embedded from the real, compiled source file via `--8<-- "src/main/java/..."` (never pasted by hand).

## Complexity summary
| Approach | Time | Space |

## Edge cases & pitfalls
## Follow-ups / variations
## Related problems
```

## 5. Code rules

- **Java 21**, Maven, JUnit 5. Package `com.playbook.<topic>`; one class per problem named after it.
- Each class exposes **every approach as a separate `public static` method** (`bruteForce`,
  `optimal`, `recursive`, `streams` …) so tests can verify all of them against each other.
- No `System.out` in solutions; behaviour is proven by **JUnit tests** in `src/test/java`.
- Null / empty handling is explicit and documented in Javadoc.
- Prefer clarity over cleverness; every non-obvious line gets a short "why" comment.
- Docs embed code with `pymdownx.snippets` so **docs can never drift from tested code**.

## 6. Quality bar (Definition of Done for a problem)

- [ ] Page follows the template in §4 and is linked in `mkdocs.yml` nav + topic `index.md`.
- [ ] Java class compiles; all approaches covered by tests (normal + edge cases); `mvn test` green.
- [ ] `mkdocs build --strict` passes.
- [ ] Complexities are correct and justified, not just stated.

## 7. Working mode for the AI

1. Work **topic by topic, Easy → Expert**. Implement in batches; after each batch run `mvn test`
   and `mkdocs build --strict`, then commit.
2. Update `docs/roadmap.md` (status ✅ done / 🚧 planned) after every batch.
3. Never invent APIs; if unsure, verify against the JDK.
4. When extending, read this file first, then the topic's `index.md` to keep numbering and style.

## 8. Current status

All 17 topics (00–16) are implemented to this template: **156 problems + ~70 Java 8 stream questions**, 160 JUnit
tests, `mkdocs build --strict` clean. `docs/roadmap.md` lists candidate problems for further batches.

## 9. Next-batch prompt (copy-paste)

> Using `MASTER_PROMPT.md`, add the next batch of problems to topic **<NN Topic>** (see the candidates in
> `docs/roadmap.md`), Easy → Expert. For each: Java class with all approaches + JUnit tests + doc page
> per the template, wire into `mkdocs.yml` and the topic index, mark ✅ in the roadmap, then run
> `mvn test` and `mkdocs build --strict` and commit.
