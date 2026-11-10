"""One function per animated problem. Each runs the algorithm for real, records frames, and asserts the answer
matches the expected output (the same example as the page). Code lines mirror the Java solution in src/main."""
from tracelib import Trace, c

TRACES = {}


def trace(name, page):
    def register(fn):
        TRACES[name] = (fn, page)
        return fn
    return register


# --------------------------------------------------------------------------------------------- arrays / strings
@trace("kadane", "02-arrays/max-subarray-sum.md")
def kadane():
    a = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
    t = Trace("Kadane on [−2, 1, −3, 4, −1, 2, 1, −5, 4]", [
        "int current = a[0], best = a[0];",
        "for (int i = 1; i < a.length; i++) {",
        "    current = Math.max(a[i], current + a[i]);  // extend or restart",
        "    best = Math.max(best, current);",
        "}",
        "return best;"])
    t.array("a", a, "a")
    t.vars("v")
    current = best = a[0]
    start, best_range = 0, (0, 0)
    t.frame("int current", f"Start with the first element: {c('current')} = {c('best')} = −2. "
            "The highlighted window is the best subarray <em>ending here</em>.",
            a={"ptrs": {"i": 0}, "win": [0, 0], "marks": {0: "best"}},
            v={"items": [["current", current], ["best", best]]})
    for i in range(1, len(a)):
        extend = current + a[i]
        if a[i] > extend:
            current, start, why = a[i], i, f"{c(a[i])} beats {c(f'{extend}')} → <strong>restart</strong> here"
        else:
            current, why = extend, f"extend: {c(f'current + a[i] = {extend}')}"
        improved = current > best
        if improved:
            best, best_range = current, (start, i)
        marks = {k: "best" for k in range(best_range[0], best_range[1] + 1)}
        t.frame("best = Math" if improved else "current = Math",
                f"i = {i}: {why}. " + (f"New <strong>best = {best}</strong>." if improved else f"best stays {best}."),
                a={"ptrs": {"i": i}, "win": [start, i], "marks": marks},
                v={"items": [["current", current], ["best", best]]})
    assert best == 6 and best_range == (3, 6)
    t.frame("return best", "Done. The best subarray is [4, −1, 2, 1] (indices 3–6), sum <strong>6</strong>. "
            "One pass, O(n) time, O(1) space.",
            a={"ptrs": {}, "win": None, "marks": {k: "ok" for k in range(3, 7)}})
    return t


@trace("binary-search", "09-searching-sorting/binary-search.md")
def binary_search():
    a, target = [-5, -1, 0, 3, 8, 13, 21], 8
    t = Trace("Binary search for 8 in [−5, −1, 0, 3, 8, 13, 21]", [
        "int lo = 0, hi = a.length - 1;",
        "while (lo <= hi) {",
        "    int mid = lo + (hi - lo) / 2;",
        "    if (a[mid] == target) return mid;",
        "    if (a[mid] < target) lo = mid + 1;",
        "    else hi = mid - 1;",
        "}",
        "return -1;"])
    t.array("a", a, "a (sorted)")
    t.vars("v")
    lo, hi = 0, len(a) - 1
    dim = set()

    def marks(extra=None):
        m = {k: "dim" for k in dim}
        m.update(extra or {})
        return m

    t.frame("int lo", f"The target can be anywhere in [lo, hi] = [0, 6]. target = {c(target)}.",
            a={"ptrs": {"lo": lo, "hi": hi}, "marks": marks()}, v={"items": [["lo", lo], ["hi", hi], ["target", target]]})
    found = -1
    while lo <= hi:
        mid = lo + (hi - lo) // 2
        t.frame("int mid", f"mid = {lo} + ({hi} − {lo}) / 2 = <strong>{mid}</strong>, a[mid] = {a[mid]}.",
                a={"ptrs": {"lo": lo, "mid": mid, "hi": hi}, "marks": marks({mid: "hl"})},
                v={"items": [["lo", lo], ["mid", mid], ["hi", hi], ["target", target]]})
        if a[mid] == target:
            found = mid
            t.frame("return mid", f"a[{mid}] = {target}: <strong>found at index {mid}</strong> after 3 probes instead of 5.",
                    a={"marks": marks({mid: "ok"})})
            break
        if a[mid] < target:
            dim.update(range(lo, mid + 1))
            lo = mid + 1
            t.frame("lo = mid + 1", f"{a[mid]} &lt; {target}, so the target is strictly right of mid: discard the left half, "
                    f"lo = {lo}.", a={"ptrs": {"lo": lo, "hi": hi}, "marks": marks()},
                    v={"items": [["lo", lo], ["hi", hi], ["target", target]]})
        else:
            dim.update(range(mid, hi + 1))
            hi = mid - 1
            t.frame("else hi", f"{a[mid]} &gt; {target}, so the target is left of mid: discard the right half, hi = {hi}.",
                    a={"ptrs": {"lo": lo, "hi": hi}, "marks": marks()},
                    v={"items": [["lo", lo], ["hi", hi], ["target", target]]})
    assert found == 4
    return t


