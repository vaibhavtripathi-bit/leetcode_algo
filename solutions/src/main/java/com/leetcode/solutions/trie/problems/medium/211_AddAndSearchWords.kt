package com.leetcode.solutions.trie.problems.medium

/**
 * LeetCode 211: Design Add and Search Words Data Structure
 * https://leetcode.com/problems/design-add-and-search-words-data-structure/
 *
 * Difficulty: Medium
 * Companies: Meta, Google, Amazon
 *
 * addWord(word): adds a word
 * search(word): supports '.' as wildcard matching any single character
 *
 * Example: addWord("bad"), addWord("dad"), search("..d") → true
 */

/** Solution 1: Trie + DFS for wildcard - Time: O(L) insert, O(26^L) worst search */
class WordDictionary {
    private class Node {
        val children = arrayOfNulls<Node>(26)
        var isEnd = false
    }

    private val root = Node()

    fun addWord(word: String) {
        var node = root
        for (c in word) {
            val i = c - 'a'
            if (node.children[i] == null) node.children[i] = Node()
            node = node.children[i]!!
        }
        node.isEnd = true
    }

    fun search(word: String): Boolean = dfs(word, 0, root)

    private fun dfs(word: String, index: Int, node: Node): Boolean {
        if (index == word.length) return node.isEnd

        val c = word[index]
        return if (c == '.') {
            node.children.filterNotNull().any { dfs(word, index + 1, it) }
        } else {
            val child = node.children[c - 'a'] ?: return false
            dfs(word, index + 1, child)
        }
    }
}
