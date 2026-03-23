package com.leetcode.solutions.matrix.problems.medium

/**
 * LeetCode 54: Spiral Matrix
 * https://leetcode.com/problems/spiral-matrix/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Return all elements of matrix in spiral order.
 *
 * Example: [[1,2,3],[4,5,6],[7,8,9]] → [1,2,3,6,9,8,7,4,5]
 */

/** Solution 1: Boundary Shrink - Time: O(m*n), Space: O(1) extra */
class SpiralMatrix1 {
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        val result = mutableListOf<Int>()
        var top = 0; var bottom = matrix.lastIndex
        var left = 0; var right = matrix[0].lastIndex

        while (top <= bottom && left <= right) {
            collectRight(matrix, top, left, right, result)
            top++

            collectDown(matrix, right, top, bottom, result)
            right--

            if (top <= bottom) {
                collectLeft(matrix, bottom, right, left, result)
                bottom--
            }

            if (left <= right) {
                collectUp(matrix, left, bottom, top, result)
                left++
            }
        }

        return result
    }

    private fun collectRight(m: Array<IntArray>, row: Int, l: Int, r: Int, res: MutableList<Int>) {
        for (c in l..r) res.add(m[row][c])
    }

    private fun collectDown(m: Array<IntArray>, col: Int, t: Int, b: Int, res: MutableList<Int>) {
        for (r in t..b) res.add(m[r][col])
    }

    private fun collectLeft(m: Array<IntArray>, row: Int, r: Int, l: Int, res: MutableList<Int>) {
        for (c in r downTo l) res.add(m[row][c])
    }

    private fun collectUp(m: Array<IntArray>, col: Int, b: Int, t: Int, res: MutableList<Int>) {
        for (r in b downTo t) res.add(m[r][col])
    }
}

/** Solution 2: Direction Array - Time: O(m*n), Space: O(m*n) */
class SpiralMatrix2 {
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        val m = matrix.size; val n = matrix[0].size
        val visited = Array(m) { BooleanArray(n) }
        val result = mutableListOf<Int>()
        val dirs = arrayOf(intArrayOf(0,1), intArrayOf(1,0), intArrayOf(0,-1), intArrayOf(-1,0))
        var r = 0; var c = 0; var d = 0

        repeat(m * n) {
            result.add(matrix[r][c])
            visited[r][c] = true

            val nr = r + dirs[d][0]
            val nc = c + dirs[d][1]

            if (nr !in 0 until m || nc !in 0 until n || visited[nr][nc]) {
                d = (d + 1) % 4
            }

            r += dirs[d][0]; c += dirs[d][1]
        }

        return result
    }
}
