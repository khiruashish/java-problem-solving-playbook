"""Second batch of traces: one per remaining topic plus more classics. Same rules as traces.py: run the real algorithm,
record frames, assert the page's expected answer."""
import heapq

from tracelib import Trace, c
from traces import trace


def rng(a, b):
    return list(range(a, b + 1))


# ============================================================================================== strings / arrays
@trace("valid-parentheses", "01-strings/valid-parentheses.md")
def valid_parentheses():
    s = "{[()]}"
    t = Trace('Valid parentheses: "{[()]}" with a stack of expected closers', [
        "Deque<Character> stack = new ArrayDeque<>();",
        "for (char c : s.toCharArray()) {",
        "    switch (c) {",
        "        case '(' -> stack.push(')');   // push what we EXPECT later",
        "        case '[' -> stack.push(']');",
        "        case '{' -> stack.push('}');",
        "        default -> { if (stack.isEmpty() || stack.pop() != c) return false; }",
        "    }",
        "}",
        "return stack.isEmpty();"])
    t.array("s", list(s), "s")
    t.stack("st", "stack (expected closers)")
    pairs = {"(": ")", "[": "]", "{": "}"}
    stack = []
    t.frame("Deque", "Trick: when we see an opener, push the <em>closer we expect</em>. Then a closer only has to "
            "equal the top of the stack.")
    for i, ch in enumerate(s):
        if ch in pairs:
            stack.append(pairs[ch])
            t.frame(f"case '{ch}'", f"{c(ch)} opens: push {c(pairs[ch])}, the closer we now expect.",
                    s={"ptrs": {"i": i}, "marks": {i: "hl"}}, st={"items": list(stack), "marks": {}})
        else:
            top = stack.pop()
            assert top == ch
            t.frame("default", f"{c(ch)} closes: pop {c(top)}, and it matches.",
                    s={"ptrs": {"i": i}, "marks": {i: "ok"}}, st={"items": list(stack)})
    assert not stack
    t.frame("return stack.isEmpty()", "End of the string and the stack is empty: <strong>valid</strong>. "
            "With \"([)]\" the pop at index 2 would give ']' instead of ')', and we'd return false right there.",
            s={"ptrs": {}, "marks": {k: "ok" for k in range(len(s))}})
    return t


@trace("kmp", "01-strings/kmp-pattern-search.md")
def kmp():
    text, pat = "AABAACAADAABAABA", "AABA"
    lps = [0] * len(pat)
    ln = 0
    for i in range(1, len(pat)):
        while ln and pat[i] != pat[ln]:
            ln = lps[ln - 1]
        if pat[i] == pat[ln]:
            ln += 1
        lps[i] = ln
    assert lps == [0, 1, 0, 1]
    t = Trace('KMP: find "AABA" in "AABAACAADAABAABA"', [
        "int[] lps = lps(pattern);   // [0, 1, 0, 1]",
        "int j = 0;                  // chars of pattern matched so far",
        "for (int i = 0; i < text.length(); i++) {",
        "    while (j > 0 && text.charAt(i) != pattern.charAt(j)) j = lps[j - 1];",
        "    if (text.charAt(i) == pattern.charAt(j)) j++;",
        "    if (j == pattern.length()) { hits.add(i - j + 1); j = lps[j - 1]; }",
        "}"])
    t.array("text", list(text), "text")
    t.array("pat", list(pat), "pattern")
    t.array("lps", lps, "lps")
    t.vars("v")
    hits, j = [], 0
    t.frame("int[] lps", "lps[k] = length of the longest proper prefix of pattern[0..k] that is also a suffix. "
            "On a mismatch it tells us how much of the match we can <em>keep</em>, so i never moves back.",
            v={"items": [["j", 0], ["hits", "[]"]]})
    for i, ch in enumerate(text):
        falls = []
        while j and ch != pat[j]:
            falls.append(j)
            j = lps[j - 1]
        matched = ch == pat[j]
        if matched:
            j += 1
        found = j == len(pat)
        if found:
            hits.append(i - j + 1)
        msg = f"i = {i} ({c(ch)}): "
        if falls:
            msg += "mismatch, fall back " + " → ".join(f"j = lps[{f - 1}] = {lps[f - 1]}" for f in falls) + ". "
        msg += f"match, j = {j}." if matched else "no match, j stays 0."
        if found:
            msg += f" <strong>Full match at {i - j + 1}</strong>; continue with j = lps[3] = {lps[j - 1]} so overlaps are found."
        start = i - j + 1
        t.frame("hits.add" if found else ("while (j > 0" if falls else "if (text.charAt(i) =="), msg,
                text={"ptrs": {"i": i}, "win": [start, i] if j else None,
                      "marks": {**{h + k: "ok" for h in hits for k in range(len(pat))}, i: "hl"}},
                pat={"ptrs": {"j": min(j, len(pat) - 1)} if j else {}, "marks": {k: "win" for k in range(j)}},
                lps={"marks": {f - 1: "hl" for f in falls}},
                v={"items": [["j", j], ["hits", str(hits)]]})
        if found:
            j = lps[j - 1]
    assert hits == [0, 9, 12]
    t.frame(6, "Hits at <strong>[0, 9, 12]</strong> (9 and 12 overlap). The text pointer only ever moved forward: O(n + m).",
            text={"ptrs": {}, "win": None, "marks": {h + k: "ok" for h in hits for k in range(4)}}, pat={"ptrs": {}, "marks": {}},
            lps={"marks": {}})
    return t


@trace("trapping-rain-water", "02-arrays/trapping-rain-water.md")
def trapping_rain_water():
    h = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
    t = Trace("Trapping rain water with two pointers", [
        "int l = 0, r = h.length - 1, leftMax = 0, rightMax = 0, water = 0;",
        "while (l < r) {",
        "    if (h[l] < h[r]) {",
        "        leftMax = Math.max(leftMax, h[l]);",
        "        water += leftMax - h[l++];",
        "    } else {",
        "        rightMax = Math.max(rightMax, h[r]);",
        "        water += rightMax - h[r--];",
        "    }",
        "}"])
    t.array("h", h, "heights", bars=0.62)
    t.vars("v")
    l, r, lm, rm, water, wat = 0, len(h) - 1, 0, 0, 0, {}
    t.frame("int l", "Water above a bar = min(tallest on its left, tallest on its right) − its height. Move the pointer "
            "with the <em>lower</em> wall: its side's max is the binding limit.",
            h={"ptrs": {"l": l, "r": r}}, v={"items": [["leftMax", 0], ["rightMax", 0], ["water", 0]]})
    while l < r:
        if h[l] < h[r]:
            lm = max(lm, h[l])
            add = lm - h[l]
            wat[l] = add
            water += add
            msg = f"h[l] = {h[l]} &lt; h[r] = {h[r]}: the right wall is taller, so leftMax ({lm}) decides. +{add}."
            line, done = "water += leftMax", l
            l += 1
        else:
            rm = max(rm, h[r])
            add = rm - h[r]
            wat[r] = add
            water += add
            msg = f"h[l] = {h[l]} ≥ h[r] = {h[r]}: rightMax ({rm}) decides for r. +{add}."
            line, done = "water += rightMax", r
            r -= 1
        t.frame(line, msg, h={"ptrs": {"l": l, "r": r}, "water": {k: v for k, v in wat.items() if v},
                              "marks": {done: "hl"}},
                v={"items": [["leftMax", lm], ["rightMax", rm], ["water", water]]})
    assert water == 6
    t.frame("while (l < r)", "The pointers met: <strong>6 units</strong> of water, one pass, O(1) space.",
            h={"ptrs": {}, "marks": {}})
    return t


