# Word search II

<span class="badge hard">Hard</span> <span class="badge tag">Trie</span> <span class="badge tag">Backtracking</span>

## Problem
Given a letter board and a list of words, return every word that can be traced through adjacent cells (up/down/left/right),
using each cell at most once per word.
```
o a a n
e t a e        words = [oath, pea, eat, rain]  →  [eat, oath]
i h k r
i f l v
```

## Thinking process
<div class="steps" markdown>

1. **Per-word backtracking**: for each word, start a DFS from every cell. This repeats the same exploration for words sharing a
   prefix ("oath", "oat", "oats"…), so it costs O(W · R·C · 4^L).
2. **Flip it**: run **one** DFS from each cell and walk the **trie at the same time**. The path is still worth extending only if
   it's a prefix of some word, which the trie answers with one child lookup.
3. When you reach a trie node holding a word, report it and set it to null so it's reported once.
4. **Prune exhausted branches**: if a trie node has no children and no word left, delete it from its parent. Later searches
   stop even earlier. This is the optimisation that makes the hardest test cases pass.
5. **Backtracking**: mark the cell `'#'` while it's on the path, and restore it afterwards.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/WordSearchII.java"
```

## Complexity
Per word: O(W · R·C · 4^L). Trie: O(R·C · 4^L) in the worst case, but shared prefixes and pruning make it far faster in practice.

## Testing note
200 random boards (up to 4×4) with random word lists over a 3-letter alphabet, which forces many shared prefixes.
Both methods must find the same words.
