# Roadmap

Every topic below is ✅: written to the [master template](https://github.com/khiruashish/java-problem-solving-playbook/blob/main/MASTER_PROMPT.md), coded in Java 21, and verified by JUnit tests
(most problems cross-check every approach against a brute force on hundreds of random inputs).
To add more problems to any topic, use the **next-batch prompt** at the end of `MASTER_PROMPT.md`.

| # | Topic | Status | Problems |
|---|-------|--------|----------|
| 00 | [Foundations](00-foundations/index.md) | ✅ | Framework, Big-O, recursion mental model, Java toolbox |
| 01 | [Strings](01-strings/index.md) | ✅ | 16 (Easy → Expert) |
| 02 | [Arrays](02-arrays/index.md) | ✅ | 14 (Easy → Expert) |
| 03 | [Logical & Math](03-logical/index.md) | ✅ | 10 (Easy → Medium), from FizzBuzz to base conversion, patterns and LCM of 1..n |
| 04 | [Recursion & Backtracking](04-recursion/index.md) | ✅ | 13 (Easy → Expert), from factorial to N-Queens and a Sudoku solver |
| 05 | [Java 8+ Streams](05-java8/index.md) | ✅ | ~70 questions in 6 banks, including Optional, custom collectors and parallel pitfalls |
| 06 | [Linked List](06-linked-list/index.md) | ✅ | 12 (Easy → Expert), including LRU cache and merge K lists |
| 07 | [Stack & Queue](07-stack-queue/index.md) | ✅ | 10 (Easy → Expert), including monotonic stack/deque, shunting-yard and calculator |
| 08 | [Hashing](08-hashing/index.md) | ✅ | 8 (Easy → Expert), including prefix-sum + map and a HashMap from scratch |
| 09 | [Searching & Sorting](09-searching-sorting/index.md) | ✅ | 11 (Easy → Expert), from binary search templates to median of two arrays and binary search on the answer |
| 10 | [Trees & BST](10-trees/index.md) | ✅ | 11 (Easy → Expert), traversals (incl. Morris) to serialize/deserialize and max path sum |
| 11 | [Heap](11-heap/index.md) | ✅ | 7 (Easy → Expert), top-k patterns, two-heap median and a heap from scratch with heapsort |
| 12 | [Graphs](12-graphs/index.md) | ✅ | 11 (Easy → Expert), BFS/DFS to Dijkstra, Union-Find, MST, Bellman-Ford and Floyd-Warshall |
| 13 | [Dynamic Programming](13-dynamic-programming/index.md) | ✅ | 12 (Easy → Expert), each as recursion → memo → table → space-optimised, up to interval DP |
| 14 | [Greedy](14-greedy/index.md) | ✅ | 7 (Easy → Expert), each greedy proven against brute force or DP |
| 15 | [Bit Manipulation](15-bit-manipulation/index.md) | ✅ | 7 (Easy → Expert), masks and Kernighan to single number III and maximum XOR trie |
| 16 | [Tries & Advanced](16-tries-advanced/index.md) | ✅ | 7 (Medium → Expert), trie to word search II, segment tree, Fenwick tree and LFU cache |

**Total: 156 problems + ~70 Java 8 stream questions.**

## Ideas for the next batches

| Topic | Candidates |
|-------|-----------|
| Strings | Z-algorithm, string to integer (atoi), count and say, wildcard / regex matching |
| Arrays | 3Sum, spiral matrix, rotate image, majority element (Boyer–Moore), first missing positive |
| Trees | Morris pre-order, vertical order traversal, BST iterator, recover BST |
| Graphs | Bipartite check, bridges & articulation points (Tarjan), strongly connected components (Kosaraju) |
| DP | Longest palindromic subsequence, egg drop, stock with cooldown, travelling salesman (bitmask DP) |
| Advanced | Lazy segment tree, sparse table (RMQ), KMP automaton, consistent hashing |
