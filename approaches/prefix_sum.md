# Prefix Sum Approach Guide

## Core Idea
A prefix sum array lets you answer "sum of elements from index i to j" in O(1) after O(n) preprocessing. This converts a repeated sum question from O(n) per query to O(1).

**When to use:** Any problem that asks about sum of a subarray/subrange, especially when done repeatedly or when looking for a specific sum.

---

## Pattern 1: Basic Prefix Sum Array

```
prefix[0] = 0
prefix[i] = prefix[i-1] + nums[i-1]    // 1-indexed prefix

// Query: sum from index l to r (0-indexed, inclusive)
rangeSum(l, r) = prefix[r+1] - prefix[l]
```

### Example
```
nums   = [1, 2, 3, 4, 5]
prefix = [0, 1, 3, 6, 10, 15]

sum(1, 3) = prefix[4] - prefix[1] = 10 - 1 = 9  (2+3+4)
```

---

## Pattern 2: Prefix Sum + HashMap (Most Common FAANG Pattern)

### Core Idea: Find number of subarrays with sum = k

```
prefixSumCount = {0: 1}   // empty subarray has sum 0
currentSum = 0
count = 0

FOR each element:
    currentSum += element
    
    // If (currentSum - k) was seen before, those subarrays sum to k
    count += prefixSumCount.get(currentSum - k, 0)
    
    prefixSumCount[currentSum] += 1

RETURN count
```

**Why it works:**
- If prefix[j] - prefix[i] = k, then subarray nums[i..j] sums to k
- So for each j, we look for how many times (prefix[j] - k) appeared before

---

## Pattern 3: Product Array (Prefix × Suffix)

### Pseudocode: Product of Array Except Self
```
prefix[i] = product of all elements to the LEFT of i
suffix[i] = product of all elements to the RIGHT of i

result[i] = prefix[i] * suffix[i]

// Build prefix left to right
prefix[0] = 1
FOR i = 1 to n-1:
    prefix[i] = prefix[i-1] * nums[i-1]

// Build suffix right to left
suffix[n-1] = 1
FOR i = n-2 downTo 0:
    suffix[i] = suffix[i+1] * nums[i+1]

result[i] = prefix[i] * suffix[i]
```

---

## Pattern 4: 2D Prefix Sum (Matrix Range Queries)

```
// Build 2D prefix sum
for r = 1 to rows:
    for c = 1 to cols:
        prefix[r][c] = matrix[r-1][c-1]
                     + prefix[r-1][c]      // above
                     + prefix[r][c-1]      // left
                     - prefix[r-1][c-1]    // remove double-counted corner

// Query: sum of rectangle (r1,c1) to (r2,c2) (0-indexed)
sum = prefix[r2+1][c2+1]
    - prefix[r1][c2+1]
    - prefix[r2+1][c1]
    + prefix[r1][c1]
```

---

## Pattern 5: Prefix Sum for Finding Equilibrium / Balance

### Pseudocode: Find pivot index (left sum == right sum)
```
totalSum = sum of all elements

leftSum = 0
FOR i = 0 to n-1:
    rightSum = totalSum - leftSum - nums[i]
    IF leftSum == rightSum: RETURN i
    leftSum += nums[i]

RETURN -1
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Range Sum Query (Immutable) | Easy | Basic Prefix Sum | Amazon, Google |
| Find Pivot Index | Easy | Prefix == Suffix | Amazon |
| Product of Array Except Self | Medium | Prefix × Suffix | All FAANG |
| Subarray Sum Equals K | Medium | Prefix Sum + HashMap | All FAANG |
| Continuous Subarray Sum | Medium | Prefix Sum + HashMap | Amazon, Google |
| Count of Range Sum | Hard | Prefix Sum + Merge Sort | Google |
| Range Sum Query 2D (Mutable) | Medium | 2D Prefix / Fenwick Tree | Google, Amazon |
| Maximum Size Subarray Sum = K | Medium | Prefix Sum + HashMap | Google |
