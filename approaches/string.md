# String Approach Guide

## Core Patterns

---

## Pattern 1: Character Frequency / Anagram Detection

### Pseudocode: Are two strings anagrams?
```
IF lengths differ: RETURN false

count = IntArray[26]
FOR c in s: count[c - 'a']++
FOR c in t: count[c - 'a']--

RETURN all counts are 0
```

### Pseudocode: Group Anagrams
```
map: sortedKey -> list of words

FOR each word:
    key = sorted(word)
    map[key].add(word)

RETURN map.values
```

---

## Pattern 2: KMP Algorithm (Pattern Matching)

### Core Idea
Precompute a failure function (LPS array) that tells: "if match fails at position i, how far back do we need to go?"

### Build LPS (Longest Proper Prefix which is also Suffix)
```
lps = IntArray(pattern.length)
lps[0] = 0
length = 0
i = 1

WHILE i < pattern.length:
    IF pattern[i] == pattern[length]:
        length++
        lps[i] = length
        i++
    ELSE:
        IF length != 0: length = lps[length - 1]
        ELSE: lps[i] = 0; i++
```

### KMP Search
```
i = 0 (text index)
j = 0 (pattern index)

WHILE i < text.length:
    IF text[i] == pattern[j]:
        i++; j++
    IF j == pattern.length:
        FOUND match at i - j
        j = lps[j - 1]
    ELSE IF i < text.length AND text[i] != pattern[j]:
        IF j != 0: j = lps[j - 1]
        ELSE: i++
```
**Time:** O(n + m), **Space:** O(m)

---

## Pattern 3: Rabin-Karp (Rolling Hash)

### Core Idea
Hash the pattern and each window of same size in text. Only compare strings when hashes match.

```
patternHash = hash(pattern)
windowHash = hash(text[0..m-1])

FOR i = 0 to n-m:
    IF windowHash == patternHash:
        IF text[i..i+m-1] == pattern: FOUND
    
    // Roll hash: remove leftmost char, add rightmost
    windowHash = (windowHash - text[i] * power) * base + text[i+m]
```
**Time:** O(n + m) avg, O(nm) worst (hash collisions)

---

## Pattern 4: Z-Algorithm

### Core Idea
Z[i] = length of longest substring starting at i that matches prefix of string.

```
z = IntArray(n), z[0] = n
left = 0, right = 0

FOR i = 1 to n-1:
    IF i < right:
        z[i] = min(right - i, z[i - left])
    WHILE i + z[i] < n AND s[z[i]] == s[i + z[i]]:
        z[i]++
    IF i + z[i] > right:
        left = i; right = i + z[i]
```

---

## Pattern 5: Palindrome Expansion

### Pseudocode: Expand around center
```
FOR each center (both single char and between chars):
    expand left and right while chars match
    record longest palindrome found
```

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Valid Anagram | Easy | Frequency Count | All FAANG |
| Valid Palindrome | Easy | Two Pointers | All FAANG |
| Group Anagrams | Medium | Sort Key Hashing | All FAANG |
| Longest Palindromic Substring | Medium | Expand Centers | All FAANG |
| Encode/Decode Strings | Medium | Delimiter Encoding | Google, Meta |
| Longest Repeating Char Replacement | Medium | Sliding Window | Amazon, Google |
| Minimum Window Substring | Hard | Sliding Window | All FAANG |
| Find All Anagrams in String | Medium | Sliding Window + Freq | Amazon, Google |
| String to Integer (atoi) | Medium | State Machine | All FAANG |