@trace("longest-substring", "01-strings/longest-substring-without-repeating.md")
def longest_substring():
    s = "abba"
    t = Trace('Longest substring without repeating characters: "abba"', [
        "int best = 0, left = 0;",
        "for (int right = 0; right < s.length(); right++) {",
        "    char c = s.charAt(right);",
        "    left = Math.max(left, lastSeen[c]);   // never move left backwards",
        "    best = Math.max(best, right - left + 1);",
        "    lastSeen[c] = right + 1;",
        "}",
        "return best;"])
    t.array("s", list(s), "s")
    t.map("seen", "lastSeen (index + 1)")
    t.vars("v")
    last, best, left = {}, 0, 0
    t.frame("int best", "Window [left, right] holds distinct characters. lastSeen stores index + 1, so 0 means "
            "\"never seen\".", s={"ptrs": {"left": 0}}, v={"items": [["left", 0], ["best", 0]]})
    for right, ch in enumerate(s):
        prev = last.get(ch, 0)
        new_left = max(left, prev)
        if prev and new_left == prev and prev > left:
            why = f"{c(ch)} was last at index {prev - 1}, inside the window: <strong>jump left to {prev}</strong>."
        elif prev:
            why = (f"{c(ch)} was last at index {prev - 1}, but that is <em>before</em> left ({left}). "
                   f"{c(f'max(left, {prev})')} keeps left at {left}. Without max, left would move backwards: the classic bug.")
        else:
            why = f"{c(ch)} not seen before; left stays {left}."
        left = new_left
        best = max(best, right - left + 1)
        last[ch] = right + 1
        t.frame("left = Math.max", f"right = {right}: {why} Window = \"{s[left:right + 1]}\", best = {best}.",
                s={"ptrs": {"left": left, "right": right}, "win": [left, right], "marks": {right: "hl"}},
                seen={"entries": [[k, v] for k, v in last.items()], "marks": {ch: "hl"}},
                v={"items": [["left", left], ["right", right], ["best", best]]})
    assert best == 2
    t.frame("return best", "Done: the longest window had length <strong>2</strong> (\"ab\" or \"ba\"). Each character "
            "is visited once: O(n).", s={"ptrs": {}, "marks": {}, "win": None}, seen={"marks": {}})
    return t


@trace("palindrome", "01-strings/palindrome.md")
def palindrome():
    s = "abcba"
    t = Trace('Two pointers: is "abcba" a palindrome?', [
        "int left = 0, right = s.length() - 1;",
        "while (left < right) {",
        "    if (s.charAt(left++) != s.charAt(right--)) return false;",
        "}",
        "return true;"])
    t.array("s", list(s), "s")
    left, right = 0, len(s) - 1
    done = {}
    t.frame("int left", "Start at both ends and walk inwards.", s={"ptrs": {"left": left, "right": right}})
    while left < right:
        ok = s[left] == s[right]
        assert ok
        done.update({left: "ok", right: "ok"})
        t.frame("if (s.charAt", f"s[{left}] = {c(s[left])} and s[{right}] = {c(s[right])} match. Move both pointers inwards.",
                s={"ptrs": {"left": left, "right": right}, "marks": dict(done)})
        left, right = left + 1, right - 1
    t.frame("return true", f"left ({left}) is no longer &lt; right ({right}): the middle character needs no partner. "
            "<strong>true</strong> after n/2 comparisons, O(1) space.", s={"ptrs": {"left": left, "right": right},
                                                                          "marks": {k: "ok" for k in range(len(s))}})
    return t


