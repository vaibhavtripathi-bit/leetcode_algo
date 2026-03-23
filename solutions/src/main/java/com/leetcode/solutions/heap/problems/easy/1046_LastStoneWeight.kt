package com.leetcode.solutions.heap.problems.easy

import java.util.PriorityQueue

/**
 * LeetCode 1046: Last Stone Weight
 * https://leetcode.com/problems/last-stone-weight/
 *
 * Difficulty: Easy
 * Companies: Amazon
 *
 * Smash two heaviest stones. If equal → both destroyed. If different → difference remains.
 * Return weight of last stone (0 if none).
 *
 * Example: stones = [2,7,4,1,8,1] → 1
 */

/** Solution 1: Max-Heap - Time: O(n log n), Space: O(n) */
class LastStoneWeight1 {
    fun lastStoneWeight(stones: IntArray): Int {
        val maxHeap = buildMaxHeap(stones)

        while (maxHeap.size > 1) {
            val heaviest = maxHeap.poll()!!
            val secondHeaviest = maxHeap.poll()!!

            val remaining = smash(heaviest, secondHeaviest)
            if (remaining > 0) maxHeap.offer(remaining)
        }

        return maxHeap.peek() ?: 0
    }

    private fun buildMaxHeap(stones: IntArray): PriorityQueue<Int> {
        val heap = PriorityQueue<Int>(reverseOrder())
        stones.forEach { heap.offer(it) }
        return heap
    }

    private fun smash(heavy: Int, light: Int) = heavy - light
}

/** Solution 2: Sorted Array Simulation - Time: O(n² log n), Space: O(n) */
class LastStoneWeight2 {
    fun lastStoneWeight(stones: IntArray): Int {
        val list = stones.toMutableList()

        while (list.size > 1) {
            list.sort()
            val y = list.removeLast()
            val x = list.removeLast()
            if (y != x) list.add(y - x)
        }

        return list.firstOrNull() ?: 0
    }
}
