package com.leetcode.solutions.binarysearch.problems.easy

/**
 * LeetCode 704: Binary Search
 * https://leetcode.com/problems/binary-search/
 *
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Given a sorted array of integers and a target, return the index.
 * Return -1 if target not found.
 *
 * Example: nums = [-1,0,3,5,9,12], target = 9 → 4
 */

/** Solution 1: Standard Binary Search - Time: O(log n), Space: O(1) */
class BinarySearch1 {
    fun search(nums: IntArray, target: Int): Int {
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
}

/** Solution 2: Recursive - Time: O(log n), Space: O(log n) */
class BinarySearch2 {
    fun search(nums: IntArray, target: Int): Int {
        return searchHelper(nums, target, 0, nums.size - 1)
    }

    private fun searchHelper(nums: IntArray, target: Int, left: Int, right: Int): Int {
        if (left > right) return -1

        val mid = left + (right - left) / 2

        return when {
            nums[mid] == target -> mid
            nums[mid] < target  -> searchHelper(nums, target, mid + 1, right)
            else                -> searchHelper(nums, target, left, mid - 1)
        }
    }
}

/** Solution 3: Kotlin built-in - Time: O(log n), Space: O(1) */
class BinarySearch3 {
    fun search(nums: IntArray, target: Int): Int {
        val idx = nums.toList().binarySearch(target)
        return if (idx < 0) -1 else idx
    }
}
