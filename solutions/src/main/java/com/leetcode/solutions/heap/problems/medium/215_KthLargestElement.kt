package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue

/**
 * LeetCode 215: Kth Largest Element in an Array
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Find the kth largest element (not kth distinct).
 *
 * Example: nums = [3,2,1,5,6,4], k = 2 → 5
 */

/** Solution 1: Min-Heap of Size K - Time: O(n log k), Space: O(k) */
class KthLargestElement1 {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        val minHeap = PriorityQueue<Int>()

        for (num in nums) {
            minHeap.offer(num)
            if (minHeap.size > k) minHeap.poll()
        }

        return minHeap.peek()!!
    }
}

/** Solution 2: Sort - Time: O(n log n), Space: O(1) */
class KthLargestElement2 {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        nums.sort()
        return nums[nums.size - k]
    }
}

/** Solution 3: QuickSelect - Time: O(n) avg, Space: O(1) */
class KthLargestElement3 {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        val targetIndex = nums.size - k
        return quickSelect(nums, 0, nums.lastIndex, targetIndex)
    }

    private fun quickSelect(nums: IntArray, left: Int, right: Int, target: Int): Int {
        val pivot = partition(nums, left, right)

        return when {
            pivot == target -> nums[pivot]
            pivot < target  -> quickSelect(nums, pivot + 1, right, target)
            else            -> quickSelect(nums, left, pivot - 1, target)
        }
    }

    private fun partition(nums: IntArray, left: Int, right: Int): Int {
        val pivot = nums[right]
        var i = left

        for (j in left until right) {
            if (nums[j] <= pivot) {
                swap(nums, i++, j)
            }
        }

        swap(nums, i, right)
        return i
    }

    private fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]; nums[i] = nums[j]; nums[j] = temp
    }
}

/** Solution 4: Max-Heap - Time: O(k log n), Space: O(n) */
class KthLargestElement4 {
    fun findKthLargest(nums: IntArray, k: Int): Int {
        val maxHeap = PriorityQueue<Int>(reverseOrder())
        nums.forEach { maxHeap.offer(it) }

        repeat(k - 1) { maxHeap.poll() }

        return maxHeap.peek()!!
    }
}