@trace("two-sum", "02-arrays/two-sum.md")
def two_sum():
    a, target = [2, 7, 11, 15], 26
    t = Trace("Two Sum with a hash map: [2, 7, 11, 15], target 26", [
        "Map<Integer, Integer> seen = new HashMap<>();",
        "for (int i = 0; i < a.length; i++) {",
        "    Integer j = seen.get(target - a[i]);",
        "    if (j != null) return new int[]{j, i};",
        "    seen.put(a[i], i);   // insert AFTER lookup so we don't pair with self",
        "}",
        "return new int[0];"])
    t.array("a", a, "a")
    t.map("seen", "seen (value → index)")
    t.vars("v")
    seen = {}
    t.frame("Map<Integer", "One pass. For each number, ask the map: have I already seen its <em>complement</em>?",
            v={"items": [["target", target]]})
    result = None
    for i, x in enumerate(a):
        need = target - x
        if need in seen:
            result = [seen[need], i]
            t.frame("if (j != null)", f"i = {i}: need {target} − {x} = {c(need)}. It's in the map at index {seen[need]}: "
                    f"<strong>answer [{seen[need]}, {i}]</strong>.",
                    a={"ptrs": {"i": i}, "marks": {seen[need]: "ok", i: "ok"}},
                    seen={"marks": {need: "ok"}}, v={"items": [["target", target], ["need", need]]})
            break
        t.frame("Integer j", f"i = {i}: need {target} − {x} = {c(need)}. Not in the map yet.",
                a={"ptrs": {"i": i}, "marks": {i: "hl"}}, seen={"marks": {}},
                v={"items": [["target", target], ["need", need]]})
        seen[x] = i
        t.frame("seen.put", f"Remember {c(f'{x} → {i}')} for later numbers. Putting it <em>after</em> the lookup stops "
                "an element pairing with itself.", seen={"entries": [[k, v] for k, v in seen.items()], "marks": {x: "hl"}})
    assert result == [2, 3]
    return t


# --------------------------------------------------------------------------------------------------- linked list
@trace("reverse-linked-list", "06-linked-list/reverse-linked-list.md")
def reverse_list():
    vals = [1, 2, 3, 4]
    nxt = [1, 2, 3, None]
    t = Trace("Reverse 1 → 2 → 3 → 4 with three pointers", [
        "ListNode prev = null, curr = head;",
        "while (curr != null) {",
        "    ListNode next = curr.next;   // 1. save the rest",
        "    curr.next = prev;            // 2. flip the arrow",
        "    prev = curr;                 // 3. advance prev",
        "    curr = next;                 // 4. advance curr",
        "}",
        "return prev;"])
    t.list("L", vals, nxt, "nodes and their next arrows")
    prev, curr = None, 0
    flipped = set()

    def ptrs(**kw):
        return {k: v for k, v in kw.items()}

    t.frame("ListNode prev", "prev = null (nothing reversed yet), curr = head.",
            L={"ptrs": ptrs(prev=None, curr=curr, next=None)})
    while curr is not None:
        nx = nxt[curr]
        t.frame("ListNode next", f"Save {c('next = curr.next')} (node {vals[nx] if nx is not None else 'null'}) "
                "before we overwrite the arrow, or the rest of the list is lost.",
                L={"ptrs": ptrs(prev=prev, curr=curr, next=nx), "marks": {curr: "hl", **{k: "done" for k in flipped}}})
        nxt[curr] = prev
        flipped.add(curr)
        t.frame("curr.next = prev", f"Flip: node {vals[curr]} now points "
                f"{'to node ' + str(vals[prev]) if prev is not None else 'to null'}.",
                L={"next": list(nxt), "marks": {curr: "hl", **{k: "done" for k in flipped if k != curr}}})
        prev, curr = curr, nx
        t.frame("curr = next", f"Advance both: prev = node {vals[prev]}, curr = "
                f"{'node ' + str(vals[curr]) if curr is not None else 'null'}.",
                L={"ptrs": ptrs(prev=prev, curr=curr, next=None), "marks": {k: "done" for k in flipped}})
    assert nxt == [None, 0, 1, 2] and prev == 3
    t.frame("return prev", "curr is null, so every arrow is flipped. <strong>prev</strong> (node 4) is the new head: "
            "4 → 3 → 2 → 1. O(n) time, O(1) space.",
            L={"ptrs": ptrs(head=prev), "marks": {k: "ok" for k in range(4)}})
    return t


