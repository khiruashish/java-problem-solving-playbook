# Autocomplete / search suggestions

<span class="badge hard">Hard</span> <span class="badge tag">Trie</span> <span class="badge tag">Binary search</span>

## Problem
After each character typed in `search`, return up to **3** products that start with the typed prefix, in lexicographic order
(LeetCode 1268).
products `[mobile, mouse, moneypot, monitor, mousepad]`, search `"mouse"` →
`m: [mobile, moneypot, monitor]` … `mou: [mouse, mousepad]` …

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for every prefix, filter and sort all products.
2. **Sort once + binary search**: in sorted order, the products sharing a prefix form a **contiguous block** starting at
   `lowerBound(prefix)`. Take up to 3 from there. As the prefix grows, the block only moves right, so the next search can start
   from the previous position.
3. **Trie with cached suggestions**: insert the products in **sorted order**, and store at each node the first 3 words that pass
   through it. Each keystroke is then **one child step** plus a list copy, which is how real autocomplete backends feel instant.
4. Once the prefix leaves the trie, every following keystroke has no suggestions.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/Autocomplete.java"
```

## Complexity
| Approach | Preprocess | Per keystroke |
|----------|------------|---------------|
| Brute force | none | O(n · L + n log n) |
| Sort + binary search | O(n log n · L) | O(log n · L) |
| Trie with top-3 | O(n log n · L + total chars) | **O(1)** plus copying 3 words |

## Real-world note
Production systems rank by **popularity**, not alphabetically. Store a top-k by frequency at each node, which brings in
[heaps](../11-heap/top-k-frequent-words.md), and refresh it offline.
