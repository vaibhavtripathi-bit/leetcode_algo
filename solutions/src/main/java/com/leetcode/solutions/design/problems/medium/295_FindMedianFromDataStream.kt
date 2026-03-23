package com.leetcode.solutions.design.problems.medium

import java.util.PriorityQueue

/**
 * LeetCode 295: Find Median from Data Stream
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * Difficulty: Hard (tagged Medium)
 * Companies: All FAANG
 *
 * Design a structure supporting addNum and findMedian in efficient time.
 *
 * Key insight: Two heaps — max-heap for lower half, min-heap for upper half.
 * Invariant: lowerMax.size >= upperMin.size, sizes differ by at most 1.
 */
class MedianFinder {
    private val lowerMax = PriorityQueue<Int>(compareByDescending { it })  // max-heap
    private val upperMin = PriorityQueue<Int>()                             // min-heap

    fun addNum(num: Int) {
        lowerMax.offer(num)
        balanceAndRebalance()
    }

    private fun balanceAndRebalance() {
        if (upperMin.isNotEmpty() && lowerMax.peek() > upperMin.peek()) {
            upperMin.offer(lowerMax.poll())
        }
        if (lowerMax.size > upperMin.size + 1) {
            upperMin.offer(lowerMax.poll())
        }
        if (upperMin.size > lowerMax.size) {
            lowerMax.offer(upperMin.poll())
        }
    }

    fun findMedian(): Double = when {
        lowerMax.size > upperMin.size -> lowerMax.peek().toDouble()
        else -> (lowerMax.peek() + upperMin.peek()) / 2.0
    }
}
