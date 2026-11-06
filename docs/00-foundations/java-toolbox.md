# Java toolbox for problem solving

```java
// Strings
StringBuilder sb = new StringBuilder();  sb.append(x).reverse().insert(0, c).setCharAt(i, c);
char[] cs = s.toCharArray();             String t = new String(cs);   String.valueOf(cs, 0, k);
Character.isLetterOrDigit(c);  Character.toLowerCase(c);  c - 'a';  (char) ('a' + i);
s.chars()  // IntStream of chars
String.join(" ", list);  s.split("\\s+");  s.strip();  s.repeat(3);  s.isBlank();

// Arrays
Arrays.sort(a);  Arrays.sort(a, from, to);  Arrays.fill(a, -1);  Arrays.copyOfRange(a, l, r);
Arrays.equals(a, b);  Arrays.stream(a).sum();  Arrays.asList(...);  int[] c = a.clone();
Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));

// Maps & sets
map.merge(key, 1, Integer::sum);            // frequency count in one line
map.getOrDefault(key, 0);
map.computeIfAbsent(key, k -> new ArrayList<>()).add(v);   // grouping
new LinkedHashMap<>()  // keeps insertion order;  new TreeMap<>() // sorted keys

// Stack / Queue / Deque. Use ArrayDeque, never java.util.Stack (synchronised, legacy).
Deque<Integer> stack = new ArrayDeque<>();  stack.push(x); stack.pop(); stack.peek();
Deque<Integer> queue = new ArrayDeque<>();  queue.offer(x); queue.poll(); queue.peek();

// Heap
PriorityQueue<Integer> min = new PriorityQueue<>();
PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());

// Overflow-safe tricks
int mid = (lo + hi) >>> 1;   long sum = (long) a + b;   Math.addExact(a, b);
```

!!! warning "Classic traps"
    - `==` on `Integer` objects outside −128..127 compares references. Use `equals`.
    - `Math.abs(Integer.MIN_VALUE)` is still negative.
    - `-7 % 3 == -1` in Java (the sign follows the dividend).
    - `list.remove(1)` removes **index** 1; `list.remove(Integer.valueOf(1))` removes the **value** 1.
