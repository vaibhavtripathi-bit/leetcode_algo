package com.leetcode.solutions.backtracking.problems.hard

/**
 * LeetCode 37: Sudoku Solver
 * https://leetcode.com/problems/sudoku-solver/
 *
 * Difficulty: Hard
 * Companies: Amazon, Google, Microsoft
 *
 * Fill the 9×9 board to solve Sudoku. Each row, col, and 3×3 box must have 1-9.
 */

/** Solution 1: Backtracking with HashSets per row/col/box */
class SudokuSolver {
    fun solveSudoku(board: Array<CharArray>) {
        val rows = Array(9) { HashSet<Char>() }
        val cols = Array(9) { HashSet<Char>() }
        val boxes = Array(9) { HashSet<Char>() }

        initConstraints(board, rows, cols, boxes)
        solve(board, rows, cols, boxes)
    }

    private fun initConstraints(
        board: Array<CharArray>,
        rows: Array<HashSet<Char>>, cols: Array<HashSet<Char>>, boxes: Array<HashSet<Char>>
    ) {
        for (r in 0 until 9) for (c in 0 until 9) {
            val ch = board[r][c]
            if (ch != '.') {
                rows[r].add(ch); cols[c].add(ch); boxes[boxIndex(r, c)].add(ch)
            }
        }
    }

    private fun solve(
        board: Array<CharArray>,
        rows: Array<HashSet<Char>>, cols: Array<HashSet<Char>>, boxes: Array<HashSet<Char>>
    ): Boolean {
        for (r in 0 until 9) for (c in 0 until 9) {
            if (board[r][c] != '.') continue

            for (digit in '1'..'9') {
                if (isValid(r, c, digit, rows, cols, boxes)) {
                    placeDigit(board, rows, cols, boxes, r, c, digit)
                    if (solve(board, rows, cols, boxes)) return true
                    removeDigit(board, rows, cols, boxes, r, c, digit)
                }
            }

            return false
        }
        return true
    }

    private fun boxIndex(r: Int, c: Int) = (r / 3) * 3 + c / 3

    private fun isValid(r: Int, c: Int, d: Char, rows: Array<HashSet<Char>>, cols: Array<HashSet<Char>>, boxes: Array<HashSet<Char>>) =
        d !in rows[r] && d !in cols[c] && d !in boxes[boxIndex(r, c)]

    private fun placeDigit(board: Array<CharArray>, rows: Array<HashSet<Char>>, cols: Array<HashSet<Char>>, boxes: Array<HashSet<Char>>, r: Int, c: Int, d: Char) {
        board[r][c] = d; rows[r].add(d); cols[c].add(d); boxes[boxIndex(r, c)].add(d)
    }

    private fun removeDigit(board: Array<CharArray>, rows: Array<HashSet<Char>>, cols: Array<HashSet<Char>>, boxes: Array<HashSet<Char>>, r: Int, c: Int, d: Char) {
        board[r][c] = '.'; rows[r].remove(d); cols[c].remove(d); boxes[boxIndex(r, c)].remove(d)
    }
}
