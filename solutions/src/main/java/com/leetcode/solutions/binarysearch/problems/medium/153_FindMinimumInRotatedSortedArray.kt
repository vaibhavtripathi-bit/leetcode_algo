package com.leetcode.solutions.binarysearch.problems.medium

/**
 * LeetCode 153: Find Minimum in Rotated Sorted Array
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Find the minimum element in a rotated sorted array with unique elements.
 *
 * Example: nums = [3,4,5,1,2] → 1
 */

/** Solution 1: Binary Search on Pivot - Time: O(log n), Space: O(1) */
class FindMin1 {
    fun findMin(nums: IntArray): Int {
        var left = 0
        var right = nums.size - 1

        while (left < right) {
            val mid = left + (right - left) / 2

            if (isRightHalfRotated(nums, mid, right)) left = mid + 1
            else right = mid
        }

        return nums[left]
    }

    private fun isRightHalfRotated(nums: IntArray, mid: Int, right: Int) =
        nums[mid] > nums[right]
}

/** Solution 2: Compare with Left Boundary - Time: O(log n), Space: O(1) */
class FindMin2 {
    fun findMin(nums: IntArray): Int {
        if (nums.size == 1 || nums.first() < nums.last()) return nums.first()

        var left = 0
        var right = nums.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (nums[mid] > nums[mid + 1]) return nums[mid + 1]
            if (nums[mid - 1] > nums[mid]) return nums[mid]

            if (nums[mid] > nums[0]) left = mid + 1
            else right = mid - 1
        }

        return -1
    }
}

/** Solution 3: Recursive - Time: O(log n), Space: O(log n) */
class FindMin3 {
    fun findMin(nums: IntArray): Int = find(nums, 0, nums.size - 1)

    private fun find(nums: IntArray, left: Int, right: Int): Int {
        if (left == right) return nums[left]
        if (nums[left] < nums[right]) return nums[left]

        val mid = left + (right - left) / 2
        return if (nums[mid] >= nums[left]) find(nums, mid + 1, right)
               else find(nums, left, mid)
    }
}
