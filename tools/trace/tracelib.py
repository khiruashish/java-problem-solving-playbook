"""Record an algorithm's run as frames for the page tracer (docs/assets/js/trace.js).

A spec builds the components once (array, grid, list, stack, map, vars), then calls `frame(...)` at each
interesting moment while running the real algorithm. State carries forward between frames, so a frame only
passes what changed. `write()` emits the HTML snippet that pages include with --8<--.
"""
import copy
import html
import json


class Trace:
    def __init__(self, title, code):
        self.title = title
        self.code = code
        self.layout = []
        self.state = {}
        self.frames = []

    # ---------- components ----------
    def _add(self, comp, state):
        self.layout.append({k: v for k, v in comp.items() if v is not None})
        self.state[comp["id"]] = state

    def array(self, id, values, label=None, indexes=True, bars=None, inline=None):
        """bars=<rem per unit> draws values as bars (state `water` adds water on top)."""
        self._add({"type": "array", "id": id, "values": list(values), "label": label, "indexes": indexes,
                   "bars": bars, "inline": inline},
                  {"values": list(values), "marks": {}, "ptrs": {}, "win": None, "water": {}})

    def grid(self, id, rows, cols, values=None, row_labels=None, col_labels=None, label=None):
        self._add({"type": "grid", "id": id, "rows": rows, "cols": cols, "values": values, "rowLabels": row_labels,
                   "colLabels": col_labels, "label": label}, {"values": values, "marks": {}})

    def list(self, id, nodes, nxt, label=None):
        self._add({"type": "list", "id": id, "nodes": list(nodes), "label": label},
                  {"next": list(nxt), "ptrs": {}, "marks": {}})

    def stack(self, id, label=None, inline=None, items=None):
        self._add({"type": "stack", "id": id, "label": label, "inline": inline}, {"items": list(items or []), "marks": {}})

    def queue(self, id, label=None, inline=None):
        self._add({"type": "queue", "id": id, "label": label, "inline": inline}, {"items": [], "marks": {}})

    def graph(self, id, nodes, edges, directed=False, label=None, inline=None):
        """nodes: [(id, label, x, y)] in layout units; edges: [(u, v)] or [(u, v, weight)]."""
        self._add({"type": "graph", "id": id, "label": label, "directed": directed, "inline": inline,
                   "nodes": [{"id": str(n[0]), "label": n[1], "x": n[2], "y": n[3]} for n in nodes],
                   "edges": [[str(e[0]), str(e[1])] + ([e[2]] if len(e) > 2 else []) for e in edges]},
                  {"marks": {}, "edges": {}, "labels": {}, "values": {}, "hide": [], "ptrs": {}})

    def tree(self, id, level_order, label=None, inline=None):
        """Binary tree from LeetCode level order (None = missing child). Node ids are level-order positions.
        Returns {id: (left_id, right_id)} so specs can walk it."""
        kids, vals, queue, i = {}, {}, [], 1
        if level_order and level_order[0] is not None:
            vals["0"] = level_order[0]
            queue.append("0")
        while queue and i < len(level_order):
            n = queue.pop(0)
            left = right = None
            if i < len(level_order) and level_order[i] is not None:
                left = str(i); vals[left] = level_order[i]; queue.append(left)
            i += 1
            if i < len(level_order) and level_order[i] is not None:
                right = str(i); vals[right] = level_order[i]; queue.append(right)
            i += 1
            kids[n] = (left, right)
        for n in vals:
            kids.setdefault(n, (None, None))
        order, depth = [], {}

        def inorder(n, d):                                   # x = in-order rank → no overlaps
            if n is None:
                return
            inorder(kids[n][0], d + 1)
            order.append(n)
            depth[n] = d
            inorder(kids[n][1], d + 1)
        inorder("0" if vals else None, 0)
        x = {n: k * 0.75 for k, n in enumerate(order)}
        nodes = [(n, vals[n], x[n], depth[n] * 0.95) for n in order]
        edges = [(n, ch) for n in order for ch in kids[n] if ch is not None]
        self.graph(id, nodes, edges, False, label, inline)
        return kids, vals

    def map(self, id, label=None):
        self._add({"type": "map", "id": id, "label": label}, {"entries": [], "marks": {}})

    def vars(self, id, label=None):
        self._add({"type": "vars", "id": id, "label": label}, {"items": []})

    # ---------- frames ----------
    def line(self, fragment):
        """Index of the first code line containing `fragment` (so specs don't hard-code line numbers)."""
        for i, text in enumerate(self.code):
            if fragment in text:
                return i
        raise KeyError(f"no code line contains {fragment!r}")

    def frame(self, line, caption, **changes):
        for cid, upd in changes.items():
            self.state[cid].update(copy.deepcopy(upd))
        self.frames.append({"line": self.line(line) if isinstance(line, str) else line,
                            "caption": caption, "state": copy.deepcopy(self.state)})

    # ---------- output ----------
    def render(self):
        data = {"title": self.title, "code": self.code, "layout": self.layout, "frames": self.frames}
        blob = json.dumps(data, ensure_ascii=False, separators=(",", ":")).replace("</", "<\\/")
        steps = "".join(f"<li>{f['caption']}</li>" for f in self.frames)
        out = (f'<div class="pb-trace" data-pb-trace>\n'
               f'<div class="pb-trace-title">{html.escape(self.title)}</div>\n'
               f'<script type="application/json" class="pb-trace-data">{blob}</script>\n'
               f'<ol class="pb-trace-steps">{steps}</ol>\n'
               f'</div>\n')
        assert "\n\n" not in out, "a blank line would end the raw HTML block"
        return out

    def write(self, path):
        with open(path, "w", encoding="utf-8") as f:
            f.write(self.render())


def c(text):
    """Inline code for captions."""
    return f"<code>{html.escape(str(text))}</code>"