@trace("move-zeros", "02-arrays/move-zeros.md")
def move_zeros():
    a = [0, 1, 0, 3, 12]
    t = Trace("Move zeros: read and write pointers", [
        "int write = 0;   // next position for a non-zero",
        "for (int read = 0; read < a.length; read++) {",
        "    if (a[read] != 0) {",
        "        int t = a[write]; a[write++] = a[read]; a[read] = t;",
        "    }",
        "}"])
    t.array("a", a, "a")
    w = 0
    t.frame("int write", "Everything before <code>write</code> is a finished non-zero, in order.",
            a={"ptrs": {"write": 0, "read": 0}})
    for rd in range(len(a)):
        if a[rd] != 0:
            a[w], a[rd] = a[rd], a[w]
            t.frame("int t = a[write]", f"a[read] = {a[w]} is non-zero: swap it to write ({w}) and advance write.",
                    a={"values": list(a), "ptrs": {"write": w + 1, "read": rd}, "marks": {**{k: "done" for k in range(w)}, w: "swap", rd: "swap"}})
            w += 1
        else:
            t.frame("if (a[read] != 0)", f"a[{rd}] is 0: skip it. write stays at {w}.",
                    a={"ptrs": {"write": w, "read": rd}, "marks": {**{k: "done" for k in range(w)}, rd: "dim"}})
    assert a == [1, 3, 12, 0, 0]
    t.frame(5, "Non-zeros in order, zeros at the end, one pass and minimal writes.",
            a={"ptrs": {}, "marks": {k: "ok" for k in range(3)}})
    return t


@trace("buy-sell-stock", "02-arrays/buy-sell-stock.md")
def buy_sell():
    p = [7, 1, 5, 3, 6, 4]
    t = Trace("Best time to buy and sell: track the cheapest day so far", [
        "int minSoFar = Integer.MAX_VALUE, best = 0;",
        "for (int p : prices) {",
        "    minSoFar = Math.min(minSoFar, p);",
        "    best = Math.max(best, p - minSoFar);",
        "}",
        "return best;"])
    t.array("p", p, "prices")
    t.vars("v")
    lo, best, lo_i, buy, sell = float("inf"), 0, None, None, None
    t.frame("int minSoFar", "If we sell today, the best buy day is the cheapest day so far.",
            v={"items": [["minSoFar", "∞"], ["best", 0]]})
    for i, x in enumerate(p):
        if x < lo:
            lo, lo_i = x, i
        prof = x - lo
        if prof > best:
            best, buy, sell = prof, lo_i, i
        marks = {lo_i: "best", i: "hl"}
        if buy is not None:
            marks.update({buy: "ok", sell: "ok"} if sell == i else {})
        t.frame("best = Math.max", f"Day {i}, price {x}: cheapest so far {lo} (day {lo_i}), selling today earns {prof}." +
                (f" <strong>New best {best}</strong>." if sell == i and prof == best and prof > 0 else ""),
                p={"ptrs": {"day": i, "min": lo_i}, "marks": marks}, v={"items": [["minSoFar", lo], ["best", best]]})
    assert best == 5 and (buy, sell) == (1, 4)
    t.frame("return best", "Buy on day 1 at 1, sell on day 4 at 6: <strong>profit 5</strong>. One pass, O(1) space.",
            p={"ptrs": {}, "marks": {1: "ok", 4: "ok"}})
    return t


