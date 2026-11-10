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

    def array(self, id, values, label=None, indexes=True):
        self._add({"type": "array", "id": id, "values": list(values), "label": label, "indexes": indexes},
                  {"values": list(values), "marks": {}, "ptrs": {}, "win": None})

    def grid(self, id, rows, cols, values=None, row_labels=None, col_labels=None, label=None):
        self._add({"type": "grid", "id": id, "rows": rows, "cols": cols, "values": values, "rowLabels": row_labels,
                   "colLabels": col_labels, "label": label}, {"values": values, "marks": {}})

    def list(self, id, nodes, nxt, label=None):
        self._add({"type": "list", "id": id, "nodes": list(nodes), "label": label},
                  {"next": list(nxt), "ptrs": {}, "marks": {}})

    def stack(self, id, label=None):
        self._add({"type": "stack", "id": id, "label": label}, {"items": [], "marks": {}})

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
