# Recursion Approach Guide

## Core Idea
A function calls itself with a smaller version of the same problem. Every recursive solution has:
1. **Base case:** smallest problem you can solve directly
2. **Recursive case:** reduce problem and call self
3. **Combine:** build answer from sub-answers

**Golden rule:** Trust that the recursive call returns the correct answer for the smaller problem. Don't trace the full recursion in your head.

---

## 3-Step Recursion Framework

```
1. DEFINE: What does the function return / what does it do?
2. BASE CASE: When is the problem trivially solved?
3. RECURSIVE CASE: How to reduce + how to combine?
```

### Example: Reverse a linked list
```
DEFINE: reverse(head) returns the head of the reversed list

BASE CASE: IF head == null OR head.next == null: RETURN head

RECURSIVE CASE:
    newHead = reverse(head.next)    // trust this reversed rest
    head.next.next = head            // point reversed tail back to head
    head.next = null                  // cut old link
    RETURN newHead
```

---

## Pattern 1: Tree Recursion (Return Value Up)

```
FUNCTION solve(node):
    IF node == null: RETURN base_value
    
    leftResult = solve(node.left)
    rightResult = solve(node.right)
    
    RETURN combine(leftResult, rightResult, node.val)
```

**Examples:** height, diameter, max path sum, check balance, same tree, invert tree

---

## Pattern 2: Path Tracking (Pass Value Down)

```
FUNCTION solve(node, accumulated):
    IF node == null: handle empty
    
    IF isLeaf(node):
        check if accumulated meets condition
        RETURN result
    
    accumulated = update(accumulated, node.val)
    
    solve(node.left, accumulated)
    solve(node.right, accumulated)
```

**Examples:** path sum, collect all paths, max depth

---

## Pattern 3: Memoization (Recursion + Cache)

```
memo = {}

FUNCTION solve(state):
    IF state in memo: RETURN memo[state]
    IF base_case(state): RETURN base_value
    
    result = combine(solve(smaller_state_1), solve(smaller_state_2))
    
    memo[state] = result
    RETURN result
```

**When to use:** Overlapping subproblems (same state computed multiple times).

---

## Pattern 4: Tail Recursion

```
// Normal recursion (builds call stack)
factorial(n):
    IF n == 0: RETURN 1
    RETURN n * factorial(n - 1)    // must wait for result

// Tail recursion (accumulator pattern, can be optimized)
factorial(n, acc = 1):
    IF n == 0: RETURN acc
    RETURN factorial(n - 1, n * acc)    // last call, nothing to do after
```

---

## Pattern 5: Mutual Recursion

```
isEven(n):
    IF n == 0: RETURN true
    RETURN isOdd(n - 1)

isOdd(n):
    IF n == 0: RETURN false
    RETURN isEven(n - 1)
```

---

## Recognizing Recursion Opportunities

Ask these questions:
1. Can I solve a smaller version of this problem first?
2. Does the problem involve a tree, linked list, or nested structure?
3. Does the problem involve "all combinations/permutations/subsets"? → Backtracking
4. Does the problem have overlapping subproblems? → DP + Memoization

---

## Recursion vs Iteration Tradeoffs

| | Recursion | Iteration |
|---|---|---|
| Code clarity | Usually clearer | More verbose |
| Stack space | O(depth) call stack | O(1) usually |
| Risk | Stack overflow for deep trees | None |
| When to convert | Very deep recursion only | N/A |

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Fibonacci / Climbing Stairs | Easy | Memoized Recursion → DP | All FAANG |
| Reverse Linked List | Easy | Return-value recursion | All FAANG |
| Tree Height / Balance | Easy | Return-value recursion | All FAANG |
| Power(x, n) | Medium | Divide recursion | All FAANG |
| Generate Parentheses | Medium | Backtracking recursion | All FAANG |
| Decode Ways | Medium | Memoized recursion | Amazon, Meta |
| Regular Expression Matching | Hard | Memoized recursion | Google, Meta |
