package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue
import java.util.Collections

/**
 * 215. Kth Largest Element in an Array
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * Can you solve it without sorting?
 *
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 *
 * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 *
 * Constraints:
 * - 1 <= k <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 *
 * Companies: Facebook, Amazon, Google, Microsoft, Apple, LinkedIn, Uber, Netflix
 */

/**
 * Solution 1: Min Heap of Size K
 * Maintain a min-heap of size k. After processing all elements,
 * the root is the kth largest element.
 *
 * Time Complexity: O(n log k)
 * Space Complexity: O(k)
 */
fun findKthLargest(nums: IntArray, k: Int): Int {
    val minHeap = PriorityQueue<Int>()

    for (num in nums) {
        minHeap.offer(num)
        if (minHeap.size > k) {
            minHeap.poll()
        }
    }

    return minHeap.peek()
}

/**
 * Solution 2: Max Heap
 * Use max-heap and poll k-1 times, then peek gives kth largest.
 *
 * Time Complexity: O(n + k log n)
 * Space Complexity: O(n)
 */
fun findKthLargestMaxHeap(nums: IntArray, k: Int): Int {
    val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())
    nums.forEach { maxHeap.offer(it) }

    repeat(k - 1) {
        maxHeap.poll()
    }

    return maxHeap.peek()
}

/**
 * Solution 3: QuickSelect (Hoare's Selection Algorithm)
 * Partition-based selection with average O(n) time complexity.
 *
 * Time Complexity: O(n) average, O(n^2) worst case
 * Space Complexity: O(1)
 */
fun findKthLargestQuickSelect(nums: IntArray, k: Int): Int {
    val targetIndex = nums.size - k

    fun partition(left: Int, right: Int): Int {
        val pivot = nums[right]
        var storeIndex = left

        for (i in left until right) {
            if (nums[i] < pivot) {
                nums[storeIndex] = nums[i].also { nums[i] = nums[storeIndex] }
                storeIndex++
            }
        }
        nums[storeIndex] = nums[right].also { nums[right] = nums[storeIndex] }
        return storeIndex
    }

    fun quickSelect(left: Int, right: Int): Int {
        if (left == right) return nums[left]

        val pivotIndex = partition(left, right)
        return when {
            pivotIndex == targetIndex -> nums[pivotIndex]
            pivotIndex < targetIndex -> quickSelect(pivotIndex + 1, right)
            else -> quickSelect(left, pivotIndex - 1)
        }
    }

    return quickSelect(0, nums.size - 1)
}

/**
 * Solution 4: QuickSelect with Random Pivot
 * Randomized pivot selection for better average case performance.
 *
 * Time Complexity: O(n) average, O(n^2) worst case (very rare with random pivot)
 * Space Complexity: O(1)
 */
fun findKthLargestRandomized(nums: IntArray, k: Int): Int {
    val targetIndex = nums.size - k
    val random = java.util.Random()

    fun partition(left: Int, right: Int): Int {
        val pivotIdx = left + random.nextInt(right - left + 1)
        nums[pivotIdx] = nums[right].also { nums[right] = nums[pivotIdx] }

        val pivot = nums[right]
        var storeIndex = left

        for (i in left until right) {
            if (nums[i] < pivot) {
                nums[storeIndex] = nums[i].also { nums[i] = nums[storeIndex] }
                storeIndex++
            }
        }
        nums[storeIndex] = nums[right].also { nums[right] = nums[storeIndex] }
        return storeIndex
    }

    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        val pivotIndex = partition(left, right)
        when {
            pivotIndex == targetIndex -> return nums[pivotIndex]
            pivotIndex < targetIndex -> left = pivotIndex + 1
            else -> right = pivotIndex - 1
        }
    }

    return nums[left]
}
