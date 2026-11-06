# Big-O cheat sheet

## What input size can each complexity handle? (≈10⁸ simple ops/sec)

| n up to | Acceptable complexity | Typical technique |
|---------|----------------------|-------------------|
| 10–12 | O(n!) | Permutations |
| 20–25 | O(2ⁿ) | Subsets, bitmask |
| 500 | O(n³) | Triple loop, interval DP |
| 10⁴ | O(n²) | Nested loops, 2D DP |
| 10⁶ | O(n log n) | Sorting, heap, binary search |
| 10⁸ | O(n) | Single pass, two pointers, hashing |
| ∞ | O(log n) / O(1) | Binary search, maths |

## Java collection costs you must know

| Operation | ArrayList | LinkedList | HashMap/HashSet | TreeMap/TreeSet | ArrayDeque | PriorityQueue |
|-----------|-----------|------------|-----------------|-----------------|------------|---------------|
| get(i) | O(1) | O(n) | — | — | — | — |
| add end | O(1)* | O(1) | O(1)* | O(log n) | O(1)* | O(log n) |
| add front / middle | O(n) | O(1) at a node you already hold | — | — | O(1) front | — |
| contains | O(n) | O(n) | O(1)* | O(log n) | O(n) | O(n) |
| remove min/max | — | — | — | O(log n) | — | O(log n) |

\* amortised.

## String costs (immutability!)

- `s + c` inside a loop is **O(n²)** overall, because every concatenation copies the string. Use `StringBuilder`.
- `substring` copies (since JDK 7u6), so it is **O(k)**, not O(1).
- `s.toCharArray()` is O(n) and returns a copy; `s.charAt(i)` is O(1).

## Recursion space

The stack depth **is** the space. Recursive reverse of a string of length n uses O(n) stack even if no
other data is allocated. Java's default stack overflows at roughly 10⁴–10⁵ frames.
