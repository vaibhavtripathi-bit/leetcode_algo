package com.leetcode.solutions.binarysearch.problems.medium

/**
 * LeetCode 33: Search in Rotated Sorted Array
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Sorted array rotated at some pivot. Search for target in O(log n).
 *
 * Example: nums = [4,5,6,7,0,1,2], target = 0 → 4
 * Example: nums = [4,5,6,7,0,1,2], target = 3 → -1
 */

/** Solution 1: One-Pass Binary Search - Time: O(log n), Space: O(1) */
class SearchRotated1 {
    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (nums[mid] == target) return mid

            if (isLeftHalfSorted(nums, left, mid)) {
                if (targetInLeftHalf(nums, left, mid, target)) right = mid - 1
                else left = mid + 1
            } else {
                if (targetInRightHalf(nums, mid, right, target)) left = mid + 1
                else right = mid - 1
            }
        }

        return -1
    }

    private fun isLeftHalfSorted(nums: IntArray, left: Int, mid: Int) =
        nums[left] <= nums[mid]

    private fun targetInLeftHalf(nums: IntArray, left: Int, mid: Int, target: Int) =
        nums[left] <= target && target < nums[mid]

    private fun targetInRightHalf(nums: IntArray, mid: Int, right: Int, target: Int) =
        nums[mid] < target && target <= nums[right]
}

/** Solution 2: Find Pivot First, then Standard Binary Search - Time: O(log n), Space: O(1) */
class SearchRotated2 {
    fun search(nums: IntArray, target: Int): Int {
        val pivotIndex = findPivot(nums)
        if (pivotIndex == 0) return standardSearch(nums, target, 0, nums.size - 1)

        if (target >= nums[0]) return standardSearch(nums, target, 0, pivotIndex - 1)
        return standardSearch(nums, target, pivotIndex, nums.size - 1)
    }

    private fun findPivot(nums: IntArray): Int {
        var left = 0
        var right = nums.size - 1

        while (left < right) {
            val mid = left + (right - left) / 2
            if (nums[mid] > nums[right]) left = mid + 1
            else right = mid
        }

        return left
    }

    private fun standardSearch(nums: IntArray, target: Int, left: Int, right: Int): Int {
        var l = left
        var r = right

        while (l <= r) {
            val mid = l + (r - l) / 2
            when {
                nums[mid] == target -> return mid
                nums[mid] < target  -> l = mid + 1
                else                -> r = mid - 1
            }
        }

        return -1
    }
}

/** Solution 3: Recursive - Time: O(log n), Space: O(log n) */
class SearchRotated3 {
    fun search(nums: IntArray, target: Int): Int {
        return search(nums, target, 0, nums.size - 1)
    }

    private fun search(nums: IntArray, target: Int, left: Int, right: Int): Int {
        if (left > right) return -1

        val mid = left + (right - left) / 2
        if (nums[mid] == target) return mid

        return if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid])
                search(nums, target, left, mid - 1)
            else
                search(nums, target, mid + 1, right)
        } else {
            if (nums[mid] < target && target <= nums[right])
                search(nums, target, mid + 1, right)
            else
                search(nums, target, left, mid - 1)
        }
    }
}
