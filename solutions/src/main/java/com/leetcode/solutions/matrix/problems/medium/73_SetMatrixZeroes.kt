package com.leetcode.solutions.matrix.problems.medium

/**
 * LeetCode 73: Set Matrix Zeroes
 * https://leetcode.com/problems/set-matrix-zeroes/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * If an element is 0, set its entire row and column to 0 in-place.
 *
 * Example: [[1,1,1],[1,0,1],[1,1,1]] → [[1,0,1],[0,0,0],[1,0,1]]
 */

/** Solution 1: Use first row/col as flags - Time: O(m*n), Space: O(1) */
class SetZeroes1 {
    fun setZeroes(matrix: Array<IntArray>) {
        val firstRowHasZero = matrix[0].any { it == 0 }
        val firstColHasZero = matrix.any { it[0] == 0 }

        markZeroesInFirstRowAndCol(matrix)
        applyZeroes(matrix)

        if (firstRowHasZero) zeroRow(matrix, 0)
        if (firstColHasZero) zeroCol(matrix, 0)
    }

    private fun markZeroesInFirstRowAndCol(matrix: Array<IntArray>) {
        for (r in 1 until matrix.size) {
            for (c in 1 until matrix[0].size) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0
                    matrix[0][c] = 0
                }
            }
        }
    }

    private fun applyZeroes(matrix: Array<IntArray>) {
        for (r in 1 until matrix.size) {
            for (c in 1 until matrix[0].size) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) matrix[r][c] = 0
            }
        }
    }

    private fun zeroRow(matrix: Array<IntArray>, r: Int) { matrix[r].fill(0) }
    private fun zeroCol(matrix: Array<IntArray>, c: Int) { matrix.forEach { it[c] = 0 } }
}

/** Solution 2: Track rows/cols with HashSets - Time: O(m*n), Space: O(m+n) */
class SetZeroes2 {
    fun setZeroes(matrix: Array<IntArray>) {
        val rows = HashSet<Int>()
        val cols = HashSet<Int>()

        for (r in matrix.indices) for (c in matrix[0].indices) {
            if (matrix[r][c] == 0) { rows.add(r); cols.add(c) }
        }

        for (r in matrix.indices) for (c in matrix[0].indices) {
            if (r in rows || c in cols) matrix[r][c] = 0
        }
    }
}
