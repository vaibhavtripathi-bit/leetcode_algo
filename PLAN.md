# LeetCode Solutions - Kotlin Project Plan

## Project Overview

High-probability FAANG interview LeetCode solutions in **Kotlin**, organized in an Android project with a separate library module.

---

## Project Structure

```
leetcode/
├── app/                          # Android app (for running/testing)
├── solutions/                    # Kotlin library module
│   └── src/main/java/com/leetcode/solutions/
│       ├── common/               # ListNode, TreeNode, etc.
│       └── <category>/           # Solutions by category
│           ├── traversals/       # Fundamental patterns
│           └── problems/         # FAANG interview questions
│               ├── easy/
│               ├── medium/
│               └── hard/
└── approaches/                   # Plain English + pseudo code
```

---

## Coding Guidelines

### 1. Simplicity Over Complexity
- Write **clean, readable code** that is easy to understand
- Avoid overly clever solutions that sacrifice readability

### 2. Modern Kotlin Features (Where Sensible)
Use Kotlin idioms that improve readability:
- `?.let { }`, `?.also { }` for null safety
- `when` expressions instead of complex if-else
- Extension functions, scope functions where they help
- Collection operations when clearer than loops

### 3. Small, Focused Functions
- **Never write one big function**
- Break problems into multiple small functions
- Each function has single responsibility

### 4. Separate Traversal from Business Logic

```kotlin
// ✅ GOOD: Separated concerns
fun solve(root: TreeNode?): Int {
    val values = collectValues(root)  // Traversal
    return processValues(values)       // Business logic
}
```

### 5. Multiple Solutions Per Problem
- **Always provide 3-4 (or more) popular solutions**
- Show different approaches: iterative, recursive, etc.

### 6. Brute Force — Only When Needed
- Skip brute force for simple problems
- Include only for DP or very complex problems

### 7. High Probability Questions Only
- FAANG interview questions from 2023-2026
- Quality over quantity

---

## Branch Organization

| Branch | Category |
|--------|----------|
| `linked-list` | Linked List |
| `tree` | Tree / Binary Tree |
| `graph` | Graph Theory |
| `dynamic-programming` | DP |
| `binary-search` | Binary Search |
| `two-pointers` | Two Pointers |
| `sliding-window` | Sliding Window |
| `stack` | Stack |
| `heap` | Heap / Priority Queue |
| ... | More to come |

---

## File Naming Convention

```
<problem_number>_<problem_name>.kt

Examples:
- 206_ReverseLinkedList.kt
- 21_MergeTwoSortedLists.kt
```

---

## Solution Template

```kotlin
package com.leetcode.solutions.<category>.problems.<difficulty>

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode <Number>: <Title>
 * https://leetcode.com/problems/<slug>/
 * 
 * Difficulty: Easy | Medium | Hard
 * Companies: Google, Amazon, Meta
 */

/** Solution 1: <Approach Name> - Time: O(?), Space: O(?) */
class Solution1 {
    fun solve(input: Type): ReturnType {
        // Implementation with helper functions
    }
    
    private fun helperFunction() { }
}

/** Solution 2: <Approach Name> - Time: O(?), Space: O(?) */
class Solution2 { }

/** Solution 3: <Approach Name> - Time: O(?), Space: O(?) */
class Solution3 { }
```
