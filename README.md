# Java Problem-Solving Playbook

A **MkDocs Material** site with a custom **3D theme** that teaches Java problem solving step by step,
from **Easy to Expert**: strings, arrays, logical/math, recursion and backtracking, Java 8+ streams, and
(next) every core data structure and algorithm.

Every problem includes:

- the **thinking process**: restate, brute force, find the waste, name the pattern
- **brute force → optimal**, plus **recursive** and **iterative/normal** versions, and **Java 8 Streams** where it fits
- a dry-run table, a complexity table, edge cases and follow-ups
- **real Java 21 code that is compiled and JUnit-tested**. Docs embed the source files with `pymdownx.snippets`,
  so the code on the page is always the code that passed the tests.

## Quick start

```bash
mvn test                              # run all tests
pip install -r requirements.txt
mkdocs serve                          # http://127.0.0.1:8000
mkdocs build --strict                 # what CI runs
```

## Layout

```
MASTER_PROMPT.md            the master prompt: scope, curriculum, page template, quality bar
mkdocs.yml                  site config + navigation (Easy → Expert ordering)
docs/                       one folder per topic; index.md = primer + problem table
docs/assets/css/3d.css      3D theme (depth cards, tilt, gradient hero, raised badges)
docs/assets/js/tilt.js      mouse-follow tilt for cards
src/main/java/com/playbook/<topic>/   one class per problem, one public static method per approach
src/test/java/com/playbook/<topic>/   JUnit 5 tests verifying every approach
```

## Status

| Topic | Problems |
|-------|----------|
| Foundations | 4 guides |
| Strings | 16 ✅ |
| Arrays | 14 ✅ |
| Logical & Math | 5 ✅ |
| Recursion & Backtracking | 7 ✅ |
| Java 8 Streams | ~50 questions ✅ |
| Linked List | 12 ✅ |
| Stack & Queue | 10 ✅ |
| Hashing … Tries (08–16) | 🚧 planned, see `docs/roadmap.md` |

## Extending

Open `MASTER_PROMPT.md` and use the **next-batch prompt** at the bottom.
