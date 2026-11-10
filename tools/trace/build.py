"""Regenerate the animated traces in docs/snippets/traces/.

    python tools/trace/build.py              # all traces
    python tools/trace/build.py kadane       # one

Each trace runs its algorithm and asserts the answer, so a wrong trace fails the build instead of shipping.
"""
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)

from traces import TRACES  # noqa: E402

OUT = os.path.normpath(os.path.join(HERE, "..", "..", "docs", "snippets", "traces"))
os.makedirs(OUT, exist_ok=True)
for name in sys.argv[1:] or sorted(TRACES):
    fn, page = TRACES[name]
    t = fn()
    path = os.path.join(OUT, name + ".html")
    t.write(path)
    print(f"{name:24} {len(t.frames):3} frames  → {os.path.relpath(path)}  (for docs/{page})")
