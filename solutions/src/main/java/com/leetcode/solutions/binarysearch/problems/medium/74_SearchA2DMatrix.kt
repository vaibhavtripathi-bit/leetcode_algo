package com.leetcode.solutions.binarysearch.problems.medium

/**
 * LeetCode 74: Search a 2D Matrix
 * https://leetcode.com/problems/search-a-2d-matrix/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft
 *
 * Each row sorted, first integer of each row > last of previous row.
 * Search for target in O(log(m*n)).
 *
 * Example: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3 → true
 */

/** Solution 1: Treat as 1D Sorted Array - Time: O(log(m*n)), Space: O(1) */
class SearchMatrix1 {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        val rows = matrix.size
        val cols = matrix[0].size

        var left = 0
        var right = rows * cols - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            val value = getElement(matrix, mid, cols)

            when {
                value == target -> return true
                value < target  -> left = mid + 1
                else            -> right = mid - 1
            }
        }

        return false
    }

    private fun getElement(matrix: Array<IntArray>, index: Int, cols: Int): Int {
        return matrix[index / cols][index % cols]
    }
}

/** Solution 2: Row then Column - Time: O(log m + log n), Space: O(1) */
class SearchMatrix2 {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        val row = findTargetRow(matrix, target) ?: return false
        return binarySearchInRow(matrix[row], target)
    }

    private fun findTargetRow(matrix: Array<IntArray>, target: Int): Int? {
        var top = 0
        var bottom = matrix.size - 1

        while (top <= bottom) {
            val mid = top + (bottom - top) / 2
            when {
                matrix[mid][0] <= target && target <= matrix[mid].last() -> return mid
                matrix[mid][0] > target -> bottom = mid - 1
                else -> top = mid + 1
            }
        }

        return null
    }

    private fun binarySearchInRow(row: IntArray, target: Int): Boolean {
        var left = 0
        var right = row.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            when {
                row[mid] == target -> return true
                row[mid] < target  -> left = mid + 1
                else               -> right = mid - 1
            }
        }

        return false
    }
}

/** Solution 3: Start from Top-Right corner - Time: O(m + n), Space: O(1) */
class SearchMatrix3 {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        var row = 0
        var col = matrix[0].size - 1

        while (row < matrix.size && col >= 0) {
            when {
                matrix[row][col] == target -> return true
                matrix[row][col] < target  -> row++
                else                       -> col--
            }
        }

        return false
    }
}
