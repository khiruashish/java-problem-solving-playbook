# Word search

<span class="badge hard">Hard</span> <span class="badge tag">Grid backtracking</span>

## Problem
Does a word exist in a letter grid as a path of up/down/left/right neighbours, with each cell used at most once?
```
A B C E
S F C S      "ABCCED" ✅   "SEE" ✅   "ABCB" ❌ (would reuse B)
A D E E
```

## Thinking process
<div class="steps" markdown>

1. Try every cell as the start.
2. From a cell matching `word[k]`, look for `word[k+1]` in the four neighbours. That's DFS.
3. **"Each cell once"** means marking cells on the **current path**. Temporarily overwrite the cell with `'#'`, and
   **restore it when backtracking**, even on success, so the caller's grid is left intact.
4. **Prunes**: the grid has fewer cells than the word is long → false. A mismatched character stops immediately.
   (Another common trick: if the last letter is rarer than the first, search the reversed word.)

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/WordSearch.java"
```

## Complexity
O(R·C · 3^L): there are 4 directions at the first step and 3 after that, since you can't step back. O(L) recursion depth.

## Next level
Searching for **many** words at once is [Word Search II](../16-tries-advanced/word-search-ii.md), which uses a trie to share prefixes.
The tests check that this single-word version agrees with Word Search II on 200 random grids.
