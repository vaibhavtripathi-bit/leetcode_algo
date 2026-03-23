# Segment Tree & Fenwick Tree (BIT) Approach Guide

## When to Use
Both solve the same class of problems: **range queries + point updates** in O(log n).

| | Segment Tree | Fenwick Tree (BIT) |
|---|---|---|
| Range Sum Query | ✓ | ✓ |
| Range Min/Max Query | ✓ | ✗ (harder) |
| Range Update | ✓ (lazy) | ✓ (difference array) |
| Code complexity | More complex | Simpler |
| Use at FAANG | Range min/max/sum | Range sum, count inversions |

---

## Fenwick Tree (Binary Indexed Tree)

### Core Idea
Each index stores the sum of a range determined by the lowest set bit of its index. `i & (-i)` gives the lowest set bit.

```
// 1-indexed (easier implementation)
tree = IntArray(n + 1)

UPDATE(i, delta):
    WHILE i <= n:
        tree[i] += delta
        i += i & (-i)    // move to parent

QUERY(i) → prefix sum [1..i]:
    sum = 0
    WHILE i > 0:
        sum += tree[i]
        i -= i & (-i)    // remove lowest set bit → move to predecessor

RANGE_QUERY(l, r) = QUERY(r) - QUERY(l - 1)
```

### Why `i & (-i)` works:
`-i` in two's complement flips all bits and adds 1.
`i & (-i)` isolates only the lowest set bit.
- Index 6 (110) → lowest bit = 2 → responsible for range of size 2
- Index 4 (100) → lowest bit = 4 → responsible for range of size 4

---

## Segment Tree

### Structure
- Binary tree where each node stores aggregate (sum/min/max) of a range
- Leaf nodes = individual elements
- Internal nodes = aggregate of children's ranges
- Array representation: node i → children at 2i and 2i+1

### Build
```
build(node, start, end):
    IF start == end:
        tree[node] = nums[start]
    ELSE:
        mid = (start + end) / 2
        build(2*node, start, mid)
        build(2*node+1, mid+1, end)
        tree[node] = tree[2*node] + tree[2*node+1]   // or min/max
```

### Point Update
```
update(node, start, end, idx, val):
    IF start == end:
        tree[node] = val
    ELSE:
        mid = (start + end) / 2
        IF idx <= mid: update(2*node, start, mid, idx, val)
        ELSE: update(2*node+1, mid+1, end, idx, val)
        tree[node] = tree[2*node] + tree[2*node+1]
```

### Range Query
```
query(node, start, end, l, r):
    IF r < start OR end < l: RETURN identity  // no overlap
    IF l <= start AND end <= r: RETURN tree[node]  // full overlap
    
    mid = (start + end) / 2
    left = query(2*node, start, mid, l, r)
    right = query(2*node+1, mid+1, end, l, r)
    RETURN left + right   // or min/max
```

### Lazy Propagation (Range Update)
```
// Instead of updating all nodes immediately, mark parent as "lazy"
// Only propagate laziness when you actually need the child's value

pushDown(node):
    IF lazy[node] != 0:
        tree[2*node] += lazy[node] * size_of_left_half
        lazy[2*node] += lazy[node]
        tree[2*node+1] += lazy[node] * size_of_right_half
        lazy[2*node+1] += lazy[node]
        lazy[node] = 0
```

---

## Count Inversions (Merge Sort OR Fenwick Tree)

### Using Fenwick Tree:
```
// Process from right to left
// For each element, count how many elements to its right are smaller

bit = Fenwick Tree of size maxVal

FOR i = n-1 downTo 0:
    inversions += bit.query(nums[i] - 1)   // count smaller elements seen so far
    bit.update(nums[i], 1)
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Structure | Asked At |
|---------|-----------|-----------|----------|
| Range Sum Query (Mutable) | Medium | Fenwick Tree / Segment Tree | Amazon, Google |
| Count of Smaller Numbers After Self | Hard | Fenwick Tree / Merge Sort | Amazon, Google |
| Count Inversions | Hard | Fenwick Tree / Merge Sort | Google |
| Range Min Query | Medium | Segment Tree | Google |
| My Calendar I/II/III | Medium/Hard | Segment Tree / TreeMap | Google |
| The Skyline Problem | Hard | Segment Tree | Google, Amazon |
| Number of Longest Increasing Subsequence | Medium | Segment Tree | Google |
