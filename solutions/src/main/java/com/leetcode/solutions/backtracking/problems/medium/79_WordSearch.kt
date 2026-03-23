package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 79: Word Search
 * https://leetcode.com/problems/word-search/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Given a grid of characters and a word, return true if word exists in the grid.
 * Word must be formed by adjacent cells (up/down/left/right, no reuse).
 *
 * Example: board = [["A","B","C","E"],...], word = "ABCCED" → true
 */

/** Solution 1: DFS Backtracking - Time: O(m*n * 4^L), Space: O(L) */
class WordSearch1 {
    private val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun exist(board: Array<CharArray>, word: String): Boolean {
        for (r in board.indices) {
            for (c in board[0].indices) {
                if (dfs(board, word, 0, r, c)) return true
            }
        }
        return false
    }

    private fun dfs(board: Array<CharArray>, word: String, index: Int, r: Int, c: Int): Boolean {
        if (index == word.length) return true
        if (!isValidCell(board, r, c) || board[r][c] != word[index]) return false

        val original = board[r][c]
        board[r][c] = '#'

        val found = directions.any { (dr, dc) ->
            dfs(board, word, index + 1, r + dr, c + dc)
        }

        board[r][c] = original

        return found
    }

    private fun isValidCell(board: Array<CharArray>, r: Int, c: Int) =
        r in board.indices && c in board[0].indices
}

/** Solution 2: DFS with Visited Array - Time: O(m*n * 4^L), Space: O(m*n) */
class WordSearch2 {
    fun exist(board: Array<CharArray>, word: String): Boolean {
        val visited = Array(board.size) { BooleanArray(board[0].size) }

        for (r in board.indices) {
            for (c in board[0].indices) {
                if (dfs(board, word, 0, r, c, visited)) return true
            }
        }
        return false
    }

    private fun dfs(
        board: Array<CharArray>, word: String, index: Int,
        r: Int, c: Int, visited: Array<BooleanArray>
    ): Boolean {
        if (index == word.length) return true
        if (r !in board.indices || c !in board[0].indices) return false
        if (visited[r][c] || board[r][c] != word[index]) return false

        visited[r][c] = true

        val found = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1).any { (dr, dc) ->
            dfs(board, word, index + 1, r + dr, c + dc, visited)
        }

        visited[r][c] = false

        return found
    }
}
