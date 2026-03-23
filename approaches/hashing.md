# Hashing Approach Guide

## Core Idea
Hash maps and hash sets reduce lookup time from O(n) to O(1). The key insight: **trade space for time**. Pre-store values/indices/frequencies so future lookups are instant.

---

## Pattern 1: Two Sum Pattern (Index Lookup)

### Core Idea: For each element, check if its complement already exists in the map.

```
seen = {}    // value -> index

FOR i, num in enumerate(nums):
    complement = target - num
    
    IF complement in seen:
        RETURN [seen[complement], i]
    
    seen[num] = i
```

**Used for:** Any problem asking "find two/three elements that satisfy a condition."

**Extensions:**
- 3Sum → fix one, Two Sum the rest
- 4Sum → fix two, Two Sum the rest
- Two Sum in BST → Two Sum with inorder traversal

---

## Pattern 2: Frequency Map (Counting)

```
freq = {}
FOR each element: freq[element] += 1

// Check: does any element appear more than n/2 times?
// Check: are all characters the same frequency?
// Check: which element appears an odd number of times?
```

### Pseudocode: Valid Anagram
```
IF lengths differ: RETURN false
count = IntArray[26]
FOR c in s: count[c]++
FOR c in t: count[c]--
RETURN all(count == 0)
```

---

## Pattern 3: Grouping by Hash Key

### Pseudocode: Group Anagrams
```
map = defaultdict(list)

FOR each word:
    key = canonical form (sorted chars, or char count tuple)
    map[key].append(word)

RETURN map.values()
```

**Canonical key ideas:**
- Sorted string: `"eat".sorted() = "aet"`
- Count tuple: `[1,0,0,...,1,0,...1,0]` for each char

---

## Pattern 4: Seen Before / Duplicate Detection

```
seen = HashSet

FOR each element:
    IF element in seen: RETURN true (duplicate found)
    seen.add(element)

RETURN false
```

**Extensions:**
- Contains Duplicate II: use map to track last seen index
- Contains Duplicate III: use sliding window + sorted set

---

## Pattern 5: HashMap for Memoization (DP Optimization)

```
memo = {}

FUNCTION solve(state):
    IF state in memo: RETURN memo[state]
    
    result = ... compute ...
    memo[state] = result
    RETURN result
```

---

## Pattern 6: Counting with Running State (Prefix + HashMap)

### Pseudocode: Subarray Sum Equals K (560)
```
prefixCount = {0: 1}
runningSum = 0
result = 0

FOR each num:
    runningSum += num
    result += prefixCount.get(runningSum - k, 0)
    prefixCount[runningSum] += 1
```

### Pseudocode: Longest Subarray with Sum = K
```
prefixIndex = {0: -1}
runningSum = 0
maxLen = 0

FOR i, num:
    runningSum += num
    IF (runningSum - k) in prefixIndex:
        maxLen = max(maxLen, i - prefixIndex[runningSum - k])
    IF runningSum not in prefixIndex:
        prefixIndex[runningSum] = i    // only store first occurrence
```

---

## Pattern 7: HashMap for O(1) Design Problems

### LRU Cache uses: HashMap (key → node) + Doubly Linked List (order)
### LFU Cache uses: HashMap (key → val) + HashMap (freq → list) + min freq tracker

```
// O(1) all operations by combining:
// - HashMap: instant lookup
// - LinkedList: O(1) insert/delete with references
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Two Sum | Easy | Complement Map | All FAANG |
| Valid Anagram | Easy | Frequency Count | All FAANG |
| Contains Duplicate | Easy | HashSet | All FAANG |
| Group Anagrams | Medium | Group by Hash Key | All FAANG |
| Subarray Sum Equals K | Medium | Prefix + HashMap | All FAANG |
| Longest Consecutive Sequence | Medium | HashSet Union-Find | All FAANG |
| 4Sum II | Medium | Split into Two 2Sums | Amazon, Google |
| LRU Cache | Medium | HashMap + DLL | All FAANG |
| Top K Frequent Elements | Medium | Freq Map + Heap/Bucket | All FAANG |
