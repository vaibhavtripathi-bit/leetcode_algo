# Bit Manipulation Approach Guide

## Core Operators

| Operator | Symbol | Effect |
|---|---|---|
| AND | `a & b` | 1 only if both bits are 1 |
| OR | `a \| b` | 1 if at least one bit is 1 |
| XOR | `a ^ b` | 1 if bits differ (same = 0) |
| NOT | `~a` | Flip all bits |
| Left shift | `a shl n` | Multiply by 2^n |
| Right shift | `a shr n` | Divide by 2^n |

---

## Essential Tricks

### Check if bit i is set
```
(n shr i) and 1 == 1
```

### Set bit i
```
n = n or (1 shl i)
```

### Clear bit i
```
n = n and (1 shl i).inv()
```

### Flip bit i
```
n = n xor (1 shl i)
```

### Get lowest set bit
```
lowest = n and (-n)    // or: n and n.inv() + 1
```

### Remove lowest set bit
```
n = n and (n - 1)
```

### Check if power of 2
```
n > 0 AND (n and (n - 1)) == 0
```

### Count set bits (Brian Kernighan)
```
count = 0
WHILE n != 0:
    n = n and (n - 1)    // removes lowest set bit
    count++
```

---

## Pattern 1: XOR for Finding Single Number

### Key XOR properties:
```
a ^ a = 0      (same number cancels)
a ^ 0 = a      (XOR with 0 = itself)
XOR is commutative and associative
```

### Pseudocode: Single Number (every number appears twice except one)
```
result = 0
FOR each num: result ^= num
RETURN result    // all pairs cancel, only unique remains
```

---

## Pattern 2: Bit Masking for Subsets

### Pseudocode: Enumerate all subsets of array
```
n = array.length
FOR mask = 0 to (1 shl n) - 1:
    subset = []
    FOR i = 0 to n-1:
        IF mask and (1 shl i) != 0:
            subset.add(array[i])
```

---

## Pattern 3: DP with Bitmask (for small sets)

### Pseudocode: Minimum path visiting all nodes (TSP-like)
```
dp[mask][i] = min cost to visit all nodes in mask, ending at node i
dp[1 shl i][i] = 0   // start at each node

FOR mask = 0 to (1 shl n) - 1:
    FOR each node i in mask:
        FOR each next node j NOT in mask:
            newMask = mask or (1 shl j)
            dp[newMask][j] = min(dp[newMask][j], dp[mask][i] + dist[i][j])
```

---

## Pattern 4: Counting Bits

### Key insight: dp[i] = dp[i >> 1] + (i & 1)
```
// i >> 1 is i with last bit removed (we know its count)
// (i & 1) adds 1 if last bit is set

dp[0] = 0
FOR i = 1 to n:
    dp[i] = dp[i >> 1] + (i & 1)
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Single Number | Easy | XOR | All FAANG |
| Number of 1 Bits | Easy | Brian Kernighan | Amazon, Google |
| Counting Bits | Easy | DP + Bit | Amazon, Microsoft |
| Reverse Bits | Easy | Bit manipulation | Apple, Amazon |
| Missing Number | Easy | XOR / Gauss | All FAANG |
| Sum of Two Integers | Medium | Bit addition | Meta, Amazon |
| Single Number II | Medium | Bit counting | Amazon |
| Subsets | Medium | Bit masking | All FAANG |
