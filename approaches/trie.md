# Trie (Prefix Tree) Approach Guide

## Core Idea
A Trie is a tree where each node represents one character. Root is empty. Each path from root to a marked node spells out a word. Used for fast prefix matching: O(L) per operation where L = word length.

---

## Trie Node Structure
```
TrieNode:
    children: Map<Char, TrieNode>    // or Array[26] for lowercase letters
    isEnd: Boolean                    // marks end of a word
```

## Core Operations

### Insert
```
FUNCTION insert(word):
    node = root
    FOR each char c in word:
        IF c not in node.children:
            node.children[c] = new TrieNode()
        node = node.children[c]
    node.isEnd = true
```

### Search (exact match)
```
FUNCTION search(word):
    node = root
    FOR each char c in word:
        IF c not in node.children: RETURN false
        node = node.children[c]
    RETURN node.isEnd
```

### StartsWith (prefix check)
```
FUNCTION startsWith(prefix):
    node = root
    FOR each char c in prefix:
        IF c not in node.children: RETURN false
        node = node.children[c]
    RETURN true    // prefix exists (don't check isEnd)
```

---

## Pattern 1: Trie + DFS for Wildcard Search

### Pseudocode: Search with '.' wildcard
```
FUNCTION search(word, node, index):
    IF index == word.length: RETURN node.isEnd
    
    c = word[index]
    
    IF c == '.':
        // Try all children
        FOR each child in node.children:
            IF search(word, child, index+1): RETURN true
        RETURN false
    ELSE:
        IF c not in node.children: RETURN false
        RETURN search(word, node.children[c], index+1)
```

---

## Pattern 2: Trie + Backtracking for Word Search

### Pseudocode: Find all words from grid using Trie
```
// Pre-build trie from word list

FOR each cell (r, c) in grid:
    dfs(r, c, root_of_trie, current_path)

FUNCTION dfs(r, c, trieNode, path):
    IF r/c out of bounds OR visited OR char not in trieNode.children:
        RETURN
    
    nextNode = trieNode.children[grid[r][c]]
    path.add(grid[r][c])
    
    IF nextNode.isEnd:
        result.add(path.toString())
        nextNode.isEnd = false    // deduplicate
    
    MARK grid[r][c] as visited
    dfs in all 4 directions
    UNMARK grid[r][c]
    path.removeLast()
```

---

## When to Use Trie vs HashMap

| Use Trie | Use HashMap |
|---|---|
| Prefix queries ("startsWith") | Exact lookups only |
| Auto-complete, search suggestions | Simple word existence |
| Multiple words share common prefixes | No prefix relationship needed |
| Word Search II (many words in grid) | Single word search |

---

## FAANG Frequency Summary

| Problem | Difficulty | Pattern | Asked At |
|---------|-----------|---------|----------|
| Implement Trie | Medium | Core Implementation | All FAANG |
| Add and Search Words | Medium | Trie + DFS Wildcard | All FAANG |
| Word Search II | Hard | Trie + Grid Backtracking | All FAANG |
| Design Search Autocomplete | Hard | Trie + Ranking | Google, Amazon |
| Replace Words | Medium | Trie Prefix Replace | Amazon |
| Longest Word in Dictionary | Medium | Trie + BFS | Amazon, Google |
