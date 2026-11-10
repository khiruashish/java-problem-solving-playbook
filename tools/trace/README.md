# Animated traces

Pages under **Step through it** play a recorded run of the algorithm: pointers glide over arrays, linked-list
arrows flip, stacks grow and shrink, DP tables fill, with the current code line highlighted.

| Piece | Where |
|---|---|
| Player (components, controls, keyboard) | `docs/assets/js/trace.js` |
| Styles (3D theme, light/dark, reduced motion) | `docs/assets/css/trace.css` |
| Generated snippets, included with `--8<--` | `docs/snippets/traces/*.html` |
| Recorder + one function per problem | `tools/trace/tracelib.py`, `tools/trace/traces.py`, `tools/trace/traces_more.py` |

Each function in `traces.py` **runs the algorithm for real** and calls `t.frame(...)` at each interesting moment,
then asserts the final answer. A wrong trace fails the build instead of reaching the site. Without JavaScript,
a trace shows as a numbered list of its steps.

```bash
python tools/trace/build.py              # regenerate every snippet (CI checks they are up to date)
python tools/trace/build.py kadane       # just one
mkdocs serve
```

## Adding a trace

1. Add a function to `traces.py` or `traces_more.py` with `@trace("name", "<topic>/<page>.md")`. Mirror the Java solution's
   variable names in `code` and use the page's own example.
2. Build components: `array` (`bars=` to draw values as bars, state `water`), `grid`, `list` (nodes + next
   indexes), `stack`, `queue`, `map`, `vars`, `graph` (nodes with x/y, edges with optional weights, `directed`)
   and `tree` (a binary tree from LeetCode level order, drawn with `graph`). `inline=True` puts a component beside
   its neighbours.
3. Call `t.frame(line, caption, component=changes...)`. `line` is a fragment of the code line that just ran.
   State carries forward, so pass only what changed. Marks: `hl ok bad swap dim win done best`.
4. `assert` the answer, run `build.py`, and include it in the page before `## Code`:
   `--8<-- "docs/snippets/traces/<name>.html"`.
