# Matrix Approach Guide

## Core Patterns

---

## Pattern 1: In-Place Rotation

### Key Insight: Rotate 90° clockwise = Transpose + Reverse each row

```
// Step 1: Transpose (flip across main diagonal)
FOR i = 0 to n-1:
    FOR j = i+1 to n-1:
        SWAP matrix[i][j] and matrix[j][i]

// Step 2: Reverse each row
FOR each row:
    REVERSE the row
```

### Rotate 90° counter-clockwise = Transpose + Reverse each column
```
Transpose first, then reverse each column (or reverse each row first, then transpose)
```

---

## Pattern 2: Spiral Traversal

### Pseudocode
```
top = 0, bottom = rows-1, left = 0, right = cols-1

WHILE top <= bottom AND left <= right:
    // Go right along top row
    FOR i = left to right: add matrix[top][i]
    top++
    
    // Go down along right column
    FOR i = top to bottom: add matrix[i][right]
    right--
    
    // Go left along bottom row (if row still valid)
    IF top <= bottom:
        FOR i = right downTo left: add matrix[bottom][i]
        bottom--
    
    // Go up along left column (if column still valid)
    IF left <= right:
        FOR i = bottom downTo top: add matrix[i][left]
        left++
```

---

## Pattern 3: Set Zeroes (Mark, then Apply)

### Core Idea: Don't zero out cells while scanning. Mark first, apply after.
```
// Step 1: Find which rows and columns should be zeroed
zeroRows = set of row indices that have 0
zeroCols = set of column indices that have 0

// Step 2: Zero out marked rows and columns
FOR each cell (r, c):
    IF r in zeroRows OR c in zeroCols:
        matrix[r][c] = 0
```

### Space-Optimized: Use first row/column as markers
```
// Use matrix[0][c] to mark column c needs zeroing
// Use matrix[r][0] to mark row r needs zeroing
```

---

## Pattern 4: Search in Sorted Matrix

### Start from top-right corner
```
row = 0, col = cols - 1

WHILE row < rows AND col >= 0:
    IF matrix[row][col] == target: RETURN true
    IF matrix[row][col] > target: col--    // too big, move left
    ELSE: row++                            // too small, move down
```

**Why top-right?** Going left decreases value, going down increases value — clear decisions each step.

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Rotate Image | Medium | Transpose + Reverse | All FAANG |
| Spiral Matrix | Medium | Boundary Shrink | All FAANG |
| Set Matrix Zeroes | Medium | Mark then Apply | All FAANG |
| Search a 2D Matrix | Medium | Binary Search | Amazon, Microsoft |
| Search a 2D Matrix II | Medium | Top-Right Start | Amazon, Google |
| Game of Life | Medium | In-Place Simulation | Amazon, Google |
| Word Search | Medium | DFS Backtracking | All FAANG |