# ================================================================================================= logical / recursion
@trace("sieve", "03-logical/prime-numbers.md")
def sieve():
    n = 30
    t = Trace("Sieve of Eratosthenes up to 30", [
        "boolean[] composite = new boolean[n + 1];",
        "for (long i = 2; i * i <= n; i++) {",
        "    if (!composite[(int) i]) {",
        "        for (long j = i * i; j <= n; j += i) composite[(int) j] = true;",
        "    }",
        "}",
        "for (int i = 2; i <= n; i++) if (!composite[i]) primes.add(i);"])
    vals = [[r * 10 + k + 1 for k in range(10)] for r in range(3)]
    t.grid("g", 3, 10, vals, label="numbers 1..30")
    pos = lambda v: f"{(v - 1) // 10},{(v - 1) % 10}"  # noqa: E731
    comp = set()
    base = {pos(1): "dim"}
    t.frame("boolean[]", "Start by assuming every number from 2 is prime. Each prime crosses out its multiples.",
            g={"marks": dict(base)})
    i = 2
    while i * i <= n:
        if i in comp:
            t.frame("if (!composite", f"{i} is already crossed out: its multiples were crossed out by a smaller prime. Skip.",
                    g={"marks": {**base, **{pos(x): "dim" for x in comp}, pos(i): "hl"}})
        else:
            new = list(range(i * i, n + 1, i))
            comp.update(new)
            t.frame("composite[(int) j] = true", f"{i} is prime. Cross out {', '.join(map(str, new))}. Start at {i}² = {i * i}: "
                    "smaller multiples were already crossed out by smaller primes.",
                    g={"marks": {**base, **{pos(x): "dim" for x in comp}, **{pos(x): "bad" for x in new}, pos(i): "ok"}})
        i += 1
    primes = [x for x in range(2, n + 1) if x not in comp]
    assert primes == [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
    t.frame("primes.add", f"6² = 36 &gt; 30, so stop. What's left is prime: <strong>{', '.join(map(str, primes))}</strong>. "
            "O(n log log n).", g={"marks": {**base, **{pos(x): "dim" for x in comp}, **{pos(x): "ok" for x in primes}}})
    return t


@trace("tower-of-hanoi", "04-recursion/tower-of-hanoi.md")
def hanoi():
    t = Trace("Tower of Hanoi, n = 3: 7 moves", [
        "void solve(int n, char from, char to, char via) {",
        "    if (n == 0) return;",
        "    solve(n - 1, from, via, to);   // park the smaller tower on the helper",
        "    moves.add(\"disk \" + n + \": \" + from + \"->\" + to);",
        "    solve(n - 1, via, to, from);   // bring it back on top",
        "}"])
    pegs = {"A": [3, 2, 1], "B": [], "C": []}
    for p in "ABC":
        t.stack(p, f"peg {p}", inline=True, items=pegs[p])
    t.stack("calls", "call stack", inline=True)
    t.vars("v")
    calls, moves = [], []
    t.frame("void solve", "Move 3 disks from A to C. Trust the recursion: move the top 2 out of the way, move the big "
            "disk, then put the 2 back on top.", v={"items": [["moves", 0]]})

    def solve(n, fr, to, via):
        if n == 0:
            return
        calls.append(f"solve({n},{fr},{to},{via})")
        solve(n - 1, fr, via, to)
        disk = pegs[fr].pop()
        assert disk == n and (not pegs[to] or pegs[to][-1] > disk)
        pegs[to].append(disk)
        moves.append((n, fr, to))
        t.frame("moves.add", f"Move {len(moves)}: disk {n} from {fr} to {to}.",
                **{p: {"items": list(pegs[p]), "marks": {len(pegs[p]) - 1: "swap"} if p == to else {}} for p in "ABC"},
                calls={"items": list(calls)}, v={"items": [["moves", len(moves)]]})
        solve(n - 1, via, to, fr)
        calls.pop()

    solve(3, "A", "C", "B")
    assert len(moves) == 7 and pegs["C"] == [3, 2, 1]
    t.frame(0, "All 3 disks on C in <strong>7 = 2³ − 1 moves</strong>. T(n) = 2T(n−1) + 1, so n disks take 2ⁿ − 1.",
            **{p: {"marks": {}} for p in "ABC"}, calls={"items": []})
    return t


@trace("subsets", "04-recursion/subsets.md")
def subsets():
    a = [1, 2, 3]
    t = Trace("Subsets of [1, 2, 3] by backtracking", [
        "void bt(int[] a, int start, List<Integer> current, List<List<Integer>> out) {",
        "    out.add(new ArrayList<>(current));        // every node is an answer",
        "    for (int i = start; i < a.length; i++) {",
        "        current.add(a[i]);",
        "        bt(a, i + 1, current, out);",
        "        current.remove(current.size() - 1);  // backtrack",
        "    }",
        "}"])
    t.array("a", a, "a")
    t.stack("calls", "call stack", inline=True)
    t.queue("out", "out", inline=True)
    t.vars("v")
    out, cur, calls = [], [], []

    def fmt(x):
        return "[" + ",".join(map(str, x)) + "]"

    def bt(start):
        calls.append(f"bt(start={start})")
        out.append(list(cur))
        t.frame("out.add", f"current = {fmt(cur)} is a subset: record it. Then try adding each element from index {start} on.",
                a={"ptrs": {"start": start} if start < len(a) else {}, "marks": {a.index(x): "ok" for x in cur}},
                calls={"items": list(calls)}, out={"items": [fmt(x) for x in out], "marks": {len(out) - 1: "hl"}},
                v={"items": [["current", fmt(cur)]]})
        for i in range(start, len(a)):
            cur.append(a[i])
            bt(i + 1)
            cur.pop()
            if i + 1 < len(a):
                t.frame("current.remove", f"Back in bt(start={start}): undo {a[i]}, current = {fmt(cur)}, and try the next element.",
                        a={"ptrs": {"i": i + 1}, "marks": {a.index(x): "ok" for x in cur}},
                        calls={"items": list(calls)}, out={"marks": {}}, v={"items": [["current", fmt(cur)]]})
        calls.pop()

    bt(0)
    assert sorted(map(tuple, out)) == sorted([(), (1,), (2,), (3,), (1, 2), (1, 3), (2, 3), (1, 2, 3)])
    t.frame(7, "All <strong>2³ = 8</strong> subsets. Adding, recursing and removing (backtracking) reuses one list "
            "instead of copying at every step.", a={"ptrs": {}, "marks": {}}, calls={"items": []}, out={"marks": {}})
    return t


# ================================================================================================= lists / stacks
@trace("middle-node", "06-linked-list/middle-node.md")
def middle_node():
    vals = list(range(1, 7))
    t = Trace("Middle of 1 → 2 → 3 → 4 → 5 → 6 with slow and fast", [
        "ListNode slow = head, fast = head;",
        "while (fast != null && fast.next != null) {",
        "    slow = slow.next;",
        "    fast = fast.next.next;",
        "}",
        "return slow;"])
    t.list("L", vals, [1, 2, 3, 4, 5, None], "list")
    slow = fast = 0
    t.frame("ListNode slow", "slow moves 1 node per step, fast moves 2. When fast runs off the end, slow is halfway.",
            L={"ptrs": {"slow": 0, "fast": 0}})
    while fast is not None and fast + 1 < len(vals):
        slow += 1
        fast = fast + 2 if fast + 2 < len(vals) else None
        t.frame("fast = fast.next.next", f"slow → node {vals[slow]}, fast → " +
                (f"node {vals[fast]}." if fast is not None else "null (fell off the end)."),
                L={"ptrs": {"slow": slow, "fast": fast}})
    assert vals[slow] == 4
    t.frame("return slow", "fast is null: <strong>slow is at node 4</strong>, the second middle of an even-length list. "
            "One pass, O(1) space.", L={"ptrs": {"slow": slow}, "marks": {slow: "ok"}})
    return t


@trace("daily-temperatures", "07-stack-queue/daily-temperatures.md")
def daily_temperatures():
    tt = [73, 74, 75, 71, 69, 72, 76, 73]
    t = Trace("Daily temperatures with a monotonic stack of indexes", [
        "for (int i = 0; i < t.length; i++) {",
        "    while (!waiting.isEmpty() && t[waiting.peek()] < t[i]) {",
        "        int day = waiting.pop();",
        "        out[day] = i - day;   // today is the first warmer day for `day`",
        "    }",
        "    waiting.push(i);",
        "}"])
    t.array("t", tt, "t")
    t.array("out", [0] * len(tt), "out")
    t.stack("st", "waiting (index:temp, top at the top)")
    out, st = [0] * len(tt), []
    lab = lambda: [f"{k}:{tt[k]}" for k in st]  # noqa: E731
    t.frame("for (int i", "The stack holds days still <em>waiting</em> for a warmer day, temperatures decreasing upwards.")
    for i, x in enumerate(tt):
        resolved = []
        while st and tt[st[-1]] < x:
            d = st.pop()
            out[d] = i - d
            resolved.append(d)
        if resolved:
            t.frame("out[day] = i - day", f"Day {i} ({x}°) is warmer than " +
                    ", ".join(f"day {d} ({tt[d]}°) → wait {out[d]}" for d in resolved) + ". Pop them.",
                    t={"ptrs": {"i": i}, "marks": {i: "hl", **{d: "ok" for d in resolved}}},
                    out={"values": list(out), "marks": {d: "ok" for d in resolved}}, st={"items": lab()})
        st.append(i)
        t.frame("waiting.push", f"Day {i} ({x}°) now waits for its own warmer day: push it.",
                t={"ptrs": {"i": i}, "marks": {i: "hl"}}, out={"marks": {}}, st={"items": lab(), "marks": {len(st) - 1: "hl"}})
    assert out == [1, 1, 4, 2, 1, 1, 0, 0]
    t.frame(6, "Days 6 and 7 are never resolved, so they stay 0. Each index is pushed and popped once: O(n).",
            t={"ptrs": {}, "marks": {}}, st={"marks": {}})
    return t


@trace("sliding-window-maximum", "07-stack-queue/sliding-window-maximum.md")
def sliding_window_max():
    a, k = [1, 3, -1, -3, 5, 3, 6, 7], 3
    t = Trace("Sliding window maximum (k = 3) with a monotonic deque", [
        "for (int i = 0; i < a.length; i++) {",
        "    if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();     // slid out",
        "    while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast();  // dominated",
        "    dq.offerLast(i);",
        "    if (i >= k - 1) out[i - k + 1] = a[dq.peekFirst()];",
        "}"])
    t.array("a", a, "a")
    t.queue("dq", "deque (index:value, front = max)")
    t.array("out", ["" for _ in range(len(a) - k + 1)], "out")
    dq, out = [], [""] * (len(a) - k + 1)
    lab = lambda: [f"{j}:{a[j]}" for j in dq]  # noqa: E731
    t.frame("for (int i", "The deque keeps indexes with <em>decreasing</em> values: the front is always the window's max.")
    for i, x in enumerate(a):
        notes = []
        if dq and dq[0] <= i - k:
            notes.append(f"index {dq.pop(0)} slid out of the window")
        dropped = []
        while dq and a[dq[-1]] <= x:
            dropped.append(a[dq.pop()])
        if dropped:
            notes.append(f"{x} dominates {', '.join(map(str, dropped))}: they can never be a max again")
        dq.append(i)
        if i >= k - 1:
            out[i - k + 1] = a[dq[0]]
        msg = f"i = {i} ({x}): " + ("; ".join(notes) + ". " if notes else "") + "push it." + \
              (f" Window max = <strong>{out[i - k + 1]}</strong>." if i >= k - 1 else "")
        t.frame("out[i - k + 1]" if i >= k - 1 else "dq.offerLast", msg,
                a={"ptrs": {"i": i}, "win": [max(0, i - k + 1), i], "marks": {dq[0]: "best"}},
                dq={"items": lab(), "marks": {0: "best"}},
                out={"values": list(out), "marks": {i - k + 1: "ok"} if i >= k - 1 else {}})
    assert out == [3, 3, 5, 5, 6, 7]
    t.frame(5, "[3, 3, 5, 5, 6, 7]. Every index enters and leaves the deque once: O(n), O(k) space.",
            a={"ptrs": {}, "win": None, "marks": {}}, out={"marks": {}})
    return t


# ================================================================================================= hashing
@trace("subarray-sum-equals-k", "08-hashing/subarray-sum-equals-k.md")
def subarray_sum_k():
    a, k = [3, 4, 7, 2, -3, 1, 4, 2], 7
    t = Trace("Subarray sum = 7 with prefix sums and a hash map", [
        "seen.put(0L, 1);   // empty prefix",
        "for (int x : a) {",
        "    prefix += x;",
        "    count += seen.getOrDefault(prefix - k, 0);   // look up BEFORE inserting",
        "    seen.merge(prefix, 1, Integer::sum);",
        "}"])
    t.array("a", a, "a")
    t.map("seen", "seen (prefix → times)")
    t.vars("v")
    seen, prefix, count = {0: 1}, 0, 0
    t.frame("seen.put", "sum(i+1..j) = prefix[j] − prefix[i]. So a subarray ending here sums to k exactly when an "
            "<em>earlier</em> prefix equals prefix − k. Start with prefix 0 seen once.",
            seen={"entries": [[0, 1]]}, v={"items": [["prefix", 0], ["count", 0]]})
    for j, x in enumerate(a):
        prefix += x
        need = prefix - k
        hits = seen.get(need, 0)
        count += hits
        msg = f"j = {j}: prefix = {prefix}, need an earlier prefix of {prefix} − 7 = {c(need)}: " + \
              (f"seen {hits}× → <strong>count = {count}</strong>." if hits else "none.")
        t.frame("count +=", msg, a={"ptrs": {"j": j}, "marks": {j: "hl"}},
                seen={"entries": [[p, n] for p, n in seen.items()], "marks": {str(need): "ok"} if hits else {}},
                v={"items": [["prefix", prefix], ["count", count]]})
        seen[prefix] = seen.get(prefix, 0) + 1
    assert count == 4
    t.frame("seen.merge", "<strong>4</strong> subarrays: [3,4], [7], [7,2,−3,1], [1,4,2]. Negative values are fine, "
            "which is why a sliding window can't solve this. O(n).",
            a={"ptrs": {}, "marks": {}}, seen={"entries": [[p, n] for p, n in seen.items()], "marks": {}})
    return t


@trace("longest-consecutive-sequence", "08-hashing/longest-consecutive-sequence.md")
def longest_consecutive():
    a = [100, 4, 200, 1, 3, 2]
    t = Trace("Longest consecutive sequence with a HashSet", [
        "Set<Integer> set = new HashSet<>(); for (int x : a) set.add(x);",
        "for (int x : set) {",
        "    if (set.contains(x - 1)) continue;   // not the START of a run",
        "    int len = 1;",
        "    for (int y = x; set.contains(y + 1); y++) len++;",
        "    best = Math.max(best, len);",
        "}"])
    t.array("a", a, "a")
    t.vars("v")
    s = set(a)
    best = 0
    t.frame("Set<Integer>", "Put everything in a set for O(1) lookups. Only count from the <em>start</em> of a run, "
            "so each number is walked at most once overall.", v={"items": [["best", 0]]})
    for x in a:                                              # page dry-run order
        i = a.index(x)
        if x - 1 in s:
            t.frame("continue", f"{x}: {x - 1} is in the set, so {x} isn't a start. Skip, its run gets counted from its start.",
                    a={"ptrs": {"x": i}, "marks": {i: "dim"}}, v={"items": [["best", best]]})
            continue
        run = [x]
        while run[-1] + 1 in s:
            run.append(run[-1] + 1)
        best = max(best, len(run))
        t.frame("for (int y", f"{x} starts a run: {' → '.join(map(str, run))}, length {len(run)}. best = {best}.",
                a={"ptrs": {"x": i}, "marks": {a.index(y): ("best" if y == x else "ok") for y in run}},
                v={"items": [["best", best]]})
    assert best == 4
    t.frame(6, "Answer <strong>4</strong> (1, 2, 3, 4). Each element is touched O(1) times: O(n) without sorting. "
            "(Java iterates the set in its own order; the result is the same.)",
            a={"ptrs": {}, "marks": {a.index(y): "ok" for y in (1, 2, 3, 4)}})
    return t


# ================================================================================================= sorting
@trace("insertion-sort", "09-searching-sorting/elementary-sorts.md")
def insertion_sort():
    a = [5, 2, 4, 6, 1, 3]
    t = Trace("Insertion sort on [5, 2, 4, 6, 1, 3]", [
        "for (int i = 1; i < a.length; i++) {",
        "    int key = a[i], j = i - 1;",
        "    while (j >= 0 && a[j] > key) {",
        "        a[j + 1] = a[j];   // shift, don't swap",
        "        j--;",
        "    }",
        "    a[j + 1] = key;",
        "}"])
    t.array("a", a, "a", bars=0.5)
    t.vars("v")
    t.frame("for (int i", "The left part is always sorted. Take the next element and slide it left into place.",
            a={"marks": {0: "done"}})
    for i in range(1, len(a)):
        key, j = a[i], i - 1
        t.frame("int key", f"key = a[{i}] = {key}. Sorted prefix: {a[:i]}.",
                a={"ptrs": {"i": i}, "marks": {**{k: "done" for k in range(i)}, i: "hl"}}, v={"items": [["key", key]]})
        while j >= 0 and a[j] > key:
            a[j + 1] = a[j]
            t.frame("a[j + 1] = a[j]", f"{a[j]} &gt; {key}: shift it right.",
                    a={"values": list(a), "ptrs": {"j": j}, "marks": {**{k: "done" for k in range(i + 1)}, j + 1: "swap"}})
            j -= 1
        a[j + 1] = key
        t.frame("a[j + 1] = key", f"Drop {key} at index {j + 1}. Prefix 0..{i} is sorted.",
                a={"values": list(a), "ptrs": {}, "marks": {**{k: "done" for k in range(i + 1)}, j + 1: "ok"}})
    assert a == [1, 2, 3, 4, 5, 6]
    t.frame(7, "Sorted. O(n²) worst case, but O(n) on nearly sorted input, stable and in place, which is why real "
            "sorts use it for small runs.", a={"marks": {k: "ok" for k in range(len(a))}})
    return t


@trace("quicksort-partition", "09-searching-sorting/merge-quick-sort.md")
def quick_partition():
    a = [7, 2, 9, 4, 3, 8, 6]
    t = Trace("Quicksort: one Lomuto partition around pivot 6", [
        "int pivot = a[hi], i = lo;   // (the real code first swaps a random element to hi)",
        "for (int j = lo; j < hi; j++)",
        "    if (a[j] < pivot) swap(a, i++, j);",
        "swap(a, i, hi);",
        "return i;   // the pivot's final index"])
    t.array("a", a, "a")
    lo, hi = 0, len(a) - 1
    pivot, i = a[hi], lo
    t.frame("int pivot", f"pivot = a[hi] = {pivot}. Invariant: a[lo..i) &lt; pivot, a[i..j) ≥ pivot.",
            a={"ptrs": {"i": i, "j": lo}, "marks": {hi: "best"}})
    for j in range(lo, hi):
        if a[j] < pivot:
            a[i], a[j] = a[j], a[i]
            t.frame("swap(a, i++, j)", f"a[j] = {a[i]} &lt; {pivot}: swap it into the small side at i = {i}, then i++.",
                    a={"values": list(a), "ptrs": {"i": i + 1, "j": j},
                       "marks": {**{k: "ok" for k in range(i + 1)}, **{k: "dim" for k in range(i + 1, j + 1)}, hi: "best", i: "swap", j: "swap"}})
            i += 1
        else:
            t.frame("if (a[j] < pivot)", f"a[j] = {a[j]} ≥ {pivot}: leave it on the big side.",
                    a={"ptrs": {"i": i, "j": j}, "marks": {**{k: "ok" for k in range(i)}, **{k: "dim" for k in range(i, j + 1)}, hi: "best"}})
    a[i], a[hi] = a[hi], a[i]
    assert all(x < 6 for x in a[:i]) and a[i] == 6 and all(x >= 6 for x in a[i + 1:])
    t.frame("swap(a, i, hi)", f"Swap the pivot into index {i}: it is now in its <strong>final</strong> place. "
            "Quicksort recurses on each side (on the smaller one first, keeping the stack O(log n)).",
            a={"values": list(a), "ptrs": {"pivot": i},
               "marks": {**{k: "done" for k in range(i)}, i: "ok", **{k: "win" for k in range(i + 1, len(a))}}})
    return t


# ================================================================================================= trees / heap
@trace("inorder-iterative", "10-trees/traversals.md")
def inorder_iterative():
    t = Trace("In-order traversal with an explicit stack", [
        "TreeNode curr = root;",
        "while (curr != null || !stack.isEmpty()) {",
        "    while (curr != null) { stack.push(curr); curr = curr.left; }   // go left",
        "    curr = stack.pop();",
        "    out.add(curr.val);                                             // visit",
        "    curr = curr.right;                                             // turn right",
        "}"])
    kids, vals = t.tree("T", [1, 2, 3, 4, 5, None, 6], "tree")
    t.stack("st", "stack", inline=True)
    t.queue("out", "out", inline=True)
    stack, out, curr, visited = [], [], "0", []
    t.frame("TreeNode curr", "Left subtree, then the node, then the right subtree, without recursion.",
            T={"ptrs": {"curr": curr}})
    while curr is not None or stack:
        pushed = []
        while curr is not None:
            stack.append(curr)
            pushed.append(vals[curr])
            curr = kids[curr][0]
        if pushed:
            t.frame("go left", f"Go left as far as possible, pushing {', '.join(map(str, pushed))}.",
                    T={"ptrs": {"top": stack[-1]}, "marks": {**{v: "done" for v in visited}, **{n: "win" for n in stack}}},
                    st={"items": [vals[n] for n in stack]})
        curr = stack.pop()
        out.append(vals[curr])
        visited.append(curr)
        t.frame("out.add", f"Pop {vals[curr]} and visit it. Its left side is done.",
                T={"ptrs": {"curr": curr}, "marks": {**{v: "done" for v in visited}, **{n: "win" for n in stack}, curr: "ok"}},
                st={"items": [vals[n] for n in stack]}, out={"items": list(out), "marks": {len(out) - 1: "hl"}})
        curr = kids[curr][1]
    assert out == [4, 2, 5, 1, 3, 6]
    t.frame(6, "In-order: <strong>4 2 5 1 3 6</strong>. The stack replaces the call stack: O(n) time, O(h) space.",
            T={"ptrs": {}, "marks": {n: "ok" for n in vals}}, out={"marks": {}})
    return t


@trace("level-order", "10-trees/level-order.md")
def level_order():
    t = Trace("Level-order traversal with a queue", [
        "q.offer(root);",
        "while (!q.isEmpty()) {",
        "    List<Integer> level = new ArrayList<>();",
        "    for (int size = q.size(); size > 0; size--) {   // snapshot = this level",
        "        TreeNode n = q.poll();",
        "        level.add(n.val);",
        "        if (n.left != null) q.offer(n.left);",
        "        if (n.right != null) q.offer(n.right);",
        "    }",
        "    out.add(level);",
        "}"])
    kids, vals = t.tree("T", [3, 9, 20, None, None, 15, 7], "tree")
    t.queue("q", "queue")
    t.vars("out")
    q, out, done = ["0"], [], []
    t.frame("q.offer", "Start with the root in the queue.", q={"items": [3]}, T={"marks": {"0": "hl"}})
    while q:
        size = len(q)
        t.frame("int size", f"Snapshot size = {size}: exactly the nodes of this level are in the queue.",
                q={"items": [vals[n] for n in q], "marks": {k: "win" for k in range(size)}},
                T={"marks": {**{n: "done" for n in done}, **{n: "win" for n in q}}})
        level = []
        for _ in range(size):
            n = q.pop(0)
            level.append(vals[n])
            done.append(n)
            q += [ch for ch in kids[n] if ch is not None]
        out.append(level)
        t.frame("out.add", f"Polled {level}, enqueued their children. Level done: {level}.",
                q={"items": [vals[n] for n in q], "marks": {}},
                T={"marks": {**{n: "done" for n in done}, **{n: "ok" for n in done[-size:]}}},
                out={"items": [[f"level {d}", str(lv)] for d, lv in enumerate(out)]})
    assert out == [[3], [9, 20], [15, 7]]
    t.frame("while (!q", "Queue empty: <strong>[[3], [9, 20], [15, 7]]</strong>. O(n) time, O(width) space.",
            T={"marks": {n: "ok" for n in vals}})
    return t


@trace("validate-bst", "10-trees/validate-bst.md")
def validate_bst():
    t = Trace("Validate BST by passing (low, high) bounds down", [
        "boolean range(TreeNode n, long low, long high) {",
        "    if (n == null) return true;",
        "    if (n.val <= low || n.val >= high) return false;",
        "    return range(n.left, low, n.val) && range(n.right, n.val, high);",
        "}"])
    kids, vals = t.tree("T", [5, 1, 6, None, None, 3, 7], "tree (every parent/child pair looks fine)")
    t.stack("calls", "call stack")
    fmt = lambda v: "−∞" if v is None else str(v)  # noqa: E731
    labels, marks, calls = {}, {}, []
    t.frame("boolean range", "Comparing a node only with its children is the classic bug: this tree passes that test. "
            "Instead, each node inherits an allowed interval from its ancestors.")

    def rng_(n, low, high):
        if n is None:
            return True
        labels[n] = f"({fmt(low)}, {'+∞' if high is None else high})"
        calls.append(f"range({vals[n]}, {fmt(low)}, {'+∞' if high is None else high})")
        ok = (low is None or vals[n] > low) and (high is None or vals[n] < high)
        marks[n] = "ok" if ok else "bad"
        t.frame("n.val <= low", f"{vals[n]} must be inside {labels[n]}: " + ("yes." if ok else
                f"<strong>no</strong>, {vals[n]} ≤ {low}. It sits in 5's <em>right</em> subtree but is smaller than 5."),
                T={"labels": dict(labels), "marks": dict(marks), "ptrs": {"n": n}}, calls={"items": list(calls)})
        res = ok and rng_(kids[n][0], low, vals[n]) and rng_(kids[n][1], vals[n], high)
        calls.pop()
        return res

    result = rng_("0", None, None)
    assert result is False
    t.frame("return range", "<strong>Not a BST.</strong> The bounds carry every ancestor's constraint down, so the "
            "violation is caught at node 3. O(n) time, O(h) stack.", T={"ptrs": {}}, calls={"items": []})
    return t


@trace("binary-heap", "11-heap/binary-heap.md")
def binary_heap():
    t = Trace("Min-heap: push 5, 3, 8, 1, then pop", [
        "void push(int x) { heap[size] = x; siftUp(heap, size++); }",
        "  siftUp: while (i > 0 && heap[parent] > heap[i]) { swap; i = parent; }",
        "int pop() { int min = heap[0]; heap[0] = heap[--size]; siftDown(heap, 0, size); return min; }",
        "  siftDown: swap with the smaller child while it is smaller"])
    cap = 7
    depth = lambda i: (i + 1).bit_length() - 1  # noqa: E731
    xs = {}
    for i in range(cap):
        d = depth(i)
        k = i - (2 ** d - 1)
        xs[i] = (k + 0.5) * (4.5 / 2 ** d)
    t.graph("H", [(i, "", xs[i], depth(i) * 0.95) for i in range(cap)],
            [(i, ch) for i in range(cap) for ch in (2 * i + 1, 2 * i + 2) if ch < cap], label="heap as a tree")
    t.array("A", [""] * cap, "the same heap as an array (children of i at 2i+1, 2i+2)")
    h = []

    def show(line, cap_text, marks=None):
        t.frame(line, cap_text,
                H={"values": {str(i): v for i, v in enumerate(h)}, "hide": [str(i) for i in range(len(h), cap)],
                   "marks": {str(k): v for k, v in (marks or {}).items()}},
                A={"values": h + [""] * (cap - len(h)), "marks": dict(marks or {})})

    show(0, "An empty min-heap: every parent ≤ its children, so the minimum is always at index 0.")
    for x in (5, 3, 8, 1):
        h.append(x)
        i = len(h) - 1
        show("void push", f"push({x}): append at index {i}.", {i: "hl"})
        while i > 0 and h[(i - 1) // 2] > h[i]:
            p = (i - 1) // 2
            h[p], h[i] = h[i], h[p]
            show("siftUp", f"{h[p]} &lt; its parent {h[i]}: swap upwards.", {p: "swap", i: "swap"})
            i = p
    assert h[0] == 1
    mn = h[0]
    h[0] = h.pop()
    show("int pop", f"pop() returns {mn}. Move the last element ({h[0]}) to the root, then sift it down.", {0: "hl"})
    i = 0
    while True:
        l, r, sm = 2 * i + 1, 2 * i + 2, i
        if l < len(h) and h[l] < h[sm]:
            sm = l
        if r < len(h) and h[r] < h[sm]:
            sm = r
        if sm == i:
            break
        h[i], h[sm] = h[sm], h[i]
        show("siftDown", f"Swap with the smaller child ({h[i]}).", {i: "swap", sm: "swap"})
        i = sm
    assert h == [3, 5, 8]
    show("siftDown", "The heap property holds again. push and pop are O(log n): one path from leaf to root.",
         {k: "ok" for k in range(len(h))})
    return t


# ================================================================================================= graphs
@trace("bfs", "12-graphs/bfs-dfs.md")
def bfs():
    edges = [(0, 1), (0, 2), (1, 3), (2, 3), (3, 4)]
    adj = {i: [] for i in range(6)}
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)
    t = Trace("BFS from 0: visit by distance", [
        "q.offer(start); seen[start] = true;",
        "while (!q.isEmpty()) {",
        "    int u = q.poll();",
        "    order.add(u);",
        "    for (int v : adj.get(u))",
        "        if (!seen[v]) { seen[v] = true; q.offer(v); }   // mark when ENQUEUING",
        "}"])
    t.graph("G", [(0, 0, 0, 0), (1, 1, 1.6, 0), (2, 2, 0, 1.4), (3, 3, 1.6, 1.4), (4, 4, 3.2, 1.4), (5, 5, 3.2, 0)],
            edges, label="graph (5 is isolated)")
    t.queue("q", "queue", inline=True)
    t.queue("order", "order", inline=True)
    q, seen, order, dist = [0], {0}, [], {0: 0}
    t.frame("q.offer", "Start at 0 (distance 0). Mark nodes as seen when they're <em>enqueued</em>, so nothing is queued twice.",
            G={"marks": {"0": "win"}, "labels": {"0": "d=0"}}, q={"items": [0]})
    while q:
        u = q.pop(0)
        order.append(u)
        new = []
        for v in adj[u]:
            if v not in seen:
                seen.add(v)
                q.append(v)
                dist[v] = dist[u] + 1
                new.append(v)
        t.frame("q.offer(v)" if new else "order.add", f"Poll {u} (distance {dist[u]}) and visit it." +
                (f" Enqueue unseen neighbours {', '.join(map(str, new))} at distance {dist[u] + 1}." if new else
                 " No unseen neighbours."),
                G={"marks": {**{str(n): "win" for n in q}, **{str(n): "done" for n in order}, str(u): "ok"},
                   "labels": {str(n): f"d={d}" for n, d in dist.items()},
                   "edges": {f"{u}-{v}": "hl" for v in new}},
                q={"items": list(q)}, order={"items": list(order), "marks": {len(order) - 1: "hl"}})
    assert order == [0, 1, 2, 3, 4]
    t.frame("while (!q", "BFS order <strong>0 1 2 3 4</strong>, by distance. Node 5 was never reached: it's a second "
            "component. O(V + E).", G={"marks": {**{str(n): "ok" for n in order}, "5": "dim"}, "edges": {}})
    return t


@trace("topological-sort", "12-graphs/topological-sort.md")
def topo():
    edges = [(5, 2), (5, 0), (4, 0), (4, 1), (2, 3), (3, 1)]
    n = 6
    t = Trace("Topological sort with Kahn's algorithm", [
        "for (int[] e : edges) indegree[e[1]]++;",
        "for (int i = 0; i < n; i++) if (indegree[i] == 0) ready.offer(i);",
        "while (!ready.isEmpty()) {",
        "    int u = ready.poll();",
        "    order[k++] = u;",
        "    for (int v : adj.get(u)) if (--indegree[v] == 0) ready.offer(v);   // last prerequisite done",
        "}"])
    t.graph("G", [(5, 5, 0, 0), (4, 4, 2.4, 0), (2, 2, 0, 1.3), (0, 0, 1.2, 1.3), (3, 3, 0, 2.6), (1, 1, 2.4, 2.6)],
            edges, directed=True, label="DAG (badge = remaining in-degree)")
    t.queue("ready", "ready", inline=True)
    t.queue("order", "order", inline=True)
    adj = {i: [] for i in range(n)}
    indeg = [0] * n
    for u, v in edges:
        adj[u].append(v)
        indeg[v] += 1
    ready = [i for i in range(n) if indeg[i] == 0]
    order, done = [], []
    lab = lambda: {str(i): f"in={indeg[i]}" for i in range(n) if i not in done}  # noqa: E731
    t.frame("ready.offer", f"Count in-degrees. Nodes with none ({', '.join(map(str, ready))}) have no prerequisites: ready.",
            G={"labels": lab(), "marks": {str(i): "win" for i in ready}}, ready={"items": list(ready)})
    while ready:
        u = ready.pop(0)
        order.append(u)
        done.append(u)
        freed = []
        for v in adj[u]:
            indeg[v] -= 1
            if indeg[v] == 0:
                ready.append(v)
                freed.append(v)
        t.frame("--indegree[v]", f"Output {u}, and remove its edges." +
                (f" {', '.join(map(str, freed))} now has no remaining prerequisites: ready." if freed else ""),
                G={"labels": lab(), "marks": {**{str(i): "done" for i in done}, **{str(i): "win" for i in ready}, str(u): "ok"},
                   "edges": {f"{u}-{v}": "dim" for v in adj[u]}},
                ready={"items": list(ready)}, order={"items": list(order), "marks": {len(order) - 1: "hl"}})
    pos = {v: i for i, v in enumerate(order)}
    assert len(order) == n and all(pos[u] < pos[v] for u, v in edges)
    t.frame(6, f"Order <strong>{' '.join(map(str, order))}</strong>: every edge points forward. If a cycle existed, "
            "some nodes would never reach in-degree 0 and the order would come out short.",
            G={"marks": {str(i): "ok" for i in range(n)}, "edges": {}})
    return t


@trace("dijkstra", "12-graphs/dijkstra.md")
def dijkstra():
    edges = [(0, 1, 4), (0, 2, 1), (2, 1, 2), (1, 3, 1), (2, 3, 5)]
    n, INF = 4, float("inf")
    t = Trace("Dijkstra from 0 with a min-heap", [
        "dist[src] = 0; pq.offer({0, src});",
        "while (!pq.isEmpty()) {",
        "    long[] top = pq.poll(); int u = (int) top[1];",
        "    if (top[0] > dist[u]) continue;           // stale entry",
        "    for (int[] e : adj.get(u)) {",
        "        long nd = dist[u] + e[1];",
        "        if (nd < dist[v]) { dist[v] = nd; pq.offer({nd, v}); }   // relax",
        "    }",
        "}"])
    t.graph("G", [(0, 0, 0, 1), (1, 1, 2, 0), (2, 2, 2, 2), (3, 3, 4, 1)], edges, directed=True,
            label="graph (badge = best distance so far)")
    t.queue("pq", "min-heap (dist, node), smallest first")
    adj = {i: [] for i in range(n)}
    for u, v, w in edges:
        adj[u].append((v, w))
    dist = [INF] * n
    dist[0] = 0
    pq, done = [(0, 0)], set()
    lab = lambda: {str(i): ("∞" if d == INF else str(d)) for i, d in enumerate(dist)}  # noqa: E731
    t.frame("dist[src] = 0", "Every distance starts at ∞ except the source. The heap always yields the closest unfinished node.",
            G={"labels": lab(), "marks": {"0": "win"}}, pq={"items": ["(0, 0)"]})
    while pq:
        d, u = heapq.heappop(pq)
        if d > dist[u]:
            t.frame("continue", f"Pop ({d}, {u}): stale, because dist[{u}] is already {dist[u]}. Skip it (lazy deletion).",
                    G={"labels": lab(), "marks": {**{str(x): "done" for x in done}, str(u): "dim"}, "edges": {}},
                    pq={"items": [f"({a}, {b})" for a, b in sorted(pq)]})
            continue
        done.add(u)
        relaxed = []
        for v, w in adj[u]:
            if d + w < dist[v]:
                dist[v] = d + w
                heapq.heappush(pq, (d + w, v))
                relaxed.append(f"{v} → {d + w}")
        t.frame("dist[v] = nd" if relaxed else "pq.poll", f"Pop ({d}, {u}): {u} is final at distance {d}." +
                (f" Relax its edges: {', '.join(relaxed)}." if relaxed else " No edge improves anything."),
                G={"labels": lab(), "marks": {**{str(x): "done" for x in done}, str(u): "ok"},
                   "edges": {f"{u}-{v}": "hl" for v, _ in adj[u]}},
                pq={"items": [f"({a}, {b})" for a, b in sorted(pq)]})
    assert dist == [0, 3, 1, 4]
    t.frame(8, "Distances <strong>[0, 3, 1, 4]</strong>. The cheapest way to 1 is 0 → 2 → 1 (cost 3), not the direct "
            "edge (4). O((V + E) log V); weights must be non-negative.",
            G={"marks": {str(i): "ok" for i in range(n)}, "edges": {"0-2": "best", "2-1": "best", "1-3": "best"}})
    return t


# ================================================================================================= dp / greedy / bits / trie
@trace("house-robber", "13-dynamic-programming/house-robber.md")
def house_robber():
    a = [2, 7, 9, 3, 1]
    t = Trace("House robber in O(1) space", [
        "int prev2 = 0, prev1 = 0;",
        "for (int x : a) {",
        "    int cur = Math.max(prev1, prev2 + x);   // skip x, or take x on top of best two back",
        "    prev2 = prev1;",
        "    prev1 = cur;",
        "}",
        "return prev1;"])
    t.array("a", a, "houses")
    t.array("best", ["" for _ in a], "best up to house i")
    t.vars("v")
    p2 = p1 = 0
    best = []
    t.frame("int prev2", "For each house: skip it (keep prev1) or rob it (prev2 + x, since the neighbour is off-limits).",
            v={"items": [["prev2", 0], ["prev1", 0]]})
    for i, x in enumerate(a):
        skip, take = p1, p2 + x
        cur = max(skip, take)
        best.append(cur)
        t.frame("int cur", f"House {i} ({x}): skip → {skip}, take → {p2} + {x} = {take}. best = <strong>{cur}</strong> ("
                + ("take" if take > skip else "skip") + ").",
                a={"ptrs": {"i": i}, "marks": {i: "hl"}},
                best={"values": best + [""] * (len(a) - len(best)), "marks": {i: "ok"}},
                v={"items": [["prev2", p1], ["prev1", cur]]})
        p2, p1 = p1, cur
    assert p1 == 12
    t.frame("return prev1", "Answer <strong>12</strong>: rob houses 0, 2 and 4 (2 + 9 + 1). O(n) time, O(1) space.",
            a={"ptrs": {}, "marks": {0: "ok", 2: "ok", 4: "ok"}}, best={"marks": {4: "ok"}})
    return t


@trace("edit-distance", "13-dynamic-programming/edit-distance.md")
def edit_distance():
    x, y = "horse", "ros"
    m, n = len(x), len(y)
    t = Trace('Edit distance "horse" → "ros"', [
        "for (int i = 0; i <= m; i++) dp[i][0] = i;   // delete everything",
        "for (int j = 0; j <= n; j++) dp[0][j] = j;   // insert everything",
        "for (int i = 1; i <= m; i++)",
        "    for (int j = 1; j <= n; j++)",
        "        dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1]",
        "                 : 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));"])
    dp = [[0] * (n + 1) for _ in range(m + 1)]
    show = [["" for _ in range(n + 1)] for _ in range(m + 1)]
    for i in range(m + 1):
        dp[i][0] = show[i][0] = i
    for j in range(n + 1):
        dp[0][j] = show[0][j] = j
    t.grid("dp", m + 1, n + 1, [r[:] for r in show], ["∅"] + list(x), ["∅"] + list(y), "dp[i][j] = cost of a[0..i) → b[0..j)")
    t.frame("dp[i][0] = i", "Border: turning a prefix into the empty string costs its length (deletes), and vice versa (inserts).")
    for i in range(1, m + 1):
        for j in range(1, n + 1):
            if x[i - 1] == y[j - 1]:
                dp[i][j] = dp[i - 1][j - 1]
                why = f"{c(x[i - 1])} = {c(y[j - 1])}: free, copy the diagonal → {dp[i][j]}."
                src = {f"{i - 1},{j - 1}": "best"}
            else:
                rep, dele, ins = dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]
                dp[i][j] = 1 + min(rep, dele, ins)
                why = f"{c(x[i - 1])} ≠ {c(y[j - 1])}: 1 + min(replace {rep}, delete {dele}, insert {ins}) = {dp[i][j]}."
                src = {f"{i - 1},{j - 1}": "win", f"{i - 1},{j}": "win", f"{i},{j - 1}": "win"}
            show[i][j] = dp[i][j]
            t.frame("dp[i][j] =", f"dp[{i}][{j}]: {why}", dp={"values": [r[:] for r in show], "marks": {f"{i},{j}": "hl", **src}})
    assert dp[m][n] == 3
    t.frame(5, "Answer dp[5][3] = <strong>3</strong>: replace h → r, delete r, delete e. O(m·n); one row is enough "
            "for O(n) space.", dp={"marks": {f"{m},{n}": "ok"}})
    return t


