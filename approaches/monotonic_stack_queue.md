# Monotonic Stack / Monotonic Queue Approach Guide

## Core Idea
A monotonic stack/queue maintains elements in sorted order (increasing or decreasing). When a new element violates the order, pop elements until order is restored — **those pops are the key insight** (they represent answers to "next greater/smaller" questions).

---

## Monotonic Stack Patterns

### Pattern 1: Next Greater Element

**Invariant:** Stack is always in decreasing order (top is smallest seen so far).
When we see a larger element, all smaller elements on the stack have their "next greater" answer.

```
stack = []    // stores indices, values are decreasing
result = [-1] * n    // default: no greater element

FOR i = 0 to n-1:
    WHILE stack not empty AND nums[stack.top] < nums[i]:
        idx = stack.pop()
        result[idx] = nums[i]    // nums[i] is next greater for idx
    
    stack.push(i)

RETURN result
```

### Pattern 2: Next Smaller Element (reverse the comparison)
```
WHILE stack not empty AND nums[stack.top] > nums[i]:
    idx = stack.pop()
    result[idx] = nums[i]    // nums[i] is next smaller
```

### Pattern 3: Previous Greater / Previous Smaller
```
// Process left to right, but stack gives "previous" info
FOR i = 0 to n-1:
    WHILE stack not empty AND stack.top <= nums[i]:
        stack.pop()
    
    prevGreater[i] = IF stack empty THEN -1 ELSE stack.top
    stack.push(nums[i])
```

---

## Monotonic Stack Applications

### Daily Temperatures (739) — "How many days until warmer?"
```
stack = []    // stores indices, temps are decreasing

FOR today = 0 to n-1:
    WHILE stack not empty AND temps[stack.top] < temps[today]:
        coldDay = stack.pop()
        result[coldDay] = today - coldDay    // days waited
    
    stack.push(today)
```

### Largest Rectangle in Histogram (84) — sentinel 0 at end
```
stack = []    // stores indices, heights are increasing

FOR i = 0 to n (extra 0 sentinel):
    height = heights[i] OR 0

    WHILE stack not empty AND heights[stack.top] > height:
        h = heights[stack.pop()]
        width = IF stack empty THEN i ELSE i - stack.top - 1
        maxArea = max(maxArea, h * width)
    
    stack.push(i)
```

### Trapping Rain Water (42) — two approaches
```
// Approach A: Two pointers O(1) space (preferred)
// Approach B: Monotonic stack
WHILE stack not empty AND heights[i] > heights[stack.top]:
    bottom = stack.pop()
    IF stack empty: BREAK
    left = stack.top
    width = i - left - 1
    boundedHeight = min(heights[left], heights[i]) - heights[bottom]
    water += width * boundedHeight
```

---

## Monotonic Deque Pattern

### Pattern: Sliding Window Maximum (239)

**Invariant:** Deque stores indices in decreasing order of values.
Front is always the max in current window.

```
deque = []    // indices, values are decreasing from front to back

FOR right = 0 to n-1:
    // Remove indices outside window
    WHILE deque not empty AND deque.front <= right - k:
        deque.removeFront()
    
    // Remove indices whose value <= current (they can never be the max)
    WHILE deque not empty AND nums[deque.back] <= nums[right]:
        deque.removeBack()
    
    deque.addBack(right)
    
    IF right >= k - 1:
        result.add(nums[deque.front])    // front is always the window max
```

**Why remove smaller elements?** If nums[i] <= nums[right] and i < right, nums[i] will NEVER be the max when nums[right] is in the window. Safe to discard.

---

## Key Decision: Which Stack Type?

| Want | Stack order | Pop condition |
|---|---|---|
| Next Greater | Decreasing | `stack.top < current` |
| Next Smaller | Increasing | `stack.top > current` |
| Prev Greater | Decreasing | Process before push |
| Sliding Window Max | Decreasing Deque | Remove front if outside window |

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Daily Temperatures | Medium | Monotonic Stack (Next Greater) | Amazon, Google, Meta |
| Next Greater Element I/II | Easy/Medium | Monotonic Stack | Amazon |
| Largest Rectangle in Histogram | Hard | Monotonic Stack (area) | All FAANG |
| Trapping Rain Water | Hard | Monotonic Stack / Two Pointer | All FAANG |
| Sliding Window Maximum | Hard | Monotonic Deque | Amazon, Google |
| Sum of Subarray Minimums | Medium | Monotonic Stack | Amazon, Google |
| Remove Duplicate Letters | Medium | Greedy + Mono Stack | Google |
| 132 Pattern | Medium | Monotonic Stack + Min Prefix | Google |
