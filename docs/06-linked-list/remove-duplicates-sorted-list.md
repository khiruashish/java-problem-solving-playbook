# Remove duplicates from a sorted list (I & II)

<span class="badge easy">Easy</span> <span class="badge tag">Pointer skipping</span> <span class="badge tag">Dummy node</span>

## Problem
Given a **sorted** linked list:

- **I**: delete duplicates so each value appears **once**. `1→1→2→3→3` ⇒ `1→2→3`.
- **II** (follow-up): delete **every** node whose value repeats, keeping only distinct values. `1→2→3→3→4→4→5` ⇒ `1→2→5`.

## Examples
| Input | I | II |
|-------|---|----|
| `1→1→2` | `1→2` | `2` |
| `1→1→1→2→3` | `1→2→3` | `2→3` |
| `1→1` | `1` | empty |
| empty | empty | empty |

## Clarifying questions
- Is the list sorted? That is what makes O(1) space possible. Unsorted lists need a hash set.
- Can I relink nodes, or must I build a new list?

## Thinking process
<div class="steps" markdown>

1. **Sorted means duplicates are adjacent.** You only ever compare a node with its `next`.
2. **I**: if `cur.next.val == cur.val`, unlink `cur.next` and **stay** on `cur` (there may be a third copy). Otherwise move on.
3. **II**: now the *first* copy must go too, and it may be the head. A **dummy node** in front removes that special case.
   `prev` is the last node known to be unique. If a run starts at `prev.next`, skip the whole run and attach `prev.next` to what follows.
   Don't advance `prev` yet, since the next node might start another run.
4. **Recursive I**: dedupe the tail, then drop `head` if it equals the tail's new head.

</div>

## Dry run (II): `1→2→3→3→4→4→5`
| prev | cur | run? | action |
|------|-----|------|--------|
| dummy | 1 | no | prev = 1 |
| 1 | 2 | no | prev = 2 |
| 2 | 3 | yes (3, 3) | prev.next = 4 |
| 2 | 4 | yes (4, 4) | prev.next = 5 |
| 2 | 5 | no | prev = 5 |

Result: `1→2→5`.

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/RemoveDuplicatesSortedList.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| I iterative | O(n) | O(1) |
| I recursive | O(n) | O(n) stack |
| I with a set (unsorted lists too) | O(n) | O(n) |
| II with a dummy node | O(n) | O(1) |

## Edge cases & pitfalls
- In I, advancing `cur` after an unlink misses triples (`1→1→1`).
- In II, without the dummy node, a run at the head (`1→1→2`) needs separate code.
- In II, advancing `prev` after skipping a run can keep the start of an immediately following run.

## Related
[Remove duplicates from a sorted array](../02-arrays/remove-duplicates-sorted.md) · [Remove Nth from end](remove-nth-from-end.md) (another dummy-node problem).
