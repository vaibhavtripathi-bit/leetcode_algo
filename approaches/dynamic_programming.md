# Dynamic Programming Approach Guide

## Core Idea
DP = Recursion + Memoization (or Tabulation). Break problem into overlapping subproblems, solve each once, store results.

**When to Use:**
- Problem asks for max/min/count of ways
- Overlapping subproblems exist
- Optimal substructure: solution built from optimal sub-solutions

---

## 3-Step DP Framework

```
1. Define the state: dp[i] means "..."
2. Write the recurrence: dp[i] = f(dp[i-1], dp[i-2], ...)
3. Identify base cases: dp[0], dp[1] = ...
```

---

## Pattern 1: 1D DP (Linear Sequence)

### Pseudocode: Fibonacci-style
```
dp[0] = base
dp[1] = base

FOR i = 2 to n:
    dp[i] = dp[i-1] + dp[i-2]    // or some function
```

### Pseudocode: Climbing Stairs
```
dp[i] = ways to reach step i
dp[1] = 1, dp[2] = 2

FOR i = 3 to n:
    dp[i] = dp[i-1] + dp[i-2]    // come from 1 step or 2 steps below
```

### Pseudocode: House Robber
```
dp[i] = max money robbing houses 0..i
dp[0] = nums[0], dp[1] = max(nums[0], nums[1])

FOR i = 2 to n-1:
    dp[i] = max(dp[i-1], dp[i-2] + nums[i])
    //       skip i    | rob i + best without i-1
```

---

## Pattern 2: Brute Force → Memoization → Tabulation (for DP problems)

### Fibonacci Example
```
// Brute Force (exponential)
fib(n) = fib(n-1) + fib(n-2)

// Memoization (top-down)
memo = {}
fib(n):
    IF n in memo: RETURN memo[n]
    IF n <= 1: RETURN n
    memo[n] = fib(n-1) + fib(n-2)
    RETURN memo[n]

// Tabulation (bottom-up)
dp[0] = 0, dp[1] = 1
FOR i = 2 to n:
    dp[i] = dp[i-1] + dp[i-2]
```

---

## Pattern 3: 2D DP (Two Sequences / Grid)

### Pseudocode: Longest Common Subsequence
```
dp[i][j] = LCS of s1[0..i-1] and s2[0..j-1]

IF s1[i-1] == s2[j-1]:
    dp[i][j] = dp[i-1][j-1] + 1
ELSE:
    dp[i][j] = max(dp[i-1][j], dp[i][j-1])
```

### Pseudocode: Unique Paths (Grid)
```
dp[r][c] = ways to reach cell (r, c)
dp[0][c] = 1 for all c  (only one way along top row)
dp[r][0] = 1 for all r  (only one way along left column)

FOR r = 1 to rows - 1:
    FOR c = 1 to cols - 1:
        dp[r][c] = dp[r-1][c] + dp[r][c-1]
```

---

## Pattern 4: Knapsack (0/1 Knapsack)

### Pseudocode: 0/1 Knapsack
```
dp[i][w] = max value with i items and capacity w

FOR i = 1 to n:
    FOR w = 0 to capacity:
        // Don't take item i
        dp[i][w] = dp[i-1][w]
        
        // Take item i (if it fits)
        IF weights[i] <= w:
            dp[i][w] = max(dp[i][w], dp[i-1][w - weights[i]] + values[i])
```

### Pseudocode: Unbounded Knapsack (Coin Change)
```
dp[i] = minimum coins to make amount i
dp[0] = 0, dp[1..amount] = infinity

FOR i = 1 to amount:
    FOR each coin:
        IF coin <= i:
            dp[i] = min(dp[i], dp[i - coin] + 1)
```

---

## Pattern 5: Interval DP

### Pseudocode: Longest Palindromic Substring
```
dp[i][j] = true if s[i..j] is palindrome

FOR i = n-1 downTo 0:
    FOR j = i to n-1:
        IF s[i] == s[j]:
            dp[i][j] = (j - i <= 2) OR dp[i+1][j-1]
        ELSE:
            dp[i][j] = false
```

---

## Pattern 6: State Machine DP (Stock Problems)

### Pseudocode: Buy/Sell Stock with Cooldown
```
// States: held (have stock), sold (just sold), rest (cooldown)
held  = max(-prices[0], -infinity)
sold  = -infinity
rest  = 0

FOR each price:
    prev_held = held
    prev_sold = sold
    prev_rest = rest
    
    held = max(prev_held, prev_rest - price)  // keep or buy
    sold = prev_held + price                   // sell
    rest = max(prev_rest, prev_sold)           // rest or come from cooldown
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Climbing Stairs | Easy | 1D DP | All FAANG |
| House Robber | Medium | 1D DP | All FAANG |
| Coin Change | Medium | Unbounded Knapsack | All FAANG |
| Longest Common Subsequence | Medium | 2D DP | Google, Amazon |
| Unique Paths | Medium | Grid DP | All FAANG |
| Longest Increasing Subsequence | Medium | 1D DP / BinarySearch | All FAANG |
| Word Break | Medium | 1D DP | All FAANG |
| Partition Equal Subset Sum | Medium | 0/1 Knapsack | Amazon, Google |
| Maximum Product Subarray | Medium | 1D DP | All FAANG |
| Edit Distance | Hard | 2D DP | Google, Amazon |
| Longest Palindromic Substring | Medium | Interval DP | All FAANG |
| Burst Balloons | Hard | Interval DP | Google |
