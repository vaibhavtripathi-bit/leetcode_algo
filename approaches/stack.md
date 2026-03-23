# Stack Approach Guide

## Core Idea
Stack = LIFO (Last In, First Out). Use when you need to remember previous elements while processing forward, especially for matching, nesting, or "next greater/smaller" problems.

---

## Pattern 1: Balanced Parentheses / Matching

### When to Use
- Validate bracket sequences
- Decode nested strings
- HTML tag matching

### Pseudocode: Valid Parentheses
```
stack = empty
pairs = { ')':'(', ']':'[', '}':'{' }

FOR each char in string:
    IF char is opening bracket:
        stack.push(char)
    ELSE IF char is closing bracket:
        IF stack is empty OR stack.top != pairs[char]:
            RETURN false
        stack.pop()

RETURN stack is empty
```

---

## Pattern 2: Monotonic Stack (Most Common in FAANG)

### Core Idea
Maintain a stack that is always sorted (increasing or decreasing). When a new element violates the order, pop until the order is restored — that's when you find the "next greater/smaller" element.

### Pseudocode: Next Greater Element
```
stack = empty (stores indices)
result = [-1, -1, ..., -1]

FOR i = 0 to length - 1:
    WHILE stack not empty AND arr[stack.top] < arr[i]:
        idx = stack.pop()
        result[idx] = arr[i]    // arr[i] is next greater for idx
    
    stack.push(i)

RETURN result
```

### Pseudocode: Daily Temperatures (Next Warmer Day)
```
stack = empty (indices of unresolved days)
result = [0, 0, ..., 0]

FOR i = 0 to length - 1:
    WHILE stack not empty AND temps[stack.top] < temps[i]:
        prev = stack.pop()
        result[prev] = i - prev   // days waited
    
    stack.push(i)
```

---

## Pattern 3: Min/Max Stack

### Core Idea
Maintain a second stack that only pushes when a new min/max is found. The top of the auxiliary stack is always the current min/max.

### Pseudocode: Min Stack
```
mainStack = empty
minStack = empty

PUSH(val):
    mainStack.push(val)
    IF minStack is empty OR val <= minStack.top:
        minStack.push(val)

POP():
    val = mainStack.pop()
    IF val == minStack.top:
        minStack.pop()

GET_MIN():
    RETURN minStack.top
```

---

## Pattern 4: Stack for Expression Evaluation

### Pseudocode: Evaluate Reverse Polish Notation
```
stack = empty

FOR each token:
    IF token is number:
        stack.push(number)
    ELSE:  // token is operator
        b = stack.pop()
        a = stack.pop()
        result = apply operator to (a, b)
        stack.push(result)

RETURN stack.top
```

---

## Pattern 5: Largest Rectangle / Area Problems

### Pseudocode: Largest Rectangle in Histogram
```
stack = empty (indices, monotonic increasing by height)
maxArea = 0

FOR i = 0 to length (including sentinel 0 at end):
    WHILE stack not empty AND heights[stack.top] > heights[i]:
        height = heights[stack.pop()]
        width = IF stack empty THEN i ELSE i - stack.top - 1
        maxArea = max(maxArea, height * width)
    
    stack.push(i)

RETURN maxArea
```

---

## Pattern 6: Stack Simulating Queue

### Pseudocode: Queue Using Two Stacks
```
inbox = stack for push
outbox = stack for pop/peek

PUSH(val):
    inbox.push(val)

POP():
    IF outbox is empty:
        MOVE all elements from inbox to outbox (reverses order)
    RETURN outbox.pop()
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Valid Parentheses | Easy | Matching | All FAANG |
| Min Stack | Easy | Auxiliary Stack | All FAANG |
| Queue Using Stacks | Easy | Two Stacks | Amazon, Microsoft |
| Evaluate RPN | Medium | Expression Eval | Amazon, Google |
| Daily Temperatures | Medium | Monotonic Stack | Amazon, Google |
| Car Fleet | Medium | Monotonic Stack | Google |
| Decode String | Medium | Nested Parsing | Google, Amazon |
| Largest Rectangle in Histogram | Hard | Monotonic Stack | All FAANG |
| Trapping Rain Water | Hard | Monotonic Stack | All FAANG |
