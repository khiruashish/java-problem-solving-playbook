# Roadmap

Every topic below is ✅: written to the [master template](https://github.com/khiruashish/java-problem-solving-playbook/blob/main/MASTER_PROMPT.md), coded in Java 21, and verified by JUnit tests
(most problems cross-check every approach against a brute force on hundreds of random inputs).
To add more problems to any topic, use the **next-batch prompt** at the end of `MASTER_PROMPT.md`.

| # | Topic | Status | Problems |
|---|-------|--------|----------|
| 00 | [Foundations](00-foundations/index.md) | ✅ | Framework, Big-O, recursion mental model, Java toolbox, pattern recognition, constraints → complexity, stress testing |
| 01 | [Strings](01-strings/index.md) | ✅ | 19 (Easy → Expert) |
| 02 | [Arrays](02-arrays/index.md) | ✅ | 17 (Easy → Expert) |
| 03 | [Logical & Math](03-logical/index.md) | ✅ | 13 (Easy → Medium), from FizzBuzz to base conversion, patterns and LCM of 1..n |
| 04 | [Recursion & Backtracking](04-recursion/index.md) | ✅ | 16 (Easy → Expert), from factorial to N-Queens and a Sudoku solver |
| 05 | [Java 8+ Streams](05-java8/index.md) | ✅ | ~104 questions in 9 banks, including Optional, custom collectors, Map API, a transactions round, laziness and parallel pitfalls |
| 06 | [Linked List](06-linked-list/index.md) | ✅ | 15 (Easy → Expert), including LRU cache and merge K lists |
| 07 | [Stack & Queue](07-stack-queue/index.md) | ✅ | 13 (Easy → Expert), including monotonic stack/deque, shunting-yard and calculator |
| 08 | [Hashing](08-hashing/index.md) | ✅ | 11 (Easy → Expert), including prefix-sum + map and a HashMap from scratch |
| 09 | [Searching & Sorting](09-searching-sorting/index.md) | ✅ | 14 (Easy → Expert), from binary search templates to median of two arrays and binary search on the answer |
| 10 | [Trees & BST](10-trees/index.md) | ✅ | 14 (Easy → Expert), traversals (incl. Morris) to serialize/deserialize and max path sum |
| 11 | [Heap](11-heap/index.md) | ✅ | 10 (Easy → Expert), top-k patterns, two-heap median and a heap from scratch with heapsort |
| 12 | [Graphs](12-graphs/index.md) | ✅ | 14 (Easy → Expert), BFS/DFS to Dijkstra, Union-Find, MST, Bellman-Ford and Floyd-Warshall |
| 13 | [Dynamic Programming](13-dynamic-programming/index.md) | ✅ | 15 (Easy → Expert), each as recursion → memo → table → space-optimised, up to interval DP |
| 14 | [Greedy](14-greedy/index.md) | ✅ | 10 (Easy → Expert), each greedy proven against brute force or DP |
| 15 | [Bit Manipulation](15-bit-manipulation/index.md) | ✅ | 10 (Easy → Expert), masks and Kernighan to single number III and maximum XOR trie |
| 16 | [Tries & Advanced](16-tries-advanced/index.md) | ✅ | 10 (Medium → Expert), trie to word search II, segment tree, Fenwick tree and LFU cache |

**Total: 201 problems + ~104 Java 8 stream questions.**

## Ideas for the next batches

The previous ideas list (Z-algorithm, atoi, 3Sum, majority element, first missing positive, BST iterator, vertical order,
bipartite, SCC, stock with cooldown, egg drop, LPS, sparse table, lazy segment tree, consistent hashing, …) is now done.

| Topic | Candidates |
|-------|-----------|
| Strings | Wildcard / regex matching, minimum insertions to form a palindrome, Manacher deep-dive |
| Arrays | Spiral matrix, rotate image, set matrix zeroes, maximum product subarray |
| Trees | Morris pre-order, recover BST, binary tree cameras |
| Graphs | Bridges & articulation points (Tarjan), A* search, network flow (Edmonds–Karp) |
| DP | Travelling salesman (bitmask DP), digit DP, regular expression matching |
| Advanced | KMP automaton, persistent segment tree, skip list |
