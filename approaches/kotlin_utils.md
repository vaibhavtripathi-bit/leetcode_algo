# Kotlin Utils — Reusable Helpers for FAANG Interviews

All utilities live in `solutions/src/main/java/com/leetcode/solutions/utils/`.
Import any file you need — each function is focused on one job.

---

## NumberUtils.kt

| Function | What it does | Used in |
|---|---|---|
| `Int.digits()` | `123 → [1,2,3]` | Add Digits, Digit DP |
| `Int.digitSum()` | Sum of all digits | Happy Number, Digital Root |
| `Int.reverseDigits()` | `123 → 321`, returns 0 on overflow | Reverse Integer (LC 7) |
| `Int.isPalindromeNumber()` | No string conversion needed | Palindrome Number (LC 9) |
| `Int.toBinaryString()` | `42 → "101010"` | Any bit problem needing visual |
| `String.binaryToInt()` | `"101010" → 42` | Sum of Two Binary Strings |
| `Int.toBinaryStringPadded(width)` | `5 → "00000101"` | Bitmask problems |
| `List<Int>.digitsToInt()` | `[1,2,3] → 123` | Reconstruct number from digits |

---

## CharStringUtils.kt

| Function | What it does | Used in |
|---|---|---|
| `Char.toLowercaseIndex()` | `'a' → 0`, `'z' → 25` | Every sliding window / anagram problem |
| `Char.toUppercaseIndex()` | `'A' → 0`, `'Z' → 25` | Same |
| `Int.toAlphabetChar()` | `0 → 'a'` | Decode Ways, cipher problems |
| `Char.toDigitInt()` | `'7' → 7` | Parsing digit strings |
| `String.charFrequency()` | Returns `IntArray(26)` | Permutation in String, Valid Anagram |
| `String.charFrequencyMap()` | Returns `HashMap<Char,Int>` | Sliding Window, Minimum Window Substring |
| `String.isAnagramOf(other)` | True if anagrams | Valid Anagram, Group Anagrams |
| `String.anagramKey()` | Sorted chars as key | Group Anagrams (HashMap key) |
| `String.isPalindrome()` | Case-sensitive check | Palindromic Substrings |
| `String.isValidPalindrome()` | Ignores non-alphanumeric | Valid Palindrome (LC 125) |

---

## ArrayUtils.kt

| Function | What it does | Used in |
|---|---|---|
| `IntArray.swap(i, j)` | In-place swap | Every sorting / two-pointer problem |
| `IntArray.reverseRange(start, end)` | Reverse subarray in-place | Next Permutation, Rotate Array |
| `IntArray.toPrefixSum()` | Returns 1-indexed prefix array | Subarray Sum, Range Sum Query |
| `rangeSum(prefix, l, r)` | O(1) range sum query | Range Sum Query (LC 303) |
| `IntArray.toSuffixSum()` | Suffix sum array | Trapping Rain Water (prefix approach) |
| `IntArray.moveZerosToEnd()` | Stable, in-place | Move Zeroes (LC 283) |
| `IntArray.dutchNationalFlag()` | Sort 0s, 1s, 2s in one pass | Sort Colors (LC 75) |
| `IntArray.binarySearch(target)` | Returns index or -1 | Standard binary search |
| `IntArray.lowerBound(target)` | First index ≥ target | Leftmost binary search |
| `IntArray.upperBound(target)` | First index > target | Rightmost binary search |

---

## GridUtils.kt

| Function / Value | What it does | Used in |
|---|---|---|
| `DIRS_4` | Up, down, left, right deltas | Number of Islands, BFS on grid |
| `DIRS_8` | All 8 directions including diagonals | Word Search, Game of Life |
| `isValid(r, c, rows, cols)` | Boundary check | Every grid BFS/DFS |
| `getNeighbors4(r, c, rows, cols)` | Valid 4-dir neighbors | BFS level-by-level on grid |
| `getNeighbors8(r, c, rows, cols)` | Valid 8-dir neighbors | Word Search |
| `encodeCell(r, c, cols)` | `(r,c) → Int` — avoids Pair allocation | BFS visited set (faster) |
| `decodeCell(key, cols)` | `Int → (r,c)` | Decode encoded cell |