@trace("jump-game", "14-greedy/jump-game.md")
def jump_game():
    a = [2, 3, 1, 1, 4]
    t = Trace("Jump game II: minimum jumps as an implicit BFS", [
        "int jumps = 0, currentEnd = 0, furthest = 0;",
        "for (int i = 0; i < a.length - 1; i++) {",
        "    furthest = Math.max(furthest, i + a[i]);",
        "    if (i == currentEnd) {   // exhausted this BFS level",
        "        jumps++;",
        "        currentEnd = furthest;",
        "    }",
        "}"])
    t.array("a", a, "a (max jump from i)")
    t.vars("v")
    jumps = cur_end = furthest = 0
    start = 0
    t.frame("int jumps", "[start, currentEnd] = indexes reachable with exactly <code>jumps</code> jumps. Scan the level, "
            "remember the furthest reach of any of them.",
            a={"win": [0, 0], "ptrs": {"end": 0}}, v={"items": [["jumps", 0], ["furthest", 0]]})
    for i in range(len(a) - 1):
        furthest = max(furthest, i + a[i])
        if i == cur_end:
            jumps += 1
            start, cur_end = i + 1, furthest
            t.frame("currentEnd = furthest", f"i = {i} reaches {i + a[i]}. Level finished: <strong>jump #{jumps}</strong>. "
                    f"The next level is indexes {start}..{cur_end}.",
                    a={"ptrs": {"i": i, "end": min(cur_end, len(a) - 1)}, "win": [start, min(cur_end, len(a) - 1)], "marks": {i: "hl"}},
                    v={"items": [["jumps", jumps], ["furthest", furthest]]})
        else:
            t.frame("furthest = Math", f"i = {i}: could reach {i + a[i]}; furthest = {furthest}.",
                    a={"ptrs": {"i": i, "end": cur_end}, "marks": {i: "hl"}},
                    v={"items": [["jumps", jumps], ["furthest", furthest]]})
    assert jumps == 2
    t.frame(7, "The last index is in the current level: <strong>2 jumps</strong> (0 → 1 → 4). Greedy, O(n), no DP table.",
            a={"ptrs": {}, "win": None, "marks": {0: "ok", 1: "ok", 4: "ok"}})
    return t


