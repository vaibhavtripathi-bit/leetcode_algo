# Heap (Priority Queue) Approach Guide

## Core Idea
A heap is a complete binary tree where parent is always smaller (min-heap) or larger (max-heap) than its children. In Kotlin/Java, use `PriorityQueue`.

- **Min-Heap**: `PriorityQueue<Int>()` — smallest at top
- **Max-Heap**: `PriorityQueue<Int>(compareByDescending { it })` or `PriorityQueue(reverseOrder())`

---

## Pattern 1: Kth Largest / Smallest Element

### Core Idea
Use a min-heap of size k. When size exceeds k, remove the smallest. The top of the heap is the kth largest.

### Pseudocode: Kth Largest
```
minHeap of size k

FOR each element in array:
    heap.add(element)
    IF heap.size > k:
        heap.poll()    // remove smallest — keeps k largest

RETURN heap.top    // kth largest is smallest of the k largest
```

### Pseudocode: Kth Smallest
```
maxHeap of size k

FOR each element in array:
    heap.add(element)
    IF heap.size > k:
        heap.poll()    // remove largest — keeps k smallest

RETURN heap.top    // kth smallest is largest of the k smallest
```

---

## Pattern 2: Top K Frequent Elements

### Pseudocode
```
// Step 1: Count frequencies
freqMap = count occurrences of each element

// Step 2: Use min-heap of size k sorted by frequency
minHeapByFreq of size k

FOR each (element, frequency) in freqMap:
    heap.add((element, frequency))
    IF heap.size > k:
        heap.poll()    // removes least frequent

RETURN all elements in heap
```

---

## Pattern 3: Merge K Sorted Lists / Arrays

### Core Idea
Add the first element of each list to the heap. Always take the smallest, then add that list's next element.

### Pseudocode
```
minHeap (sorted by value)

// Initialize — add head of each list
FOR each list:
    IF list not empty:
        heap.add((value, listIndex, elementIndex))

result = []
WHILE heap not empty:
    (val, listIdx, elemIdx) = heap.poll()
    result.add(val)
    
    // Add next element from the same list
    IF elemIdx + 1 < lists[listIdx].size:
        heap.add((lists[listIdx][elemIdx+1], listIdx, elemIdx+1))

RETURN result
```

---

## Pattern 4: Two Heaps (Median Finding)

### Core Idea
Maintain two heaps:
- **Max-heap** for lower half
- **Min-heap** for upper half

Balance them so sizes differ by at most 1. Median is top of larger heap (or average of both tops).

### Pseudocode: Add Number
```
// Always add to maxHeap first
maxHeap.add(num)

// Balance: max of lower half <= min of upper half
IF maxHeap.top > minHeap.top:
    minHeap.add(maxHeap.poll())

// Balance sizes (maxHeap can be 1 larger at most)
IF minHeap.size > maxHeap.size:
    maxHeap.add(minHeap.poll())
```

### Pseudocode: Get Median
```
IF maxHeap.size > minHeap.size:
    RETURN maxHeap.top
ELSE:
    RETURN (maxHeap.top + minHeap.top) / 2.0
```

---

## Pattern 5: K Closest Points

### Pseudocode
```
// Use max-heap of size k sorted by distance
maxHeapByDistance of size k

FOR each point:
    dist = sqrt(x² + y²)
    heap.add((dist, point))
    IF heap.size > k:
        heap.poll()    // remove farthest

RETURN all points in heap
```

---

## Pattern 6: Task Scheduler (Greedy + Heap)

### Pseudocode
```
countFrequency for each task
maxHeap = all frequencies

time = 0
WHILE heap not empty:
    cycle = []
    FOR i = 0 to n (cooling period):
        IF heap not empty:
            freq = heap.poll()
            IF freq - 1 > 0:
                cycle.add(freq - 1)
    
    heap.addAll(cycle)
    
    // If heap empty, last cycle might be shorter
    time += IF heap.empty THEN cycle.size ELSE n + 1

RETURN time
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Kth Largest in Stream | Easy | Min-Heap of size k | Amazon, Google |
| Last Stone Weight | Easy | Max-Heap | Amazon |
| Kth Largest Element | Medium | Min-Heap | All FAANG |
| Top K Frequent Elements | Medium | Heap + Count | All FAANG |
| K Closest Points to Origin | Medium | Max-Heap of size k | Google, Amazon |
| Task Scheduler | Medium | Greedy + Max-Heap | Amazon, Google |
| Find Median from Data Stream | Hard | Two Heaps | All FAANG |
| Merge K Sorted Lists | Hard | Min-Heap | All FAANG |
| Smallest Range Covering K Lists | Hard | Min-Heap | Google |