---

## BitUtils.kt

| Function | What it does | Used in |
|---|---|---|
| `Int.countSetBits()` | Brian Kernighan's algorithm | Counting Bits (LC 338), Hamming Weight |
| `Int.isBitSet(pos)` | Check bit at position | Bitmask DP, subset problems |
| `Int.setBit(pos)` | Set bit at position | Bitmask generation |
| `Int.clearBit(pos)` | Clear bit at position | Bitmask DP state transitions |
| `Int.toggleBit(pos)` | Flip bit at position | XOR operations |
| `Int.isPowerOfTwo()` | `n & (n-1) == 0` check | Power of Two (LC 231) |
| `Int.lowestSetBit()` | `n & (-n)` | Fenwick Tree update/query |
| `Int.removeLowestSetBit()` | `n & (n-1)` | Brian Kernighan's loop |
| `xorUpTo(n)` | XOR of 1..n using cycle pattern | Missing Number (LC 268) |
| `List<T>.allSubsets()` | All 2^n subsets via bitmask | Subsets (LC 78), bitmask DP |

---

## MathUtils.kt

| Function | What it does | Used in |
|---|---|---|
| `gcd(a, b)` | Euclidean GCD | GCD of Strings, fraction problems |
| `lcm(a, b)` | Least common multiple | Scheduling, interval problems |
| `Int.safeMod(m)` | Always non-negative modulo | Circular array, hash problems |
| `mulMod(a, b, mod)` | `(a*b) % mod` without overflow | Combination, large answer problems |
| `addMod(a, b, mod)` | `(a+b) % mod` | DP with mod |
| `fastPow(base, exp)` | O(log n) power | Pow(x, n) (LC 50) |
| `modPow(base, exp, mod)` | Modular fast power | Combinations mod p |
| `isPrime(n)` | O(√n) primality check | Count Primes, specific math |
| `sieve(limit)` | Sieve of Eratosthenes | Count Primes (LC 204) |
| `isPerfectSquare(n)` | No floating point | Valid Perfect Square (LC 367) |

---

## GraphUtils.kt

| Function / Class | What it does | Used in |
|---|---|---|
| `buildAdjList(n, edges)` | Undirected adjacency list | Number of Connected Components |
| `buildDirectedAdjList(n, edges)` | Directed adjacency list | Course Schedule |
| `buildWeightedAdjList(n, edges)` | Undirected with weights | Dijkstra, Prim's MST |
| `buildWeightedDirectedAdjList(n, edges)` | Directed with weights | Shortest Path problems |
| `UnionFind(n)` | Path compression + union by rank | Redundant Connection, Accounts Merge |
| `UnionFind.union(x, y)` | Returns true if new union | Kruskal's MST |
| `UnionFind.connected(x, y)` | Same component? | Graph Valid Tree |
| `UnionFind.components` | Current component count | Number of Connected Components |
| `topologicalSort(n, adj)` | Kahn's BFS topo sort | Course Schedule I/II |

---

## CollectionUtils.kt

| Function | What it does | Used in |
|---|---|---|
| `Iterable<T>.frequencyMap()` | Generic frequency counting | Top K Frequent, Group Anagrams |
| `IntArray.frequencyMap()` | IntArray frequency | Contains Duplicate II |
| `String.frequencyMap()` | Char frequency map | Minimum Window Substring |
| `IntArray.sortedDescending()` | Descending sort | Greedy problems |
| `Array<IntArray>.sortByStartThenEnd()` | Sort intervals | Merge Intervals, Meeting Rooms |
| `HashMap<T,Int>.sortedByFrequencyDesc()` | Top-k style | Top K Frequent (LC 347) |
| `mergeIntervals(intervals)` | Merge overlapping intervals | Merge Intervals (LC 56) |
| `intervalsOverlap(a, b)` | Quick overlap check | Insert Interval, Meeting Rooms |
| `minHeap()` / `maxHeap()` | Ready-made PriorityQueue | Kth Largest, Task Scheduler |
| `minHeapByFirst()` | PQ of IntArray by `[0]` | Dijkstra, K Closest Points |
| `Map<K,V>.invertToMultiMap()` | Invert key↔value | Accounts Merge, Group By |
