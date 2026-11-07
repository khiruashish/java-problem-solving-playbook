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

<div class="grid cards" markdown>

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

    FizzBuzz, reverse number with overflow, Armstrong, primes & sieve, GCD/LCM.

    [:octicons-arrow-right-24: 5 problems](03-logical/index.md)

-   :material-source-branch:{ .lg .middle } **04 · Recursion & Backtracking**

    ---

    Factorial → Fibonacci memo → fast power → Hanoi → subsets → N-Queens.

    [:octicons-arrow-right-24: 7 problems](04-recursion/index.md)

-   :material-language-java:{ .lg .middle } **05 · Java 8+ Streams**

    ---

    ~50 interview questions: numbers, strings and the classic Employee dataset round.

    [:octicons-arrow-right-24: 3 question banks](05-java8/index.md)

-   :material-link-variant:{ .lg .middle } **06 · Linked List**

    ---

    Reversal, slow/fast pointers, Floyd's cycle, k-group reversal, LRU cache, merge K lists.

    [:octicons-arrow-right-24: 12 problems](06-linked-list/index.md)

-   :material-layers-triple:{ .lg .middle } **07 · Stack & Queue**

    ---

    Monotonic stack & deque, min stack, RPN, shunting-yard, histogram, a full calculator.

    [:octicons-arrow-right-24: 10 problems](07-stack-queue/index.md)

-   :material-graph:{ .lg .middle } **08–16 · Data Structures & Algorithms**

    ---

    Hashing, sorting, trees, heaps, graphs, DP, greedy, bits, tries. Planned in order.

    [:octicons-arrow-right-24: Roadmap](roadmap.md)

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
