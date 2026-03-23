# Binary Search Approach Guide

## Core Idea
Binary search works on **sorted** data. Instead of checking every element O(n), eliminate half the search space each step → O(log n).

---

## Template 1: Standard (Find Exact Target)

```
left = 0
right = length - 1

WHILE left <= right:
    mid = left + (right - left) / 2    // avoids integer overflow
    
    IF arr[mid] == target:
        RETURN mid
    ELSE IF arr[mid] < target:
        left = mid + 1
    ELSE:
        right = mid - 1

RETURN -1   // not found
```

**Key:** `left <= right`, shrink with `mid + 1` and `mid - 1`

---

## Template 2: Find Leftmost (First Occurrence)

```
left = 0
right = length - 1
result = -1

WHILE left <= right:
    mid = left + (right - left) / 2
    
    IF arr[mid] == target:
        result = mid       // record, but keep searching left
        right = mid - 1
    ELSE IF arr[mid] < target:
        left = mid + 1
    ELSE:
        right = mid - 1

RETURN result
```

---

## Template 3: Find Rightmost (Last Occurrence)

```
left = 0
right = length - 1
result = -1

WHILE left <= right:
    mid = left + (right - left) / 2
    
    IF arr[mid] == target:
        result = mid       // record, but keep searching right
        left = mid + 1
    ELSE IF arr[mid] < target:
        left = mid + 1
    ELSE:
        right = mid - 1

RETURN result
```

---

## Template 4: Binary Search on Answer (Most Powerful)

### Core Idea
When the problem asks "minimum/maximum value that satisfies a condition" — binary search on the **answer space**, not the array.

### Pattern:
```
left = minimum_possible_answer
right = maximum_possible_answer

WHILE left < right:
    mid = left + (right - left) / 2
    
    IF canAchieve(mid):
        right = mid        // try smaller
    ELSE:
        left = mid + 1

RETURN left
```

**Examples:**
- Koko Eating Bananas: binary search on eating speed
- Minimum Days to Make Bouquets: binary search on days
- Capacity to Ship Packages: binary search on capacity

---

## Rotated Sorted Array Pattern

### Core Idea
Even though rotated, one half is always fully sorted. Use this to decide which half to search.

```
left = 0, right = n - 1

WHILE left <= right:
    mid = left + (right - left) / 2
    
    IF arr[mid] == target: RETURN mid
    
    // Check which half is sorted
    IF arr[left] <= arr[mid]:   // left half is sorted
        IF arr[left] <= target < arr[mid]:
            right = mid - 1
        ELSE:
            left = mid + 1
    ELSE:                       // right half is sorted
        IF arr[mid] < target <= arr[right]:
            left = mid + 1
        ELSE:
            right = mid - 1

RETURN -1
```

---

## 2D Matrix Binary Search

### Core Idea
Treat the 2D matrix as a 1D sorted array. Index mapping:
- `row = mid / cols`
- `col = mid % cols`

```
left = 0
right = rows * cols - 1

WHILE left <= right:
    mid = left + (right - left) / 2
    val = matrix[mid / cols][mid % cols]
    
    IF val == target: RETURN true
    ELSE IF val < target: left = mid + 1
    ELSE: right = mid - 1

RETURN false
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Binary Search | Easy | Template 1 | All FAANG |
| Search Insert Position | Easy | Template 1 | Amazon, Google |
| Search in Rotated Array | Medium | Rotated Pattern | All FAANG |
| Find First & Last Position | Medium | Template 2 & 3 | Google, Amazon |
| Find Minimum in Rotated Array | Medium | Modified Binary Search | All FAANG |
| Search a 2D Matrix | Medium | 2D → 1D Mapping | Amazon, Microsoft |
| Koko Eating Bananas | Medium | Binary Search on Answer | Google, Meta |
| Time Based Key-Value | Medium | Binary Search on Answer | Google |
| Median of Two Sorted Arrays | Hard | Binary Search on Partition | All FAANG |
| Find in Mountain Array | Hard | Binary Search Variant | Google |
