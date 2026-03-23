# Divide and Conquer Approach Guide

## Core Idea
Split the problem into smaller subproblems, solve them independently, then combine the results.

```
solve(problem):
    IF problem is small enough: solve directly (base case)
    
    left, right = split(problem)
    leftResult = solve(left)
    rightResult = solve(right)
    
    RETURN combine(leftResult, rightResult)
```

**Time:** T(n) = 2T(n/2) + O(merge) → Master Theorem → usually O(n log n)

---

## Pattern 1: Merge Sort

### Core Algorithm
```
mergeSort(arr, left, right):
    IF left >= right: RETURN
    
    mid = (left + right) / 2
    mergeSort(arr, left, mid)
    mergeSort(arr, mid+1, right)
    merge(arr, left, mid, right)

merge(arr, left, mid, right):
    create temp left and right halves
    
    i = 0, j = 0, k = left
    WHILE i < leftHalf.size AND j < rightHalf.size:
        IF leftHalf[i] <= rightHalf[j]:
            arr[k++] = leftHalf[i++]
        ELSE:
            arr[k++] = rightHalf[j++]
    
    copy remaining elements
```
**Time:** O(n log n), **Space:** O(n)

---

## Pattern 2: Count Inversions (Merge Sort + Count)

### Core Idea: During merge, whenever we pick from right half, all remaining left half elements are inversions with it.

```
mergeAndCount(arr, left, mid, right):
    inversions = 0
    i = left, j = mid+1, k = 0
    
    WHILE i <= mid AND j <= right:
        IF arr[i] <= arr[j]:
            temp[k++] = arr[i++]
        ELSE:
            // arr[i..mid] are ALL greater than arr[j] → inversions
            inversions += (mid - i + 1)
            temp[k++] = arr[j++]
    
    RETURN inversions + copy remaining
```

---

## Pattern 3: Quick Sort (Partition-based)

```
quickSort(arr, low, high):
    IF low >= high: RETURN
    
    pivot = partition(arr, low, high)
    quickSort(arr, low, pivot - 1)
    quickSort(arr, pivot + 1, high)

partition(arr, low, high):   // Lomuto scheme
    pivot = arr[high]
    i = low - 1
    
    FOR j = low to high - 1:
        IF arr[j] <= pivot:
            i++
            SWAP arr[i], arr[j]
    
    SWAP arr[i+1], arr[high]
    RETURN i + 1
```

---

## Pattern 4: QuickSelect (Kth Smallest/Largest)

### Core Idea: Only recurse on the side containing the kth element.
```
quickSelect(arr, low, high, k):
    IF low == high: RETURN arr[low]
    
    pivot = partition(arr, low, high)
    
    IF k == pivot: RETURN arr[pivot]
    IF k < pivot:  RETURN quickSelect(arr, low, pivot-1, k)
    ELSE:          RETURN quickSelect(arr, pivot+1, high, k)
```
**Time:** O(n) average, O(n²) worst

---

## Pattern 5: Divide Problem Space (Binary Search variant)

### Pseudocode: Closest Pair of Points
```
// Split points by median x-coordinate
// Solve left and right halves recursively
// Combine: only check points in the strip around the dividing line

closestPair(points):
    IF size <= 3: brute force
    
    mid = median x-coordinate
    dLeft = closestPair(left half)
    dRight = closestPair(right half)
    d = min(dLeft, dRight)
    
    // Check strip of width 2d around dividing line
    strip = points within d of mid line, sorted by y
    FOR i in strip:
        FOR j = i+1: j-y - i-y < d:
            d = min(d, distance(strip[i], strip[j]))
    
    RETURN d
```

---

## Pattern 6: D&C for Trees (Natural Recursion)

Trees are naturally recursive — every tree algorithm is implicitly divide and conquer:
```
solve(root):
    IF root == null: return base case
    
    left = solve(root.left)
    right = solve(root.right)
    
    RETURN combine(left, root.val, right)
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Sort an Array | Medium | Merge Sort / Quick Sort | All FAANG |
| Kth Largest Element | Medium | QuickSelect | All FAANG |
| Count of Smaller Numbers After Self | Hard | Merge Sort + Count | Amazon, Google |
| Merge k Sorted Lists | Hard | D&C / Heap | All FAANG |
| Maximum Subarray | Easy | D&C (or Kadane's) | All FAANG |
| Median of Two Sorted Arrays | Hard | Binary Search D&C | All FAANG |
| Closest Pair of Points | Medium | Geometric D&C | Google |
