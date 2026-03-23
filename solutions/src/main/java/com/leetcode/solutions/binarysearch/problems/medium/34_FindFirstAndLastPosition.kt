package com.leetcode.solutions.binarysearch.problems.medium

/**
 * LeetCode 34: Find First and Last Position of Element in Sorted Array
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Find the starting and ending position of a target in sorted array.
 * Return [-1,-1] if not found. Must be O(log n).
 *
 * Example: nums = [5,7,7,8,8,10], target = 8 → [3,4]
 */

/** Solution 1: Two Binary Searches - Time: O(log n), Space: O(1) */
class SearchRange1 {
    fun searchRange(nums: IntArray, target: Int): IntArray {
        val first = findFirst(nums, target)
        val last = findLast(nums, target)
        return intArrayOf(first, last)
    }

    private fun findFirst(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1
        var result = -1

        while (left <= right) {
            val mid = left + (right - left) / 2
            when {
                nums[mid] == target -> { result = mid; right = mid - 1 }
                nums[mid] < target  -> left = mid + 1
                else                -> right = mid - 1
            }
        }

        return result
    }

    private fun findLast(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1
        var result = -1

        while (left <= right) {
            val mid = left + (right - left) / 2
            when {
                nums[mid] == target -> { result = mid; left = mid + 1 }
                nums[mid] < target  -> left = mid + 1
                else                -> right = mid - 1
            }
        }

        return result
    }
}

/** Solution 2: Lower Bound / Upper Bound Helper - Time: O(log n), Space: O(1) */
class SearchRange2 {
    fun searchRange(nums: IntArray, target: Int): IntArray {
        val first = lowerBound(nums, target)

        if (first == nums.size || nums[first] != target) return intArrayOf(-1, -1)

        val last = lowerBound(nums, target + 1) - 1
        return intArrayOf(first, last)
    }

    private fun lowerBound(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size

        while (left < right) {
            val mid = left + (right - left) / 2
            if (nums[mid] < target) left = mid + 1
            else right = mid
        }

        return left
    }
}

/** Solution 3: Single Pass with Early Return - Time: O(log n), Space: O(1) */
class SearchRange3 {
    fun searchRange(nums: IntArray, target: Int): IntArray {
        if (nums.isEmpty()) return intArrayOf(-1, -1)

        val anyIndex = findAny(nums, target)
        if (anyIndex == -1) return intArrayOf(-1, -1)

        return intArrayOf(
            expandLeft(nums, target, anyIndex),
            expandRight(nums, target, anyIndex)
        )
    }

    private fun findAny(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            when {
                nums[mid] == target -> return mid
                nums[mid] < target  -> left = mid + 1
                else                -> right = mid - 1
            }
        }

        return -1
    }

    private fun expandLeft(nums: IntArray, target: Int, from: Int): Int {
        var result = from
        var left = 0
        var right = from - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            if (nums[mid] == target) { result = mid; right = mid - 1 }
            else left = mid + 1
        }

        return result
    }

    private fun expandRight(nums: IntArray, target: Int, from: Int): Int {
        var result = from
        var left = from + 1
        var right = nums.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            if (nums[mid] == target) { result = mid; left = mid + 1 }
            else right = mid - 1
        }

        return result
    }
}
