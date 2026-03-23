package com.leetcode.solutions.trie.problems.hard

import com.leetcode.solutions.trie.core.TrieNodeArray

/**
 * LeetCode 212: Word Search II
 * https://leetcode.com/problems/word-search-ii/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Given a board and list of words, find all words that exist in the board.
 * Word must be formed by adjacent cells (no reuse).
 *
 * Key insight: Build trie from word list, then DFS the grid using the trie.
 * This avoids re-searching the grid for each word separately.
 */

/** Solution 1: Trie + DFS Backtracking - Time: O(M*N*4^L), Space: O(W*L) */
class WordSearchII {
    fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
        val root = buildTrie(words)
        val result = mutableListOf<String>()

        for (r in board.indices) {
            for (c in board[0].indices) {
                dfs(board, r, c, root, result)
            }
        }

        return result
    }

    private fun buildTrie(words: Array<String>): TrieNodeArray {
        val root = TrieNodeArray()
        for (word in words) {
            var node = root
            for (c in word) {
                val i = c - 'a'
                if (node.children[i] == null) node.children[i] = TrieNodeArray()
                node = node.children[i]!!
            }
            node.word = word
        }
        return root
    }

    private fun dfs(
        board: Array<CharArray>, r: Int, c: Int,
        node: TrieNodeArray, result: MutableList<String>
    ) {
        if (r !in board.indices || c !in board[0].indices) return

        val ch = board[r][c]
        if (ch == '#') return

        val childNode = node.children[ch - 'a'] ?: return

        if (childNode.word != null) {
            result.add(childNode.word!!)
            childNode.word = null    // deduplicate — don't add same word twice
        }

        board[r][c] = '#'

        dfs(board, r + 1, c, childNode, result)
        dfs(board, r - 1, c, childNode, result)
        dfs(board, r, c + 1, childNode, result)
        dfs(board, r, c - 1, childNode, result)

        board[r][c] = ch
    }
}
