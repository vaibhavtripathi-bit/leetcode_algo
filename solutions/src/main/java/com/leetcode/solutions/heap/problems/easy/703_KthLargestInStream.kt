package com.leetcode.solutions.heap.problems.easy

import java.util.PriorityQueue

/**
 * LeetCode 703: Kth Largest Element in a Stream
 * https://leetcode.com/problems/kth-largest-element-in-a-stream/
 *
 * Difficulty: Easy
 * Companies: Amazon, Google, Facebook
 *
 * Design a class to find the kth largest element in a stream.
 * add(val) returns the kth largest after adding.
 */

/** Solution 1: Min-Heap of Size K */
class KthLargest(private val k: Int, nums: IntArray) {

    private val minHeap = PriorityQueue<Int>()

    init {
        nums.forEach { add(it) }
    }

    fun add(value: Int): Int {
        minHeap.offer(value)
        if (minHeap.size > k) minHeap.poll()
        return minHeap.peek()!!
    }
}

/** Solution 2: Sorted List (for understanding, not optimal) */
class KthLargestSorted(private val k: Int, nums: IntArray) {

    private val sorted = sortedListOf(*nums.toTypedArray())

    fun add(value: Int): Int {
        insertSorted(value)
        if (sorted.size > k * 2) trimToK()
        return getKthLargest()
    }

    private fun insertSorted(value: Int) {
        sorted.add(value)
    }

    private fun trimToK() {
        while (sorted.size > k) sorted.removeAt(0)
    }

    private fun getKthLargest() = sorted[sorted.size - k]
}
