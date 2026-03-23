# Two Pointers Approach Guide

## Core Idea
Use two index variables to avoid nested loops. Instead of O(n²) brute force, move pointers strategically to achieve O(n).

---

## Pattern 1: Converging Pointers (Opposite Ends)

### When to Use
- Sorted array — find pair with target sum
- Palindrome check
- Container with most water
- Trapping rain water

### Core Logic
```
left = 0
right = length - 1

WHILE left < right:
    IF condition satisfied:
        record answer
    ELSE IF need to increase value:
        left++
    ELSE:
        right--
```

### Pseudocode: Two Sum (Sorted Array)
```
left = 0, right = length - 1

WHILE left < right:
    sum = arr[left] + arr[right]
    
    IF sum == target: RETURN [left, right]
    IF sum < target:  left++   // need bigger number
    IF sum > target:  right--  // need smaller number
```

### Pseudocode: Valid Palindrome
```
left = 0, right = length - 1

WHILE left < right:
    SKIP non-alphanumeric on left
    SKIP non-alphanumeric on right
    
    IF toLowerCase(s[left]) != toLowerCase(s[right]):
        RETURN false
    
    left++
    right--

RETURN true
```

---

## Pattern 2: Same Direction (Fast-Slow / Partition)

### When to Use
- Remove duplicates in-place
- Move zeros
- Partition array

### Core Logic
```
slow = 0   // boundary of valid section

FOR fast = 0 to length - 1:
    IF arr[fast] is valid/needed:
        arr[slow] = arr[fast]
        slow++

// Elements 0..slow-1 are the result
```

### Pseudocode: Move Zeroes
```
slow = 0

FOR fast = 0 to length - 1:
    IF arr[fast] != 0:
        arr[slow] = arr[fast]
        slow++

// Fill rest with zeros
FOR i = slow to length - 1:
    arr[i] = 0
```

---

## Pattern 3: Three Pointers (Extension of Two)

### Pseudocode: 3Sum
```
SORT array

FOR i = 0 to length - 3:
    SKIP duplicates for i
    
    left = i + 1
    right = length - 1
    
    WHILE left < right:
        sum = arr[i] + arr[left] + arr[right]
        
        IF sum == 0:
            record [arr[i], arr[left], arr[right]]
            SKIP duplicates for left
            SKIP duplicates for right
            left++, right--
        ELSE IF sum < 0:
            left++
        ELSE:
            right--
```

---

## Pattern 4: Container / Area (Max Optimization)

### Pseudocode: Container with Most Water
```
left = 0, right = length - 1
maxWater = 0

WHILE left < right:
    height = min(arr[left], arr[right])
    width = right - left
    maxWater = max(maxWater, height * width)
    
    // Move pointer with smaller height
    IF arr[left] < arr[right]:
        left++
    ELSE:
        right--

RETURN maxWater
```

**Why move the shorter side?** Moving the taller side can only keep or decrease width, while height stays the same or gets worse. The shorter side has the only chance to improve.

---

## Pattern 5: Trapping Rain Water

### Pseudocode: Two Pointer (O(n) time, O(1) space)
```
left = 0, right = length - 1
maxLeft = 0, maxRight = 0
trapped = 0

WHILE left < right:
    IF height[left] <= height[right]:
        IF height[left] >= maxLeft:
            maxLeft = height[left]
        ELSE:
            trapped += maxLeft - height[left]
        left++
    ELSE:
        IF height[right] >= maxRight:
            maxRight = height[right]
        ELSE:
            trapped += maxRight - height[right]
        right--

RETURN trapped
```

**Key insight:** Water trapped at any position = min(maxLeft, maxRight) - height[i]. Two pointers let us compute this without extra arrays.

---

## When NOT to Use Two Pointers
- Array is unsorted and sorting would change semantics
- Need all pairs, not just any pair
- Conditions depend on non-adjacent elements

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Valid Palindrome | Easy | Converging | All FAANG |
| Two Sum II | Easy | Converging | Amazon, Google |
| Move Zeroes | Easy | Same Direction | Meta, Google |
| 3Sum | Medium | Three Pointers | All FAANG |
| Container With Most Water | Medium | Converging + Max | All FAANG |
| Sort Colors (Dutch Flag) | Medium | Three Pointers | Google, Amazon |
| Trapping Rain Water | Hard | Two Pointers | All FAANG |
| 4Sum | Medium | Four Pointers | Amazon |
