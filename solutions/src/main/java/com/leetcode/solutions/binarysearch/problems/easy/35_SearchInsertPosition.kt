package com.leetcode.solutions.binarysearch.problems.easy

/**
 * LeetCode 35: Search Insert Position
 * https://leetcode.com/problems/search-insert-position/
 *
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft
 *
 * Given a sorted array and a target, return the index if found.
 * If not, return the index where it would be inserted to keep sorted order.
 *
 * Example: nums = [1,3,5,6], target = 5 → 2
 * Example: nums = [1,3,5,6], target = 2 → 1
 */

/** Solution 1: Binary Search - Time: O(log n), Space: O(1) */
class SearchInsert1 {
    fun searchInsert(nums: IntArray, target: Int): Int {
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

        return left
    }
}

/** Solution 2: Find First Element >= Target - Time: O(log n), Space: O(1) */
class SearchInsert2 {
    fun searchInsert(nums: IntArray, target: Int): Int {
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

/** Solution 3: Linear scan - Time: O(n), Space: O(1) */
class SearchInsert3 {
    fun searchInsert(nums: IntArray, target: Int): Int {
        return nums.indexOfFirst { it >= target }.takeIf { it >= 0 } ?: nums.size
    }
}
