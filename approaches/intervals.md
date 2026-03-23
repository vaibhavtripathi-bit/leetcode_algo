# Intervals Approach Guide

## Core Idea
Interval problems involve ranges [start, end]. Key insight: **sorting by start time** reduces most problems from O(n²) to O(n log n).

---

## Pattern 1: Sort + Merge (Overlapping Intervals)

### When to Use
- Merge overlapping intervals
- Check if any two intervals overlap
- Count minimum rooms/platforms needed

### Two intervals overlap when:
```
A.start <= B.end AND B.start <= A.end
```

### Pseudocode: Merge Intervals
```
SORT intervals by start time

result = [intervals[0]]

FOR each interval from index 1:
    last = result.last
    
    IF interval.start <= last.end:   // overlapping
        last.end = max(last.end, interval.end)   // extend
    ELSE:
        result.add(interval)    // no overlap, add new

RETURN result
```

---

## Pattern 2: Line Sweep

### Core Idea
"Sweep" a vertical line from left to right across the number line. Track events (interval starts and ends) and process them in order. Counts how many intervals are active at any point.

### When to Use
- Count minimum meeting rooms (max simultaneous intervals)
- Find busiest time period
- Skyline problem
- Car pooling / passenger count

### Pseudocode: Meeting Rooms II (Min Rooms Needed)
```
// Create events: +1 for start, -1 for end
events = []
FOR each interval [s, e]:
    events.add((s, +1))   // person arrives
    events.add((e, -1))   // person leaves

// Sort events: by time, ties: ends before starts
SORT events by (time, type)

maxRooms = 0
currentRooms = 0

FOR each (time, change) in events:
    currentRooms += change
    maxRooms = max(maxRooms, currentRooms)

RETURN maxRooms
```

### Pseudocode: Meeting Rooms II (Two Sorted Arrays)
```
SORT starts
SORT ends

rooms = 0, maxRooms = 0
i = 0, j = 0

WHILE i < n:
    IF starts[i] < ends[j]:
        rooms++
        i++
    ELSE:
        rooms--
        j++
    maxRooms = max(maxRooms, rooms)

RETURN maxRooms
```

---

## Pattern 3: Insert Interval (Find position + merge)

### Pseudocode
```
result = []
i = 0

// Step 1: Add all intervals that end before new interval starts
WHILE i < n AND intervals[i].end < newInterval.start:
    result.add(intervals[i++])

// Step 2: Merge all overlapping with new interval
WHILE i < n AND intervals[i].start <= newInterval.end:
    newInterval.start = min(newInterval.start, intervals[i].start)
    newInterval.end = max(newInterval.end, intervals[i].end)
    i++

result.add(newInterval)

// Step 3: Add remaining
WHILE i < n:
    result.add(intervals[i++])

RETURN result
```

---

## Pattern 4: Interval Scheduling (Greedy)

### Pseudocode: Non-Overlapping Intervals (Max intervals to keep)
```
SORT by END time (greedy: keep intervals ending earliest)

count = 1
lastEnd = intervals[0].end

FOR each interval from index 1:
    IF interval.start >= lastEnd:   // no overlap
        count++
        lastEnd = interval.end

RETURN count
// Removals = total - count
```

---

## Pattern 5: Skyline Problem (Hard - Line Sweep with Heap)

### Core Idea
Buildings are intervals [left, right, height]. As we sweep left to right:
- At left edge: add building height to active set
- At right edge: remove building height from active set
- Record point when max height changes

### Pseudocode
```
events = []
FOR each building [l, r, h]:
    events.add((l, -h))   // negative = start (process starts before ends)
    events.add((r, +h))   // positive = end

SORT events

maxHeap = [0]    // ground level always present
result = []
prevMax = 0

FOR each (x, h) in events:
    IF h < 0:             // building starts
        maxHeap.add(-h)
    ELSE:                  // building ends
        maxHeap.remove(h)
    
    currentMax = maxHeap.peek()
    IF currentMax != prevMax:
        result.add([x, currentMax])
        prevMax = currentMax

RETURN result
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Merge Intervals | Medium | Sort + Merge | All FAANG |
| Insert Interval | Medium | Insert + Merge | All FAANG |
| Non-Overlapping Intervals | Medium | Greedy / Sort by End | Google, Amazon |
| Meeting Rooms I | Easy | Sort + Check | All FAANG |
| Meeting Rooms II | Medium | Line Sweep / Two Pointers | All FAANG |
| Minimum Interval to Include Query | Hard | Line Sweep + Heap | Google |
| The Skyline Problem | Hard | Line Sweep + Max-Heap | Google, Amazon |
| Car Pooling | Medium | Line Sweep | Amazon |
| Employee Free Time | Hard | Merge + Traverse | Google |
