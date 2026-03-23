# Two Pointer Approach

## When to Use
- Sorted arrays/lists
- Finding pairs with a condition
- Comparing from both ends
- In-place operations with O(1) space

## Core Patterns

### Pattern 1: Converging (Opposite Direction)
```
left = 0, right = end
while left < right:
    if condition met: record result
    move left or right based on comparison
```

### Pattern 2: Same Direction
```
slow = 0, fast = 0
while fast < length:
    if element valid: process at slow, slow++
    fast++
```

## Pseudo Code: Two Sum (Sorted)

```
FUNCTION twoSum(numbers, target):
    left = 0
    right = length - 1
    
    WHILE left < right:
        sum = numbers[left] + numbers[right]
        IF sum == target: RETURN [left, right]
        ELSE IF sum < target: left++
        ELSE: right--
```

## Complexity
- Time: O(n)
- Space: O(1)