@trace("linked-list-cycle", "06-linked-list/linked-list-cycle.md")
def cycle():
    vals = [1, 2, 3, 4, 5, 6, 7]
    nxt = [1, 2, 3, 4, 5, 6, 2]                  # 7 → 3: the cycle starts at node 3
    t = Trace("Floyd's tortoise and hare on a list whose tail loops back to node 3", [
        "ListNode slow = head, fast = head;",
        "while (fast != null && fast.next != null) {",
        "    slow = slow.next;",
        "    fast = fast.next.next;",
        "    if (slow == fast) {",
        "        ListNode p = head;",
        "        while (p != slow) { p = p.next; slow = slow.next; }",
        "        return p;   // cycle start",
        "    }",
        "}",
        "return null;"])
    t.list("L", vals, nxt, "list (node 7 points back to node 3)")
    slow = fast = 0
    t.frame("ListNode slow", "Both pointers start at the head. slow moves 1 step, fast moves 2.",
            L={"ptrs": {"slow": slow, "fast": fast}})
    while True:
        slow, fast = nxt[slow], nxt[nxt[fast]]
        meet = slow == fast
        t.frame("if (slow == fast)" if meet else "fast = fast.next.next",
                f"slow → node {vals[slow]}, fast → node {vals[fast]}." +
                (" <strong>They meet</strong>: inside a loop the faster runner must land on the slower one, "
                 "because the gap shrinks by exactly 1 each round." if meet else
                 " Once both are inside the loop, fast gains one step per round."),
                L={"ptrs": {"slow": slow, "fast": fast}, "marks": {slow: "best"} if meet else {}})
        if meet:
            break
    meet_at = slow
    p = 0
    t.frame("ListNode p", f"Phase 2: put {c('p')} at the head and keep slow at the meeting point (node {vals[meet_at]}). "
            "Both now move 1 step at a time.", L={"ptrs": {"p": p, "slow": slow}, "marks": {}})
    while p != slow:
        p, slow = nxt[p], nxt[slow]
        t.frame("while (p != slow)", f"p → node {vals[p]}, slow → node {vals[slow]}." +
                (" Same node!" if p == slow else ""), L={"ptrs": {"p": p, "slow": slow}})
    assert vals[p] == 3
    t.frame("return p", "They meet exactly at the <strong>start of the cycle, node 3</strong>: the distance from the head "
            "equals the distance from the meeting point, modulo the loop length. O(n) time, O(1) space.",
            L={"ptrs": {"p": p}, "marks": {p: "ok"}})
    return t


# ------------------------------------------------------------------------------------------------- stack / sorting
@trace("next-greater-element", "07-stack-queue/next-greater-element.md")
def next_greater():
    a = [4, 5, 2, 25, 7, 8]
    t = Trace("Next greater element with a monotonic stack (right to left)", [
        "for (int i = a.length - 1; i >= 0; i--) {",
        "    while (!stack.isEmpty() && stack.peek() <= a[i]) stack.pop();",
        "    out[i] = stack.isEmpty() ? -1 : stack.peek();",
        "    stack.push(a[i]);",
        "}"])
    t.array("a", a, "a")
    t.array("out", ["" for _ in a], "out")
    t.stack("st", "stack (values, top at the top)")
    out, stack = [""] * len(a), []
    t.frame("for (int i", "Walk from the right. The stack holds candidates in <em>decreasing</em> order: anything smaller "
            "than a newcomer can never be anyone's answer again.")
    for i in range(len(a) - 1, -1, -1):
        popped = []
        while stack and stack[-1] <= a[i]:
            popped.append(stack.pop())
        if popped:
            t.frame("stack.pop()", f"i = {i}, a[i] = {a[i]}: pop {', '.join(map(str, popped))} (≤ {a[i]}). "
                    f"{a[i]} is closer and at least as big, so they're useless from here on.",
                    a={"ptrs": {"i": i}, "marks": {i: "hl"}}, st={"items": list(stack)})
        out[i] = stack[-1] if stack else -1
        t.frame("out[i] =", f"i = {i}: the top of the stack is the next greater element: "
                f"<strong>out[{i}] = {out[i]}</strong>." if out[i] != -1 else
                f"i = {i}: the stack is empty, so nothing to the right is bigger: <strong>out[{i}] = −1</strong>.",
                a={"ptrs": {"i": i}, "marks": {i: "hl"}}, out={"values": list(out), "marks": {i: "ok"}},
                st={"items": list(stack), "marks": {len(stack) - 1: "ok"} if stack else {}})
        stack.append(a[i])
        t.frame("stack.push", f"Push {a[i]}: it's a candidate for everything further left.",
                st={"items": list(stack), "marks": {}}, out={"marks": {}})
    assert out == [5, 25, 25, -1, 8, -1]
    t.frame(4, "Done: [5, 25, 25, −1, 8, −1]. Every value is pushed once and popped at most once: O(n).",
            a={"ptrs": {}, "marks": {}}, out={"marks": {k: "ok" for k in range(len(a))}})
    return t


