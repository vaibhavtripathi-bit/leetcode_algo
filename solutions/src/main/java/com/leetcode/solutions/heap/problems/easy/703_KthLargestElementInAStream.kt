package com.leetcode.solutions.heap.problems.easy

import java.util.PriorityQueue

/**
 * 703. Kth Largest Element in a Stream
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/
 *
 * Design a class to find the kth largest element in a stream. Note that it is
 * the kth largest element in the sorted order, not the kth distinct element.
 *
 * Implement KthLargest class:
 * - KthLargest(int k, int[] nums) Initializes the object with the integer k and the stream of integers nums.
 * - int add(int val) Appends the integer val to the stream and returns the element representing
 *   the kth largest element in the stream.
 *
 * Example 1:
 * Input: ["KthLargest", "add", "add", "add", "add", "add"]
 *        [[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
 * Output: [null, 4, 5, 5, 8, 8]
 *
 * Constraints:
 * - 1 <= k <= 10^4
 * - 0 <= nums.length <= 10^4
 * - -10^4 <= nums[i] <= 10^4
 * - -10^4 <= val <= 10^4
 * - At most 10^4 calls will be made to add.
 * - It is guaranteed that there will be at least k elements in the array when you search for the kth element.
 *
 * Companies: Amazon, Facebook, Google, Microsoft, Apple
 */

/**
 * Solution 1: Min Heap
 * Maintain a min-heap of size k. The root is always the kth largest element.
 *
 * Time Complexity: O(n log k) for initialization, O(log k) for each add
 * Space Complexity: O(k)
 */
class KthLargest(private val k: Int, nums: IntArray) {
    private val minHeap = PriorityQueue<Int>()

    init {
        for (num in nums) {
            add(num)
        }
    }

    fun add(`val`: Int): Int {
        minHeap.offer(`val`)
        if (minHeap.size > k) {
            minHeap.poll()
        }
        return minHeap.peek()
    }
}

/**
 * Solution 2: Min Heap with Early Rejection
 * Optimization: Only add to heap if value could be in top k.
 *
 * Time Complexity: O(n log k) for initialization, O(log k) for each add (worst case)
 * Space Complexity: O(k)
 */
class KthLargestOptimized(private val k: Int, nums: IntArray) {
    private val minHeap = PriorityQueue<Int>()

    init {
        for (num in nums) {
            addInternal(num)
        }
    }

    private fun addInternal(value: Int) {
        if (minHeap.size < k) {
            minHeap.offer(value)
        } else if (value > minHeap.peek()) {
            minHeap.poll()
            minHeap.offer(value)
        }
    }

    fun add(`val`: Int): Int {
        addInternal(`val`)
        return minHeap.peek()
    }
}

/**
 * Solution 3: Using Sorted List (Brute Force)
 * Maintain a sorted list and return kth largest after each add.
 *
 * Time Complexity: O(n log n) for initialization, O(n) for each add
 * Space Complexity: O(n)
 */
class KthLargestBruteForce(private val k: Int, nums: IntArray) {
    private val sortedList = nums.toMutableList().apply { sort() }

    fun add(`val`: Int): Int {
        val insertPos = sortedList.binarySearch(`val`).let { if (it < 0) -(it + 1) else it }
        sortedList.add(insertPos, `val`)
        return sortedList[sortedList.size - k]
    }
}

/**
 * Solution 4: TreeMap with Frequency Count
 * Use TreeMap to maintain sorted elements with their frequencies.
 *
 * Time Complexity: O(n log n) for initialization, O(log n) for each add
 * Space Complexity: O(n)
 */
class KthLargestTreeMap(private val k: Int, nums: IntArray) {
    private val treeMap = java.util.TreeMap<Int, Int>()
    private var totalCount = 0

    init {
        for (num in nums) {
            addInternal(num)
        }
    }

    private fun addInternal(value: Int) {
        treeMap[value] = treeMap.getOrDefault(value, 0) + 1
        totalCount++
    }

    fun add(`val`: Int): Int {
        addInternal(`val`)

        var count = 0
        for ((key, freq) in treeMap.descendingMap()) {
            count += freq
            if (count >= k) {
                return key
            }
        }
        return treeMap.firstKey()
    }
}
