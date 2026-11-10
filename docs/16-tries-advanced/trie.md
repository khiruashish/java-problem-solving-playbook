# Implement a trie (prefix tree)

<span class="badge medium">Medium</span> <span class="badge tag">Trie</span> <span class="badge tag">Design</span>

## Problem
Support `insert(word)`, `search(word)`, `startsWith(prefix)`, `countWordsWithPrefix(prefix)` and `delete(word)` over lowercase words.

```
insert: apple, app, apt
            (root)
              a
              p
            /   \
          p*     t*        * = isWord
          l
          e*
```

## Thinking process
<div class="steps" markdown>

1. A HashSet answers `search` in O(L), but `startsWith` would need to scan every word.
2. **A trie shares prefixes**: each node is a character position, and its children are the possible next characters (`Node[26]`).
   Walking a prefix takes O(L), no matter how many words are stored.
3. **`isWord` flag**: `"app"` is a word only if a word **ends** there, not merely because `"apple"` passes through it.
4. **`prefixCount`** per node (the number of words in its subtree) makes `countWordsWithPrefix` O(L), and makes `delete` easy:
   decrement the counts along the path, and when a count hits 0, cut off that branch.

</div>

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/trie.html"

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/Trie.java"
```

## Complexity
| Operation | Time | Space |
|-----------|------|-------|
| insert / search / startsWith / delete | O(L) | insert adds ≤ L nodes |
| total memory | | O(total characters × 26) references |

## Design trade-offs (the senior discussion)
- `Node[26]` is fast but uses a lot of memory. A `HashMap<Character, Node>` per node saves memory for sparse or Unicode alphabets.
- A **compressed trie** (radix tree) merges single-child chains, which is how routers store IP prefixes.
- `delete` with counters avoids a second recursive pruning pass.

## Testing note
3,000 random insert/delete/query operations, checked against a `HashSet` with stream-based prefix counting.
