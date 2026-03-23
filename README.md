# LeetCode FAANG Interview Prep — Kotlin

A comprehensive Android project containing high-probability FAANG (Google, Meta, Amazon, Apple, Microsoft) LeetCode solutions in Kotlin, covering all major algorithms and data structures asked in interviews from 2022–2026.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Branches](#branches)
- [Approach Docs](#approach-docs)
- [How to Run](#how-to-run)
- [Coding Guidelines](#coding-guidelines)
- [Problem Count by Branch](#problem-count-by-branch)

---

## Project Structure

```
leetcode/
├── app/                                  # Android app module (run & demo solutions)
│   └── src/main/java/com/leetcode/app/
│       └── MainActivity.kt               # Entry point to run solutions
│
├── solutions/                            # Kotlin library module — all LeetCode code lives here
│   └── src/main/java/com/leetcode/solutions/
│       ├── common/
│       │   ├── ListNode.kt               # Singly linked list node + utilities
│       │   └── TreeNode.kt               # Binary tree node + utilities
│       │
│       ├── linkedlist/
│       │   ├── traversals/               # BasicTraversals, TwoPointerPatterns, ReversalPatterns
│       │   └── problems/easy|medium|hard/
│       │
│       ├── tree/
│       │   ├── traversals/               # DFSTraversals, BFSTraversals, TreeProperties
│       │   └── problems/easy|medium|hard/
│       │
│       ├── graph/
│       │   ├── traversals/               # GraphTraversals (DFS, BFS, Topological Sort)
│       │   ├── shortest_path/            # Dijkstra, Bellman-Ford
│       │   ├── union_find/               # UnionFind DSU
│       │   ├── mst/                      # Kruskal, Prim
│       │   ├── scc/                      # Tarjan SCC, Critical Connections
│       │   └── problems/medium|hard/
│       │
│       ├── binarysearch/problems/
│       ├── twopointers/problems/
│       ├── slidingwindow/problems/
│       ├── stack/problems/
│       ├── heap/problems/
│       ├── dp/problems/
│       ├── backtracking/problems/
│       ├── trie/
│       │   ├── core/                     # TrieNode (Array-based & HashMap-based)
│       │   └── problems/
│       ├── intervals/problems/
│       ├── bitmanipulation/problems/
│       ├── greedy/problems/
│       ├── matrix/problems/
│       ├── strings/
│       │   ├── algorithms/               # KMP string matching
│       │   └── problems/
│       └── math/
│           ├── algorithms/               # MathUtils: GCD, Fast Power, Sieve
│           └── problems/
│
└── approaches/                           # Plain English algorithm explanations + pseudocode
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
    └── backtracking.md
```

---

## Branches

Each problem category lives on its own dedicated branch. Switch branches to study a specific topic.

| Branch | Topic | Key Algorithms |
|---|---|---|
| `linked-list` | Linked Lists | Fast-Slow Pointer, Reversal, Floyd's Cycle Detection, Merge |
| `tree` | Binary Trees | DFS (Pre/In/Post), BFS Level Order, LCA, BST, Path Sum |
| `binary-search` | Binary Search | Standard, Rotated Array, Binary Search on Answer, 2D Matrix |
| `two-pointers` | Two Pointers | Converging, Same Direction, 3Sum, Trapping Rain Water |
| `sliding-window` | Sliding Window | Fixed Window, Variable Window, Monotonic Deque |
| `stack` | Stack | Monotonic Stack, Min Stack, Expression Evaluation, Histogram |
| `heap` | Heap / Priority Queue | Min/Max Heap, Two Heaps, Kth Largest, QuickSelect |
| `graph` | Graphs | DFS/BFS, Dijkstra, Bellman-Ford, Kruskal/Prim MST, Union-Find, Tarjan SCC, Bipartite |
| `trie` | Trie / Prefix Tree | Insert/Search/Prefix, Wildcard DFS, Word Search II |
| `intervals` | Intervals | Line Sweep, Merge, Insert, Meeting Rooms, Skyline Problem |
| `bit-manipulation` | Bit Manipulation | XOR tricks, Brian Kernighan, Bit Masking, DP with bitmask |
| `greedy` | Greedy | Jump Game, Gas Station, Candy, Partition Labels |
| `matrix` | 2D Matrix | Rotate Image, Spiral Order, Set Zeroes, Search |
| `string` | Strings | KMP Pattern Matching, Manacher's, Anagram, Group Anagrams |
| `math` | Math / Number Theory | GCD/LCM, Fast Power, Sieve of Eratosthenes |
| `dynamic-programming` | Dynamic Programming | 1D DP, 2D DP, Knapsack (0/1 & Unbounded), Interval DP |
| `backtracking` | Backtracking | Subsets, Permutations, N-Queens, Sudoku, IP Addresses |

```bash
# Switch to a topic branch
git checkout linked-list
git checkout graph
git checkout dynamic-programming
```

---

## Approach Docs

The `approaches/` folder contains plain-English explanations + pseudocode for every major algorithmic pattern — no code, just the thinking.

Each doc includes:
- **Core idea** explained simply
- **Pseudocode** for every variant
- **"Why it works"** explanation for non-obvious algorithms
- **FAANG Frequency table** showing which companies ask which problems

> Read the approach doc **before** looking at the code solution. This is how real interviews work — you need to explain your approach first.

---

## How to Run

### Prerequisites
- Android Studio (Hedgehog or newer)
- JDK 17+

### Steps
1. Clone the repository
   ```bash
   git clone git@github.com:vaibhavtripathi-bit/leetcode_algo.git
   cd leetcode_algo
   ```

2. Open in Android Studio → **File → Open**

3. Wait for Gradle sync to complete

4. Switch to the branch you want to study:
   ```bash
   git checkout tree
   ```

5. Run the app on an emulator or device — the main screen has a "Run Sample" button to test the solutions module is working.

6. To study solutions: browse `solutions/src/main/java/com/leetcode/solutions/`

---

## Coding Guidelines

All solutions follow these rules:

### Readability First
- Simple, readable code — no overly clever one-liners
- Modern Kotlin features used **only where they improve clarity** (scope functions, `when`, null safety)

### Small Focused Functions
- Every problem is broken into multiple small functions, each doing one thing
- No large "wall of code" functions

### Separated Concerns
- Traversal logic is separated from business logic (especially for trees, graphs, linked lists)
- E.g., tree traversal in `traversals/` folder; problem solutions use those utilities

### Multiple Solutions Per Problem
- Every problem has **3–4 solutions** showing different approaches
- Solutions progress from simpler → more optimized
- For DP problems: Brute force → Memoization → Tabulation → Space Optimized

### High-Probability Questions Only
- Only FAANG interview questions from 2022–2026
- Problems marked with which companies ask them

---

## Problem Count by Branch

| Branch | Easy | Medium | Hard | Total |
|---|---|---|---|---|
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
| **Total** | | | | **~120** |

---

## Topics Covered

### Data Structures
- Singly Linked List, Doubly Linked List
- Binary Tree, Binary Search Tree
- Graph (Adjacency List, Adjacency Matrix, Grid)
- Stack, Queue, Deque, Monotonic Stack
- Heap / Priority Queue, Two-Heap
- Trie (Prefix Tree) — Array-based & HashMap-based
- Union-Find (Disjoint Set Union)

### Algorithms
- Binary Search (4 templates including "Binary Search on Answer")
- BFS, DFS, Multi-Source BFS
- Dijkstra's Shortest Path
- Bellman-Ford (Negative Weights)
- Kruskal's & Prim's MST
- Tarjan's SCC & Bridge Finding
- Topological Sort (Kahn's BFS + DFS-based)
- KMP String Pattern Matching
- Manacher's Palindrome Algorithm
- Sieve of Eratosthenes
- Fast Power (Binary Exponentiation)
- Floyd's Cycle Detection
- QuickSelect

### Patterns
- Two Pointers, Fast-Slow Pointer
- Sliding Window (Fixed & Variable)
- Line Sweep
- Backtracking with Pruning
- Dynamic Programming (1D, 2D, Knapsack, Interval, State Machine)
- Greedy
- Divide and Conquer
- Bit Manipulation

---

## License

This project is for personal interview preparation and educational use.