@trace("count-set-bits", "15-bit-manipulation/count-set-bits.md")
def count_bits():
    n0 = 52
    t = Trace("Brian Kernighan: count the 1 bits of 52", [
        "int count = 0;",
        "while (n != 0) {",
        "    n &= n - 1;   // clears the LOWEST set bit",
        "    count++;",
        "}"])
    bits = lambda v: [int(b) for b in format(v, "08b")]  # noqa: E731
    t.array("b", bits(n0), "n in binary (bit 7 … bit 0)", indexes=False)
    t.vars("v")
    n, count = n0, 0
    t.frame("int count", f"n = {n0} = {format(n0, '08b')}. n − 1 flips the lowest 1 and every 0 below it, so "
            "n &amp; (n − 1) erases exactly that one bit.",
            b={"marks": {k: "ok" for k, v in enumerate(bits(n0)) if v}}, v={"items": [["n", n], ["count", 0]]})
    while n:
        low = (n & -n).bit_length() - 1
        n &= n - 1
        count += 1
        t.frame("n &= n - 1", f"Clear bit {low}: n = {n} = {format(n, '08b')}. count = {count}.",
                b={"values": bits(n), "marks": {**{k: "ok" for k, v in enumerate(bits(n)) if v}, 7 - low: "bad"}},
                v={"items": [["n", n], ["count", count]]})
    assert count == 3
    t.frame("while (n != 0)", "n is 0 after <strong>3</strong> rounds: one loop per set bit, not per bit position.",
            b={"marks": {}})
    return t


