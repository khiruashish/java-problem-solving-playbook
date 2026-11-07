# Longest common prefix via a trie

<span class="badge medium">Medium</span> <span class="badge tag">Trie</span>

## Problem
The [longest common prefix](../01-strings/longest-common-prefix.md) of an array of words, solved with a trie.
`["flower","flow","flight"]` → `"fl"`.

## Thinking process
<div class="steps" markdown>

1. Insert every word.
2. Walk down from the root **while the current node has exactly one child and no word ends there**. Every character on
   that path is shared by all words.
3. Stop at a **branch** (the words diverge) or a **word end** (a shorter word is itself the prefix: `["ab","abc"]` → `"ab"`).
4. **When is this better than scanning?** For a single query, the vertical scan from the Strings topic is simpler. The trie
   wins when the word set stays fixed and you answer **many** prefix questions.

</div>

## Code
The method `longestCommonPrefix` lives in the [Trie](trie.md) class:
```java
--8<-- "src/main/java/com/playbook/advanced/Trie.java"
```

## Complexity
Build O(S) for S total characters, then the walk is O(LCP length × 26).

## Testing note
Compared with the Strings topic's vertical scan on 300 random word lists.
