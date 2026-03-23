# Sliding Window Approach Guide

## Core Idea
Maintain a "window" (subarray/substring) with two pointers. Expand from the right, shrink from the left. Avoids recomputing the window from scratch each step → O(n).

---

## Pattern 1: Fixed-Size Window

### When to Use
- "Find max/min/avg in subarray of size k"
- "Count subarrays of exactly size k"

### Pseudocode
```
// Initialize window with first k elements
FOR i = 0 to k - 1:
    windowSum += arr[i]

result = windowSum

// Slide window: add right, remove left
FOR i = k to length - 1:
    windowSum += arr[i]        // add new right element
    windowSum -= arr[i - k]    // remove old left element
    result = max/min(result, windowSum)

RETURN result
```

---

## Pattern 2: Variable-Size Window (Most Common in FAANG)

### When to Use
- "Longest/shortest subarray/substring satisfying condition"
- "Minimum window containing all characters"

### Two Variants:

#### Variant A: Shrink when invalid
```
left = 0
FOR right = 0 to length - 1:
    ADD arr[right] to window
    
    WHILE window is invalid:
        REMOVE arr[left] from window
        left++
    
    UPDATE result with current window size (right - left + 1)
```

#### Variant B: Grow only when better (for finding minimum)
```
left = 0
FOR right = 0 to length - 1:
    ADD arr[right] to window
    
    IF window is valid:
        UPDATE result with current window size
        SHRINK from left
        REMOVE arr[left] from window
        left++
```

---

## Pattern 3: Window with HashMap (Character Frequency)

### Pseudocode: Longest Substring Without Repeating Characters
```
charIndex = empty map
left = 0
maxLen = 0

FOR right = 0 to length - 1:
    IF s[right] is in charIndex AND charIndex[s[right]] >= left:
        left = charIndex[s[right]] + 1   // shrink from left
    
    charIndex[s[right]] = right
    maxLen = max(maxLen, right - left + 1)

RETURN maxLen
```

### Pseudocode: Minimum Window Substring
```
need = frequency map of target string
have = empty map
formed = 0    // count of chars meeting their required frequency
left = 0
minLen = infinity
result = ""

FOR right = 0 to length - 1:
    ADD s[right] to have map
    
    IF s[right] in need AND have[s[right]] == need[s[right]]:
        formed++
    
    WHILE formed == total unique chars needed:
        UPDATE result if current window is smaller
        REMOVE s[left] from have map
        IF s[left] in need AND have[s[left]] < need[s[left]]:
            formed--
        left++

RETURN result
```

---

## Pattern 4: Sliding Window Maximum (Monotonic Deque)

### Core Idea
Maintain a deque of indices where values are in decreasing order. The front is always the max of the current window.

```
deque = empty (stores indices)
result = []

FOR right = 0 to length - 1:
    // Remove elements outside window
    WHILE deque not empty AND deque.front < right - k + 1:
        deque.removeFront()
    
    // Remove smaller elements (they'll never be useful)
    WHILE deque not empty AND arr[deque.back] < arr[right]:
        deque.removeBack()
    
    deque.addBack(right)
    
    IF right >= k - 1:
        result.add(arr[deque.front])    // front is always max
```

---

## Key Rules
1. Window grows by moving `right`
2. Window shrinks by moving `left`  
3. `left` never overtakes `right`
4. Result is computed when window is valid

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Best Time to Buy/Sell Stock | Easy | Single Pass (no window) | All FAANG |
| Longest Substring w/o Repeating | Medium | Variable + HashMap | All FAANG |
| Longest Repeating Char Replacement | Medium | Variable + Count | Google, Amazon |
| Permutation in String | Medium | Fixed + Frequency | Amazon, Meta |
| Fruit Into Baskets | Medium | Variable + HashMap | Google |
| Minimum Window Substring | Hard | Variable + HashMap | All FAANG |
| Sliding Window Maximum | Hard | Monotonic Deque | Amazon, Google |
| Substring with All K Distinct | Medium | Variable + Count | Amazon |