@trace("trie", "16-tries-advanced/trie.md")
def trie():
    t = Trace("Trie: insert apple, app, apt; then search", [
        "void insert(String word) {",
        "    Node n = root; n.prefixCount++;",
        "    for (char c : word.toCharArray()) {",
        "        if (n.next[c - 'a'] == null) n.next[c - 'a'] = new Node();",
        "        n = n.next[c - 'a']; n.prefixCount++;",
        "    }",
        "    n.isWord = true;",
        "}",
        "boolean search(String w) { Node n = walk(w); return n != null && n.isWord; }"])
    nodes = [("root", "·", 1.5, 0), ("a", "a", 1.5, 0.9), ("ap", "p", 1.5, 1.8), ("app", "p", 0.8, 2.7),
             ("apt", "t", 2.3, 2.7), ("appl", "l", 0.8, 3.6), ("apple", "e", 0.8, 4.5)]
    t.graph("T", nodes, [("root", "a"), ("a", "ap"), ("ap", "app"), ("ap", "apt"), ("app", "appl"), ("appl", "apple")],
            label="trie (badge = words through this node, * = a word ends here)")
    exists, words, count = {"root"}, set(), {"root": 0}
    allids = [n[0] for n in nodes]

    def lab():
        return {k: f"{count[k]}{'*' if k in words else ''}" for k in exists if k != "root" or count[k]}

    def state(marks=None, ptr=None):
        return {"hide": [k for k in allids if k not in exists], "labels": lab(),
                "marks": marks or {k: "ok" for k in words}, "ptrs": {"n": ptr} if ptr else {}}

    t.frame("void insert", "A trie shares prefixes: each node is one character on the way to some words.", T=state())
    for w in ("apple", "app", "apt"):
        count["root"] += 1
        path = []
        for k in range(1, len(w) + 1):
            p = w[:k]
            new = p not in exists
            exists.add(p)
            count[p] = count.get(p, 0) + 1
            path.append(p)
        words.add(w)
        t.frame("n.isWord = true", f"insert(\"{w}\"): walk/create {' → '.join(w)}, bump each prefixCount, and flag the "
                f"last node as a word end." + (" Only the new branch was created." if w != "apple" else ""),
                T=state({**{k: "ok" for k in words}, **{p: "hl" for p in path}}, path[-1]))
    t.frame("boolean search", "search(\"app\"): the walk ends on a node flagged as a word → <strong>true</strong>.",
            T=state({"a": "win", "ap": "win", "app": "ok"}, "app"))
    t.frame("boolean search", "search(\"ap\"): the walk succeeds but \"p\" is not a word end → <strong>false</strong>. "
            "startsWith(\"ap\") would be true (3 words pass through). Every operation is O(length).",
            T=state({"a": "win", "ap": "bad"}, "ap"))
    assert words == {"apple", "app", "apt"} and count["ap"] == 3
    return t
