package com.leetcode.solutions.backtracking.problems.hard

/**
 * LeetCode 51: N-Queens
 * https://leetcode.com/problems/n-queens/
 *
 * Difficulty: Hard
 * Companies: Amazon, Google, Microsoft
 *
 * Place n queens on n×n chessboard so no two queens attack each other.
 * Queens attack same row, column, or diagonal.
 *
 * Example: n = 4 → [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 */

/** Solution 1: Backtracking with HashSets - Time: O(n!), Space: O(n) */
class NQueens1 {
    fun solveNQueens(n: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val cols = HashSet<Int>()
        val diag1 = HashSet<Int>()    // row - col (top-left to bottom-right)
        val diag2 = HashSet<Int>()    // row + col (top-right to bottom-left)
        val board = Array(n) { CharArray(n) { '.' } }

        placeQueens(0, n, board, cols, diag1, diag2, result)
        return result
    }

    private fun placeQueens(
        row: Int, n: Int, board: Array<CharArray>,
        cols: HashSet<Int>, diag1: HashSet<Int>, diag2: HashSet<Int>,
        result: MutableList<List<String>>
    ) {
        if (row == n) {
            result.add(board.map { String(it) })
            return
        }

        for (col in 0 until n) {
            if (isUnderAttack(row, col, cols, diag1, diag2)) continue

            placeQueen(row, col, board, cols, diag1, diag2)
            placeQueens(row + 1, n, board, cols, diag1, diag2, result)
            removeQueen(row, col, board, cols, diag1, diag2)
        }
    }

    private fun isUnderAttack(row: Int, col: Int, cols: Set<Int>, d1: Set<Int>, d2: Set<Int>) =
        col in cols || (row - col) in d1 || (row + col) in d2

    private fun placeQueen(
        row: Int, col: Int, board: Array<CharArray>,
        cols: HashSet<Int>, d1: HashSet<Int>, d2: HashSet<Int>
    ) {
        board[row][col] = 'Q'
        cols.add(col); d1.add(row - col); d2.add(row + col)
    }

    private fun removeQueen(
        row: Int, col: Int, board: Array<CharArray>,
        cols: HashSet<Int>, d1: HashSet<Int>, d2: HashSet<Int>
    ) {
        board[row][col] = '.'
        cols.remove(col); d1.remove(row - col); d2.remove(row + col)
    }
}

/** Solution 2: Boolean arrays instead of HashSets (slightly faster) */
class NQueens2 {
    fun solveNQueens(n: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val board = Array(n) { CharArray(n) { '.' } }
        val colUsed = BooleanArray(n)
        val diag1Used = BooleanArray(2 * n)
        val diag2Used = BooleanArray(2 * n)

        backtrack(0, n, board, colUsed, diag1Used, diag2Used, result)
        return result
    }

    private fun backtrack(
        row: Int, n: Int, board: Array<CharArray>,
        col: BooleanArray, d1: BooleanArray, d2: BooleanArray,
        result: MutableList<List<String>>
    ) {
        if (row == n) { result.add(board.map { String(it) }); return }

        for (c in 0 until n) {
            if (col[c] || d1[row - c + n] || d2[row + c]) continue

            board[row][c] = 'Q'; col[c] = true; d1[row - c + n] = true; d2[row + c] = true
            backtrack(row + 1, n, board, col, d1, d2, result)
            board[row][c] = '.'; col[c] = false; d1[row - c + n] = false; d2[row + c] = false
        }
    }
}
