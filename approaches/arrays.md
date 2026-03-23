# Arrays Approach Guide

## Core Idea
Arrays are the foundation of almost every interview problem. Key patterns:
1. **Prefix Sum** — precompute cumulative sums for O(1) range queries
2. **Kadane's Algorithm** — scan once, track local/global maximum
3. **Cyclic Sort** — place each number at its "correct" index
4. **Two Pass (Prefix × Suffix)** — compute left context, then multiply with right context

---

## Pattern 1: Two Sum / Complement HashMap

```
seen = {}
FOR each (index, num):
    complement = target - num
    IF complement in seen: RETURN [seen[complement], index]
    seen[num] = index
```

---

## Pattern 2: Kadane's Algorithm (Maximum Subarray)

```
currentSum = nums[0]
maxSum = nums[0]

FOR i = 1 to n-1:
    // Extend or restart?
    currentSum = max(nums[i], currentSum + nums[i])
    maxSum = max(maxSum, currentSum)

RETURN maxSum
```

**Key insight:** If adding the current element to the running sum makes it smaller than the element alone, discard the previous subarray and start fresh.

---

## Pattern 3: Prefix × Suffix (Product Except Self)

```
// result[i] = product of all elements except nums[i]

// Left pass: result[i] = product of nums[0..i-1]
result[0] = 1
FOR i = 1 to n-1: result[i] = result[i-1] * nums[i-1]

// Right pass: multiply by product of nums[i+1..n-1]
suffix = 1
FOR i = n-1 downTo 0:
    result[i] *= suffix
    suffix *= nums[i]
```

---

## Pattern 4: Cyclic Sort (Missing / Duplicate Numbers)

**When:** Array contains numbers in range [1, n] and you need O(1) space.

```
// Place each num at index (num-1)
FOR i = 0 to n-1:
    WHILE nums[i] in [1,n] AND nums[nums[i]-1] != nums[i]:
        SWAP(nums[i], nums[nums[i]-1])

// Now scan for mismatches
FOR i = 0 to n-1:
    IF nums[i] != i+1: RETURN i+1   // first missing positive
```

---

## Pattern 5: Next Permutation

```
// Step 1: Find rightmost descent (pivot)
i = n-2
WHILE i >= 0 AND nums[i] >= nums[i+1]: i--

// Step 2: If pivot found, swap with rightmost number greater than it
IF i >= 0:
    j = n-1
    WHILE nums[j] <= nums[i]: j--
    SWAP(nums[i], nums[j])

// Step 3: Reverse suffix after pivot
REVERSE(nums, i+1, n-1)
```

---

## Pattern 6: Subarray Sum with Prefix + HashMap (see prefix_sum.md)

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Two Sum | Easy | Complement HashMap | All FAANG |
| Maximum Subarray | Easy | Kadane's | All FAANG |
| Best Time to Buy/Sell Stock | Easy | Single Pass Max | All FAANG |
| Contains Duplicate | Easy | HashSet | All FAANG |
| Product of Array Except Self | Medium | Prefix × Suffix | All FAANG |
| Subarray Sum Equals K | Medium | Prefix + HashMap | All FAANG |
| Longest Consecutive Sequence | Medium | HashSet | All FAANG |
| Next Permutation | Medium | Three-step | Google, Amazon, MS |
| First Missing Positive | Hard | Cyclic Sort / Mark | All FAANG |
| Count of Range Sum | Hard | Prefix + Merge Sort | Google |
