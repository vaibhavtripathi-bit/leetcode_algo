package com.leetcode.solutions.trie.problems.medium

import com.leetcode.solutions.trie.core.TrieNodeArray
import com.leetcode.solutions.trie.core.TrieNodeMap

/**
 * LeetCode 208: Implement Trie (Prefix Tree)
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Implement insert, search, and startsWith operations.
 * All operations must be O(L) where L = word length.
 */

/** Solution 1: Array-based children - Time: O(L), Space: O(L) */
class Trie1 {
    private val root = TrieNodeArray()

    fun insert(word: String) {
        var node = root
        for (c in word) {
            val idx = c - 'a'
            if (node.children[idx] == null) {
                node.children[idx] = TrieNodeArray()
            }
            node = node.children[idx]!!
        }
        node.isEnd = true
    }

    fun search(word: String): Boolean {
        val node = traverse(word) ?: return false
        return node.isEnd
    }

    fun startsWith(prefix: String): Boolean {
        return traverse(prefix) != null
    }

    private fun traverse(str: String): TrieNodeArray? {
        var node = root
        for (c in str) {
            val idx = c - 'a'
            node = node.children[idx] ?: return null
        }
        return node
    }
}

/** Solution 2: HashMap-based children (handles any character set) */
class Trie2 {
    private val root = TrieNodeMap()

    fun insert(word: String) {
        var node = root
        for (c in word) {
            node = node.children.getOrPut(c) { TrieNodeMap() }
        }
        node.isEnd = true
    }

    fun search(word: String): Boolean {
        val node = traverse(word) ?: return false
        return node.isEnd
    }

    fun startsWith(prefix: String): Boolean = traverse(prefix) != null

    private fun traverse(str: String): TrieNodeMap? {
        var node = root
        for (c in str) node = node.children[c] ?: return null
        return node
    }
}
