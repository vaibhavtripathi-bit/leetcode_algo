package com.leetcode.solutions.matrix.problems.medium

/**
 * LeetCode 48: Rotate Image
 * https://leetcode.com/problems/rotate-image/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Rotate an n×n matrix 90° clockwise IN PLACE.
 *
 * Example: [[1,2,3],[4,5,6],[7,8,9]] → [[7,4,1],[8,5,2],[9,6,3]]
 */

/** Solution 1: Transpose + Reverse Rows - Time: O(n²), Space: O(1) */
class RotateImage1 {
    fun rotate(matrix: Array<IntArray>) {
        transpose(matrix)
        reverseEachRow(matrix)
    }

    private fun transpose(matrix: Array<IntArray>) {
        val n = matrix.size
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[j][i]
                matrix[j][i] = temp
            }
        }
    }

    private fun reverseEachRow(matrix: Array<IntArray>) {
        for (row in matrix) row.reverse()
    }
}

/** Solution 2: Four-way rotation in-place - Time: O(n²), Space: O(1) */
class RotateImage2 {
    fun rotate(matrix: Array<IntArray>) {
        val n = matrix.size

        for (i in 0 until n / 2) {
            for (j in i until n - 1 - i) {
                rotateFour(matrix, i, j, n)
            }
        }
    }

    private fun rotateFour(matrix: Array<IntArray>, i: Int, j: Int, n: Int) {
        val temp = matrix[i][j]
        matrix[i][j] = matrix[n - 1 - j][i]
        matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j]
        matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i]
        matrix[j][n - 1 - i] = temp
    }
}