@trace("sort-colors", "09-searching-sorting/sort-colors.md")
def sort_colors():
    a = [2, 0, 2, 1, 1, 0]
    t = Trace("Dutch national flag: sort [2, 0, 2, 1, 1, 0] in one pass", [
        "int low = 0, mid = 0, high = a.length - 1;",
        "while (mid <= high) {",
        "    switch (a[mid]) {",
        "        case 0 -> swap(a, low++, mid++);",
        "        case 1 -> mid++;",
        "        default -> swap(a, mid, high--);   // don't advance mid",
        "    }",
        "}"])
    t.array("a", a, "a")
    low, mid, high = 0, 0, len(a) - 1

    def zones(extra=None):
        m = {k: "done" for k in list(range(0, low)) + list(range(high + 1, len(a)))}
        m.update(extra or {})
        return m

    t.frame("int low", "Regions: [0, low) are 0s · [low, mid) are 1s · [mid, high] unknown · (high, end] are 2s.",
            a={"ptrs": {"low": low, "mid": mid, "high": high}})
    while mid <= high:
        v = a[mid]
        if v == 0:
            a[low], a[mid] = a[mid], a[low]
            sw = {low: "swap", mid: "swap"}
            low, mid = low + 1, mid + 1
            line, why = "case 0", f"a[mid] = 0: swap it to low, then advance <strong>both</strong> (what came from low is a known 1, or the same cell)."
        elif v == 1:
            sw = {mid: "hl"}
            mid += 1
            line, why = "case 1", "a[mid] = 1: already in the middle zone, just advance mid."
        else:
            a[mid], a[high] = a[high], a[mid]
            sw = {mid: "swap", high: "swap"}
            high -= 1
            line, why = "default", "a[mid] = 2: swap it to high and shrink high. <strong>Don't advance mid</strong>: the value that came back is still unknown."
        t.frame(line, why, a={"values": list(a), "ptrs": {"low": low, "mid": mid, "high": high}, "marks": zones(sw)})
    assert a == [0, 0, 1, 1, 2, 2]
    t.frame("while (mid", f"mid ({mid}) &gt; high ({high}): nothing unknown is left. Sorted in one pass, O(1) extra space.",
            a={"marks": {k: "ok" for k in range(len(a))}})
    return t


