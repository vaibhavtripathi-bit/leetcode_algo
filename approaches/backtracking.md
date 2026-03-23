# Backtracking Approach Guide

## Core Idea
Explore all possibilities by building a solution incrementally. When a partial solution cannot lead to a valid answer, **backtrack** (undo the last choice) and try another path.

**Think of it as a decision tree** — at each node you make a choice, and backtrack if the path is invalid.

---

## Universal Template

```
FUNCTION backtrack(path, choices, result):
    IF path is complete/valid:
        result.add(copy of path)
        RETURN
    
    FOR each choice in choices:
        IF choice is valid:
            MAKE choice (add to path)
            backtrack(updated path, updated choices, result)
            UNDO choice (remove from path)    // <-- this is the backtrack step
```

---

## Pattern 1: Subsets / Power Set

### Pseudocode
```
FUNCTION subsets(nums, start, current, result):
    result.add(copy of current)    // add at every node
    
    FOR i = start to nums.size - 1:
        current.add(nums[i])
        subsets(nums, i + 1, current, result)
        current.removeLast()       // backtrack
```

---

## Pattern 2: Permutations

### Pseudocode
```
FUNCTION permute(nums, used, current, result):
    IF current.size == nums.size:
        result.add(copy of current)
        RETURN
    
    FOR i = 0 to nums.size - 1:
        IF used[i]: CONTINUE
        
        used[i] = true
        current.add(nums[i])
        permute(nums, used, current, result)
        current.removeLast()       // backtrack
        used[i] = false
```

---

## Pattern 3: Combinations

### Pseudocode: Combinations Sum (can reuse)
```
FUNCTION combinationSum(candidates, target, start, current, result):
    IF target == 0:
        result.add(copy of current)
        RETURN
    IF target < 0: RETURN
    
    FOR i = start to candidates.size - 1:
        current.add(candidates[i])
        combinationSum(candidates, target - candidates[i], i, current, result)
        // i not i+1 because we can reuse
        current.removeLast()       // backtrack
```

---

## Pattern 4: N-Queens Style (Position Validation)

### Pseudocode
```
FUNCTION placeQueens(row, cols, diag1, diag2, board, result):
    IF row == n:
        result.add(copy of board)
        RETURN
    
    FOR col = 0 to n - 1:
        IF col in cols OR (row-col) in diag1 OR (row+col) in diag2:
            CONTINUE    // invalid position
        
        PLACE queen at (row, col)
        cols.add(col), diag1.add(row-col), diag2.add(row+col)
        
        placeQueens(row + 1, ...)
        
        REMOVE queen from (row, col)
        cols.remove(col), diag1.remove(row-col), diag2.remove(row+col)
```

---

## Pattern 5: Word Search (Grid Backtracking)

### Pseudocode
```
FUNCTION dfs(grid, word, index, r, c):
    IF index == word.length: RETURN true    // found all chars
    IF out of bounds OR grid[r][c] != word[index]: RETURN false
    
    temp = grid[r][c]
    grid[r][c] = '#'    // mark visited
    
    found = dfs(r+1,c) OR dfs(r-1,c) OR dfs(r,c+1) OR dfs(r,c-1)
    
    grid[r][c] = temp   // restore (backtrack)
    RETURN found
```

---

## Key Optimization: Pruning
Always add a pruning condition to avoid exploring dead branches:
```
IF remaining < 0: RETURN         // Combination Sum
IF col in used_cols: CONTINUE    // N-Queens
IF grid[r][c] already visited: RETURN  // Word Search
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Subsets | Medium | Subsets | All FAANG |
| Permutations | Medium | Permutations | All FAANG |
| Combination Sum | Medium | Combinations | All FAANG |
| Letter Combinations Phone | Medium | Tree Branching | All FAANG |
| Word Search | Medium | Grid Backtracking | All FAANG |
| Palindrome Partitioning | Medium | Subsets + Validation | Amazon, Google |
| N-Queens | Hard | Position Validation | Amazon, Google |
| Sudoku Solver | Hard | Grid + Validation | Amazon, Google |
