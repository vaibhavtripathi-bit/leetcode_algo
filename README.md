# LeetCode FAANG Interview Prep — Kotlin

A comprehensive Android project containing high-probability FAANG (Google, Meta, Amazon, Apple, Microsoft) LeetCode solutions in Kotlin, covering all major algorithms, data structures, and patterns asked in interviews from 2022–2026.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Branches](#branches)
- [Approach Docs](#approach-docs)
- [Kotlin Utilities](#kotlin-utilities)
- [How to Run](#how-to-run)
- [Coding Guidelines](#coding-guidelines)
- [Problem Count by Branch](#problem-count-by-branch)
- [Topics Covered](#topics-covered)

---

## Project Structure

```
leetcode/
├── app/                                      # Android app module (run & demo solutions)
│   └── src/main/java/com/leetcode/app/
│       └── MainActivity.kt
│
├── solutions/                                # Kotlin library module — all algorithm code
│   └── src/main/java/com/leetcode/solutions/
│       │
│       ├── common/
│       │   ├── ListNode.kt                   # Singly linked list node + utilities
│       │   └── TreeNode.kt                   # Binary tree node + utilities
│       │
│       ├── utils/                            # ← Reusable helper functions (kotlin-utils branch)
│       │   ├── NumberUtils.kt                # Digits, reverse, palindrome, binary string
│       │   ├── CharStringUtils.kt            # Char↔index, freq array, anagram, palindrome
│       │   ├── ArrayUtils.kt                 # Swap, reverse, prefix sum, Dutch flag, binary search
│       │   ├── GridUtils.kt                  # DIRS_4/8, isValid, getNeighbors, encodeCell
│       │   ├── BitUtils.kt                   # Count bits, set/clear/toggle, isPowerOf2, subsets
│       │   ├── MathUtils.kt                  # GCD, LCM, safe mod, fast power, sieve, isPrime
│       │   ├── GraphUtils.kt                 # Build adj list, UnionFind class, topological sort
│       │   ├── CollectionUtils.kt            # Frequency map, merge intervals, heap factories
│       │   └── general/                      # General-purpose conversions (non-algo)
│       │       ├── BaseConversions.kt        # Any base ↔ Int, Roman numerals, English words
│       │       ├── TypeConversions.kt        # IntArray↔List, Array↔IntArray, Char↔ASCII, Pair/Triple
│       │       ├── StringManipUtils.kt       # Reverse words, title case, run-length encode/decode
│       │       ├── CollectionConversions.kt  # Flatten, zip/unzip, rotate, transpose, set ops
│       │       └── RangeUtils.kt             # Clamp, isInRange, circularIndex, ceilDiv, overlap
│       │
│       ├── arrays/problems/                  # ← arrays branch
│       ├── linkedlist/
│       │   ├── traversals/
│       │   └── problems/easy|medium|hard/
│       ├── tree/
│       │   ├── traversals/
│       │   └── problems/easy|medium|hard/
│       ├── graph/
│       │   ├── traversals/
│       │   ├── shortest_path/                # Dijkstra, Bellman-Ford
│       │   ├── union_find/
│       │   ├── mst/                          # Kruskal, Prim
│       │   ├── scc/                          # Tarjan, Critical Connections
│       │   └── problems/medium|hard/
│       ├── design/problems/                  # ← design branch (LRU, LFU, Min Stack, etc.)
│       ├── binarysearch/problems/
│       ├── twopointers/problems/
│       ├── slidingwindow/problems/
│       ├── stack/problems/
│       ├── heap/problems/
│       ├── dp/problems/
│       ├── backtracking/problems/
│       ├── trie/
│       │   ├── core/
│       │   └── problems/
│       ├── intervals/problems/
│       ├── bitmanipulation/problems/
│       ├── greedy/problems/
│       ├── matrix/problems/
│       ├── strings/
│       │   ├── algorithms/                   # KMP
│       │   └── problems/
│       └── math/
│           ├── algorithms/
│           └── problems/
│
└── approaches/                               # Plain English explanations + pseudocode
    ├── README.md
    ├── linked_list.md
    ├── tree.md
    ├── binary_search.md
    ├── two_pointers.md
    ├── sliding_window.md
    ├── stack.md
    ├── heap.md
    ├── graph.md
    ├── trie.md
    ├── intervals.md
    ├── bit_manipulation.md
    ├── greedy.md
    ├── matrix.md
    ├── string.md
    ├── math.md
    ├── dynamic_programming.md
    ├── backtracking.md
    ├── arrays.md
    ├── design.md
    ├── prefix_sum.md          ← NEW
    ├── hashing.md             ← NEW
    ├── monotonic_stack_queue.md  ← NEW
    ├── segment_tree_fenwick.md   ← NEW
    ├── divide_and_conquer.md  ← NEW
    ├── recursion.md           ← NEW
    └── kotlin_utils.md        ← NEW (index to all utils functions)
```

---

## Branches

Each topic lives on its own dedicated Git branch. Switch branches to study a specific area.

| Branch | Topic | Key Algorithms / Patterns |
|---|---|---|
| `main` | Entry point | README, all approach docs |
| `arrays` | Arrays | Two Sum, Kadane's, Prefix Sum, Product Except Self, Cyclic Sort |
| `linked-list` | Linked Lists | Fast-Slow Pointer, Floyd's Cycle, Reversal, Merge, Intersection |
| `tree` | Binary Trees | DFS (Pre/In/Post), BFS, LCA, BST Validate, Path Sum, Serialize |
| `binary-search` | Binary Search | Standard, Leftmost/Rightmost, On Answer, Rotated Array, 2D Matrix |
| `two-pointers` | Two Pointers | Converging, Same Direction, 3Sum, Trapping Rain Water |
| `sliding-window` | Sliding Window | Fixed, Variable, Freq Map, Monotonic Deque (Window Max) |
| `stack` | Stack | Monotonic Stack, Min Stack, Expression Evaluation, Histogram |
| `heap` | Heap / Priority Queue | Kth Largest, Top K Frequent, Two Heaps, Merge K Lists |
| `graph` | Graphs | DFS/BFS, Dijkstra, Bellman-Ford, Kruskal/Prim MST, Union-Find, Tarjan SCC, Bipartite |
| `trie` | Trie / Prefix Tree | Insert/Search/Prefix, Wildcard DFS, Word Search II |
| `intervals` | Intervals | Sort+Merge, Line Sweep, Insert Interval, Meeting Rooms, Skyline |
| `bit-manipulation` | Bit Manipulation | XOR tricks, Brian Kernighan, Bitmask DP, Subset generation |
| `greedy` | Greedy | Jump Game, Gas Station, Candy, Partition Labels, Task Scheduler |
| `matrix` | 2D Matrix | Rotate 90°, Spiral Order, Set Zeroes, Search Sorted Matrix |
| `string` | Strings | KMP, Manacher's, Anagram, Group Anagrams, Palindrome Partition |
| `math` | Math / Number Theory | GCD/LCM, Fast Power, Sieve, Digit Manipulation |
| `dynamic-programming` | Dynamic Programming | 1D DP, 2D DP, Knapsack (0/1 & Unbounded), Interval DP, State Machine DP |
| `backtracking` | Backtracking | Subsets, Permutations, Combinations, N-Queens, Sudoku, Word Search |
| `design` | Data Structure Design | LRU Cache, LFU Cache, Min Stack, Randomized Set, Median Finder |
| `kotlin-utils` | Reusable Utilities | 8 utils files + `general/` subfolder (see below) |

```bash
# Switch to any topic branch
git checkout arrays
git checkout design
git checkout kotlin-utils
git checkout dynamic-programming
```

---

## Approach Docs

The `approaches/` folder has plain-English explanations + pseudocode for every major pattern.
**Read the approach doc before the code.** This is how FAANG interviews work — explain first, then code.

Each doc contains:
- Core idea in simple language
- Pseudocode for every variant of the pattern
- "Why it works" for non-obvious algorithms
- FAANG frequency table (which companies ask which problems)

| Doc | What it covers |
|---|---|
| `linked_list.md` | Fast-Slow, Reversal, Dummy Node, Runner, Two Pointer |
| `tree.md` | DFS (3 orders), BFS, Return-Value Pattern, BST, LCA, Serialize |
| `binary_search.md` | 4 templates: Standard, Leftmost, Rightmost, On Answer |
| `two_pointers.md` | Converging, Same Direction, Three Pointers, Container/Area |
| `sliding_window.md` | Fixed, Variable (2 variants), HashMap, Monotonic Deque |
| `stack.md` | Balanced Parens, Monotonic Stack, Min Stack, RPN, Histogram |
| `heap.md` | Kth Largest, Top K Frequent, Merge K Lists, Two Heaps |
| `graph.md` | DFS/BFS, Dijkstra, Bellman-Ford, Floyd-Warshall, Topo Sort, Union-Find, MST, SCC, Bipartite |
| `trie.md` | Node structure, Insert/Search/Prefix, Wildcard DFS, Backtracking |
| `intervals.md` | Sort+Merge, Line Sweep, Insert Interval, Interval Scheduling |
| `bit_manipulation.md` | Core ops, Brian Kernighan, XOR tricks, Bitmask, DP with bitmask |
| `greedy.md` | Interval Scheduling, Jump Game, Gas Station, Candy, PQ Greedy |
| `matrix.md` | In-Place Rotation, Spiral Traversal, Set Zeroes, Search |
| `string.md` | KMP (LPS array), Rabin-Karp, Z-Algorithm, Manacher's |
| `math.md` | GCD/LCM, Fast Power, Sieve, Digit Manipulation, Modular Arithmetic |
| `dynamic_programming.md` | 3-Step Framework, 1D/2D DP, Knapsack, Interval DP, State Machine |
| `backtracking.md` | Universal Template, Subsets, Permutations, Grid Backtracking, Pruning |
| `arrays.md` | Two Sum Pattern, Kadane's, Prefix×Suffix, Cyclic Sort, Next Permutation |
| `design.md` | LRU/LFU Cache, O(1) Insert+Delete+Random, Two Heaps, Iterator Design |
| `prefix_sum.md` | Basic Prefix Sum, Prefix+HashMap, 2D Prefix Sum, Equilibrium Index |
| `hashing.md` | Complement Map, Frequency Map, Group By Key, Running State |
| `monotonic_stack_queue.md` | Next Greater/Smaller, Prev Greater, Sliding Window Max (Deque) |
| `segment_tree_fenwick.md` | Fenwick Tree (BIT), Segment Tree, Lazy Propagation, Count Inversions |
| `divide_and_conquer.md` | Merge Sort, Count Inversions, Quick Sort, QuickSelect, Closest Pair |
| `recursion.md` | 3-Step Framework, Return-Value Up, Pass-Value Down, Memoization, Tail Recursion |
| `kotlin_utils.md` | Complete lookup table for all ~100 utility functions with problem references |

---

## Kotlin Utilities

The `kotlin-utils` branch has **~100 reusable helper functions** across 8 focused files + a `general/` subfolder. Import what you need — no need to rewrite boilerplate every problem.

### Algorithm Utils (`utils/`)

| File | Highlights |
|---|---|
| `NumberUtils.kt` | `digits()`, `reverseDigits()`, `isPalindromeNumber()`, `toBinaryString()`, `binaryToInt()` |
| `CharStringUtils.kt` | `toLowercaseIndex()` (`'a'→0`), `charFrequency()` (IntArray[26]), `isAnagramOf()`, `anagramKey()`, `isPalindrome()` |
| `ArrayUtils.kt` | `swap()`, `reverseRange()`, `toPrefixSum()`, `moveZerosToEnd()`, `dutchNationalFlag()`, `lowerBound()`, `upperBound()` |
| `GridUtils.kt` | `DIRS_4`, `DIRS_8`, `isValid(r,c,rows,cols)`, `getNeighbors4/8()`, `encodeCell()` |
| `BitUtils.kt` | `countSetBits()`, `isBitSet/setBit/clearBit/toggleBit()`, `isPowerOfTwo()`, `lowestSetBit()`, `xorUpTo()`, `allSubsets()` |
| `MathUtils.kt` | `gcd()`, `lcm()`, `safeMod()`, `modPow()`, `fastPow()`, `isPrime()`, `sieve()`, `isPerfectSquare()` |
| `GraphUtils.kt` | `buildAdjList()`, `buildWeightedAdjList()`, `UnionFind` class, `topologicalSort()` |
| `CollectionUtils.kt` | `frequencyMap()`, `mergeIntervals()`, `intervalsOverlap()`, `minHeap/maxHeap()`, `minHeapByFirst()` |

### General Conversions (`utils/general/`)

| File | Highlights |
|---|---|
| `BaseConversions.kt` | `toBaseString(n)`, `fromBase(n)`, `toHexString()`, `toRoman()`, `romanToInt()`, `toEnglishWords()` |
| `TypeConversions.kt` | `IntArray↔List<Int>`, `Array<Int>↔IntArray`, `Array<IntArray>↔List<List<Int>>`, `Char↔ASCII`, `Boolean↔Int`, `IntArray→Pair/Triple` |
| `StringManipUtils.kt` | `reverseWords()`, `titleCase()`, `camelToSnake()`, `firstUniqueCharIndex()`, `runLengthEncode/Decode()`, `keepAlphanumeric()` |
| `CollectionConversions.kt` | `flatten()`, `zipWith()`, `unzip()`, `rotateLeft/Right()`, `transpose()`, `intersectWith()`, `drainToList()` |
| `RangeUtils.kt` | `clamp()`, `isInRange()`, `circularIndex()`, `overlapLength()`, `ceilDiv()`, `rangeList()`, `maxOfAll/minOfAll()` |

---

## How to Run

### Prerequisites
- Android Studio (Hedgehog or newer)
- JDK 17+

### Steps

1. Clone the repository:
   ```bash
   git clone git@github.com:vaibhavtripathi-bit/leetcode_algo.git
   cd leetcode_algo
   ```

2. Open in Android Studio → **File → Open**

3. Wait for Gradle sync to complete

4. Switch to the branch you want to study:
   ```bash
   git checkout arrays          # Array fundamentals
   git checkout graph           # Graph algorithms
   git checkout design          # LRU Cache, LFU Cache, etc.
   git checkout kotlin-utils    # Reusable helper functions
   ```

5. Run the app on an emulator or device to verify the solutions module links correctly.

6. Browse solutions at: `solutions/src/main/java/com/leetcode/solutions/`

---

## Coding Guidelines

### Readability First
- Simple, readable code — no overly clever one-liners
- Modern Kotlin features used **only where they improve clarity** (`when`, scope functions, null safety operators)

### Small Focused Functions
- Every problem broken into multiple small functions, each with a single responsibility
- No large "wall of code" functions

### Separated Concerns
- Traversal logic separated from business logic for trees, graphs, linked lists
- E.g., tree traversals live in `traversals/`; problem solutions call them

### Multiple Solutions Per Problem
- Every problem has **3–4 solutions** showing different approaches
- Solutions progress: simpler → more optimized
- DP problems: Brute Force → Memoization → Tabulation → Space Optimized

### High-Probability Questions Only
- FAANG interview questions from 2022–2026 only
- No "good to know but rarely asked" problems

---

## Problem Count by Branch

| Branch | Easy | Medium | Hard | Total |
|---|---|---|---|---|
| `arrays` | 2 | 5 | 1 | **8** |
| `linked-list` | 7 | 7 | 2 | **16** |
| `tree` | 5 | 8 | 2 | **15** |
| `binary-search` | 2 | 5 | 1 | **8** |
| `two-pointers` | 3 | 3 | 1 | **7** |
| `sliding-window` | 1 | 3 | 2 | **6** |
| `stack` | 3 | 3 | 1 | **7** |
| `heap` | 2 | 3 | 1 | **6** |
| `graph` | — | 7 | 2 | **9+** |
| `trie` | — | 2 | 1 | **3** |
| `intervals` | 1 | 5 | 1 | **7** |
| `bit-manipulation` | 3 | 2 | — | **5** |
| `greedy` | — | 4 | 1 | **5** |
| `matrix` | — | 3 | — | **3** |
| `string` | 1 | 2 | — | **3** |
| `math` | — | 2 | — | **2** |
| `dynamic-programming` | 1 | 8 | 2 | **11** |
| `backtracking` | — | 6 | 2 | **8** |
| `design` | — | 4 | 2 | **6** |
| **Total** | | | | **~135** |

---

## Topics Covered

### Data Structures
- Singly Linked List, Doubly Linked List
- Binary Tree, Binary Search Tree
- Graph (Adjacency List, Adjacency Matrix, Grid)
- Stack, Queue, Deque, Monotonic Stack, Monotonic Queue
- Heap / Priority Queue, Two-Heap Pattern
- Trie (Prefix Tree) — Array-based & HashMap-based
- Union-Find (Disjoint Set Union) with path compression + rank
- Segment Tree (Range Sum/Min/Max + Lazy Propagation)
- Fenwick Tree (Binary Indexed Tree)

### Algorithms
- Binary Search (4 templates including "Binary Search on Answer")
- BFS, DFS, Multi-Source BFS
- Dijkstra's Shortest Path (non-negative weights)
- Bellman-Ford (negative weights, K stops)
- Floyd-Warshall (all-pairs shortest path)
- Kruskal's & Prim's Minimum Spanning Tree
- Tarjan's SCC & Bridge / Critical Connection Finding
- Topological Sort (Kahn's BFS + DFS-based)
- KMP String Pattern Matching
- Rabin-Karp Rolling Hash
- Manacher's Palindrome Algorithm
- Sieve of Eratosthenes
- Fast Power (Binary Exponentiation)
- Floyd's Cycle Detection
- QuickSelect (Kth Largest in O(n) average)
- Merge Sort, Quick Sort

### Patterns
- Two Pointers, Fast-Slow Pointer
- Sliding Window (Fixed & Variable size)
- Prefix Sum + HashMap
- Monotonic Stack / Monotonic Queue
- Line Sweep
- Backtracking with Pruning
- Dynamic Programming (1D, 2D, Knapsack, Interval, State Machine, Bitmask)
- Greedy (Interval Scheduling, Exchange Argument)
- Divide and Conquer
- Bit Manipulation
- Union-Find
- Cyclic Sort

---

## License

This project is for personal interview preparation and educational use.