# ------------------------------------------------------------------------------------------------------------- DP
@trace("lcs", "13-dynamic-programming/longest-common-subsequence.md")
def lcs():
    x, y = "abcde", "ace"
    m, n = len(x), len(y)
    t = Trace('LCS table for "abcde" and "ace"', [
        "int[][] dp = new int[m + 1][n + 1];",
        "for (int i = 1; i <= m; i++)",
        "    for (int j = 1; j <= n; j++)",
        "        dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1)",
        "                ? dp[i - 1][j - 1] + 1",
        "                : Math.max(dp[i - 1][j], dp[i][j - 1]);"])
    dp = [[0] * (n + 1) for _ in range(m + 1)]
    show = [[0 if i == 0 or j == 0 else "" for j in range(n + 1)] for i in range(m + 1)]
    t.grid("dp", m + 1, n + 1, show, ["∅"] + list(x), ["∅"] + list(y), "dp[i][j] = LCS of a[0..i) and b[0..j)")
    t.frame("int[][] dp", "Row 0 and column 0 are 0: an empty prefix has nothing in common with anything.")
    for i in range(1, m + 1):
        for j in range(1, n + 1):
            if x[i - 1] == y[j - 1]:
                dp[i][j] = dp[i - 1][j - 1] + 1
                src, line = {f"{i - 1},{j - 1}": "best"}, "? dp"
                why = f"{c(x[i - 1])} = {c(y[j - 1])}: take the diagonal + 1 → <strong>{dp[i][j]}</strong>."
            else:
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
                src = {f"{i - 1},{j}": "win", f"{i},{j - 1}": "win"}
                line, why = ": Math.max", (f"{c(x[i - 1])} ≠ {c(y[j - 1])}: drop one character from either string, "
                                          f"max(up {dp[i - 1][j]}, left {dp[i][j - 1]}) = {dp[i][j]}.")
            show[i][j] = dp[i][j]
            t.frame(line, f"dp[{i}][{j}]: {why}", dp={"values": [r[:] for r in show], "marks": {f"{i},{j}": "hl", **src}})
    assert dp[m][n] == 3
    path, i, j, seq = {}, m, n, []
    while i > 0 and j > 0:
        if x[i - 1] == y[j - 1]:
            path[f"{i},{j}"] = "ok"
            seq.append(x[i - 1])
            i, j = i - 1, j - 1
        elif dp[i - 1][j] >= dp[i][j - 1]:
            path[f"{i},{j}"] = "done"
            i -= 1
        else:
            path[f"{i},{j}"] = "done"
            j -= 1
    assert "".join(reversed(seq)) == "ace"
    t.frame(5, "Answer: dp[5][3] = <strong>3</strong>. Walking back from the corner (diagonal on a match) spells the LCS "
            "<strong>\"ace\"</strong>. O(m·n) time and space.", dp={"marks": path})
    return t


@trace("coin-change", "13-dynamic-programming/coin-change.md")
def coin_change():
    coins, amount = [1, 2, 5], 11
    t = Trace("Fewest coins for 11 using [1, 2, 5], bottom-up", [
        "int[] dp = new int[amount + 1];",
        "Arrays.fill(dp, amount + 1);   // \"infinity\"",
        "dp[0] = 0;",
        "for (int a = 1; a <= amount; a++)",
        "    for (int c : coins) if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);",
        "return dp[amount] > amount ? -1 : dp[amount];"])
    inf = amount + 1
    dp = [inf] * (amount + 1)
    dp[0] = 0
    show = lambda: ["∞" if v == inf else v for v in dp]  # noqa: E731
    t.array("dp", show(), "dp[a] = fewest coins for amount a")
    t.frame("dp[0] = 0", "dp[0] = 0 (no coins for amount 0). Everything else starts at ∞ (amount + 1, which no real "
            "answer can reach).", dp={"values": show(), "marks": {0: "ok"}})
    for a in range(1, amount + 1):
        best_c = None
        for coin in coins:
            if coin <= a and dp[a - coin] + 1 < dp[a]:
                dp[a], best_c = dp[a - coin] + 1, coin
        opts = ", ".join(f"dp[{a - k}]+1 = {dp[a - k] + 1}" for k in coins if k <= a)
        t.frame("for (int c", f"a = {a}: try every coin as the last one: {opts}. "
                f"Best is coin {best_c} → <strong>dp[{a}] = {dp[a]}</strong>.",
                dp={"values": show(), "ptrs": {"a": a, "a−c": a - best_c}, "marks": {a: "hl", a - best_c: "best"}})
    assert dp[amount] == 3
    t.frame("return dp", "dp[11] = <strong>3</strong> (5 + 5 + 1). O(amount × coins) time, O(amount) space.",
            dp={"ptrs": {}, "marks": {11: "ok", 6: "ok", 1: "ok", 0: "ok"}})
    return t


