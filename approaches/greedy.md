# Greedy Approach Guide

## Core Idea
At each step, make the locally optimal choice. For greedy to work, the problem must have the **greedy choice property** — a local optimal choice leads to a global optimal solution.

**Before using greedy, ask yourself:**
1. Can I prove that choosing the best option now doesn't hurt future choices?
2. Does the problem have optimal substructure?

If unsure, try greedy first, then verify with examples.

---

## Pattern 1: Interval Scheduling (Sort by End Time)

### Core Idea: Finish earliest = most future room

```
SORT intervals by END time

lastEnd = -infinity
count = 0

FOR each interval:
    IF interval.start >= lastEnd:   // no conflict
        count++
        lastEnd = interval.end

RETURN count
```

**Why end time and not start?** Ending early frees the most time for future intervals.

---

## Pattern 2: Jump Game

### Pseudocode: Can Reach End?
```
maxReach = 0

FOR i = 0 to length - 1:
    IF i > maxReach: RETURN false    // can't reach this position
    maxReach = max(maxReach, i + nums[i])

RETURN true
```

### Pseudocode: Minimum Jumps
```
jumps = 0
currentEnd = 0
farthest = 0

FOR i = 0 to length - 2:
    farthest = max(farthest, i + nums[i])
    
    IF i == currentEnd:        // must jump here
        jumps++
        currentEnd = farthest

RETURN jumps
```

---

## Pattern 3: Gas Station (Circular Greedy)

### Pseudocode
```
total = sum(gas) - sum(cost)
IF total < 0: RETURN -1   // impossible

tank = 0
start = 0

FOR i = 0 to n-1:
    tank += gas[i] - cost[i]
    IF tank < 0:
        start = i + 1    // can't start at any station 0..i
        tank = 0

RETURN start
```

**Why it works:** If total >= 0, a solution exists. The start point is where resetting the tank gives us the right position.

---

## Pattern 4: Candy / Resource Distribution

### Pseudocode: Candy (minimum candy, higher rating gets more)
```
// Two pass greedy
candy = [1, 1, ..., 1]   // everyone starts with 1

// Left to right: if rating[i] > rating[i-1], give more
FOR i = 1 to n-1:
    IF rating[i] > rating[i-1]:
        candy[i] = candy[i-1] + 1

// Right to left: fix right-side constraint
FOR i = n-2 downTo 0:
    IF rating[i] > rating[i+1]:
        candy[i] = max(candy[i], candy[i+1] + 1)

RETURN sum(candy)
```

---

## Pattern 5: Greedy with Priority Queue

### Pseudocode: Task Scheduler
```
Count frequency of each task
maxHeap = all frequencies

time = 0
WHILE heap not empty:
    nextCycle = []
    FOR i = 0 to n (cooling):
        IF heap not empty:
            freq = heap.pop()
            IF freq - 1 > 0: nextCycle.add(freq - 1)
    
    heap.addAll(nextCycle)
    time += IF heap empty THEN nextCycle.size ELSE n + 1
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Jump Game | Medium | Greedy Reach | All FAANG |
| Jump Game II | Medium | Greedy Jumps | All FAANG |
| Gas Station | Medium | Circular Greedy | Amazon, Google |
| Candy | Hard | Two-Pass Greedy | Amazon, Google |
| Task Scheduler | Medium | Heap + Greedy | Amazon, Google |
| Partition Labels | Medium | Greedy Intervals | Amazon, Google |
| Non-Overlapping Intervals | Medium | Sort by End | Google, Amazon |
