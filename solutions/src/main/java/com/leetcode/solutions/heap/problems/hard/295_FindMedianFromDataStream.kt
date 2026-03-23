package com.leetcode.solutions.heap.problems.hard

import java.util.PriorityQueue

/**
 * LeetCode 295: Find Median from Data Stream
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Implement MedianFinder that supports:
 * - addNum(num): add integer to data structure
 * - findMedian(): return median of all elements so far
 */

/**
 * Solution 1: Two Heaps (Optimal) - Time: O(log n) add, O(1) median
 *
 * Lower half in max-heap, upper half in min-heap.
 * Max-heap can have at most 1 extra element.
 */
class MedianFinder1 {
    private val lowerHalf = PriorityQueue<Int>(reverseOrder())  // max-heap
    private val upperHalf = PriorityQueue<Int>()                 // min-heap

    fun addNum(num: Int) {
        addToCorrectHalf(num)
        rebalance()
    }

    fun findMedian(): Double {
        return if (isOddTotal()) lowerHalf.peek()!!.toDouble()
               else averageOfTops()
    }

    private fun addToCorrectHalf(num: Int) {
        if (lowerHalf.isEmpty() || num <= lowerHalf.peek()!!) {
            lowerHalf.offer(num)
        } else {
            upperHalf.offer(num)
        }
    }

    private fun rebalance() {
        when {
            lowerHalf.size > upperHalf.size + 1 -> upperHalf.offer(lowerHalf.poll())
            upperHalf.size > lowerHalf.size     -> lowerHalf.offer(upperHalf.poll())
        }
    }

    private fun isOddTotal() = lowerHalf.size > upperHalf.size

    private fun averageOfTops() = (lowerHalf.peek()!! + upperHalf.peek()!!) / 2.0
}

/**
 * Solution 2: Sorted List (Simple but slow) - Time: O(n) add, O(1) median
 */
class MedianFinder2 {
    private val data = sortedListOf<Int>()

    fun addNum(num: Int) {
        data.add(num)
    }

    fun findMedian(): Double {
        val n = data.size
        return if (n % 2 == 1) data[n / 2].toDouble()
               else (data[n / 2 - 1] + data[n / 2]) / 2.0
    }
}