# ----------------------------------------------------------------------------------------------- graphs / recursion
@trace("number-of-islands", "12-graphs/number-of-islands.md")
def islands():
    g = ["11000", "11000", "00100", "00011"]
    rows, cols = len(g), len(g[0])
    t = Trace("Number of islands: DFS flood fill", [
        "for (int r = 0; r < g.length; r++)",
        "    for (int c = 0; c < g[0].length; c++)",
        "        if (g[r][c] == '1') { count++; sink(g, r, c); }",
        "",
        "void sink(char[][] g, int r, int c) {",
        "    if (out of bounds || g[r][c] != '1') return;",
        "    g[r][c] = '0';   // mark visited",
        "    for (int[] d : DIRS) sink(g, r + d[0], c + d[1]);",
        "}"])
    grid = [list(row) for row in g]
    t.grid("g", rows, cols, [r[:] for r in grid], [str(r) for r in range(rows)], [str(k) for k in range(cols)], "grid")
    t.stack("calls", "call stack")
    t.vars("v")
    colours = ["ok", "swap", "win"]
    marks, count, calls = {}, 0, []
    t.frame("for (int r", "Scan cell by cell. Each '1' that hasn't been visited starts a new island.",
            v={"items": [["count", 0]]})

    def sink(r, k, isl):
        if r < 0 or k < 0 or r >= rows or k >= cols or grid[r][k] != "1":
            return
        grid[r][k] = "0"
        marks[f"{r},{k}"] = colours[isl]
        calls.append(f"sink({r},{k})")
        t.frame("g[r][c] = '0'", f"Visit ({r},{k}): mark it so it's never counted again, then try its 4 neighbours.",
                g={"values": [row[:] for row in grid], "marks": {**marks, f"{r},{k}": colours[isl] + " hl"}},
                calls={"items": list(calls)})
        for dr, dk in ((1, 0), (-1, 0), (0, 1), (0, -1)):
            sink(r + dr, k + dk, isl)
        calls.pop()

    for r in range(rows):
        for k in range(cols):
            if grid[r][k] == "1":
                count += 1
                t.frame("count++", f"({r},{k}) is unvisited land: <strong>island #{count}</strong>. Sink all of it.",
                        g={"marks": {**marks, f"{r},{k}": "hl"}}, v={"items": [["count", count]]})
                sink(r, k, count - 1)
    assert count == 3
    t.frame(0, "Every cell scanned: <strong>3 islands</strong>. Each cell is visited a constant number of times: O(R·C). "
            "(The real code sinks a copy, so the caller's grid is untouched.)",
            g={"marks": marks}, calls={"items": []})
    return t


@trace("fibonacci-memo", "04-recursion/fibonacci.md")
def fib_memo():
    n = 5
    t = Trace("fib(5) with memoization: the call stack and the cache", [
        "long memo(int n, Map<Integer, Long> cache) {",
        "    if (n < 2) return n;",
        "    Long hit = cache.get(n);",
        "    if (hit != null) return hit;",
        "    long v = memo(n - 1, cache) + memo(n - 2, cache);",
        "    cache.put(n, v);",
        "    return v;",
        "}"])
    t.stack("calls", "call stack")
    t.map("cache", "cache (n → F(n))")
    cache, calls = {}, []

    def entries():
        return [[k, cache[k]] for k in sorted(cache)]

    def memo(k):
        calls.append(f"memo({k})")
        if k < 2:
            t.frame("if (n < 2)", f"memo({k}): base case, return {k}.", calls={"items": list(calls), "marks": {}},
                    cache={"entries": entries(), "marks": {}})
            calls.pop()
            return k
        if k in cache:
            t.frame("if (hit != null)", f"memo({k}): <strong>cache hit</strong>, return {cache[k]} without recursing. "
                    "This is the whole speed-up.", calls={"items": list(calls), "marks": {len(calls) - 1: "ok"}},
                    cache={"entries": entries(), "marks": {str(k): "ok"}})
            calls.pop()
            return cache[k]
        t.frame("long v", f"memo({k}): not cached, so compute memo({k - 1}) + memo({k - 2}).",
                calls={"items": list(calls), "marks": {}}, cache={"entries": entries(), "marks": {}})
        v = memo(k - 1) + memo(k - 2)
        cache[k] = v
        t.frame("cache.put", f"memo({k}) = {v}. Store it, so any later memo({k}) is free.",
                calls={"items": list(calls), "marks": {}}, cache={"entries": entries(), "marks": {str(k): "hl"}})
        calls.pop()
        return v

    result = memo(n)
    assert result == 5
    t.frame("return v", "fib(5) = <strong>5</strong>. Each n is computed once: O(n) calls instead of the naive O(2ⁿ).",
            calls={"items": []}, cache={"marks": {}})
    return t
