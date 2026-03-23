package com.leetcode.solutions.heap.problems.easy

import java.util.PriorityQueue
import java.util.Collections

/**
 * 1046. Last Stone Weight
 * https://leetcode.com/problems/last-stone-weight/
 *
 * You are given an array of integers stones where stones[i] is the weight of the ith stone.
 *
 * We are playing a game with the stones. On each turn, we choose the heaviest two stones
 * and smash them together. Suppose the heaviest two stones have weights x and y with x <= y.
 * The result of this smash is:
 * - If x == y, both stones are destroyed, and
 * - If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
 *
 * At the end of the game, there is at most one stone left.
 * Return the weight of the last remaining stone. If there are no stones left, return 0.
 *
 * Example 1:
 * Input: stones = [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
 * we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
 * we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
 * we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.
 *
 * Example 2:
 * Input: stones = [1]
 * Output: 1
 *
 * Constraints:
 * - 1 <= stones.length <= 30
 * - 1 <= stones[i] <= 1000
 *
 * Companies: Amazon, Google, Facebook, Microsoft, Apple, Adobe
 */

/**
 * Solution 1: Max Heap
 * Use a max-heap to always get the two heaviest stones efficiently.
 *
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 */
fun lastStoneWeight(stones: IntArray): Int {
    val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())
    for (stone in stones) {
        maxHeap.offer(stone)
    }

    while (maxHeap.size > 1) {
        val first = maxHeap.poll()
        val second = maxHeap.poll()
        if (first != second) {
            maxHeap.offer(first - second)
        }
    }

    return if (maxHeap.isEmpty()) 0 else maxHeap.peek()
}

/**
 * Solution 2: Max Heap with Custom Comparator
 * Same approach using lambda comparator.
 *
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 */
fun lastStoneWeightV2(stones: IntArray): Int {
    val maxHeap = PriorityQueue<Int> { a, b -> b - a }
    stones.forEach { maxHeap.offer(it) }

    while (maxHeap.size >= 2) {
        val diff = maxHeap.poll() - maxHeap.poll()
        if (diff > 0) {
            maxHeap.offer(diff)
        }
    }

    return maxHeap.poll() ?: 0
}

/**
 * Solution 3: Sorting Approach (Simulation)
 * Sort after each smash operation.
 *
 * Time Complexity: O(n^2 log n)
 * Space Complexity: O(n)
 */
fun lastStoneWeightSorting(stones: IntArray): Int {
    val stoneList = stones.toMutableList()

    while (stoneList.size > 1) {
        stoneList.sortDescending()
        val first = stoneList.removeAt(0)
        val second = stoneList.removeAt(0)
        if (first != second) {
            stoneList.add(first - second)
        }
    }

    return if (stoneList.isEmpty()) 0 else stoneList[0]
}

/**
 * Solution 4: Bucket Sort Approach
 * Since stone weights are bounded (1-1000), use bucket sort for O(1) access.
 *
 * Time Complexity: O(n + W) where W is max stone weight
 * Space Complexity: O(W)
 */
fun lastStoneWeightBucket(stones: IntArray): Int {
    val maxWeight = stones.maxOrNull() ?: return 0
    val buckets = IntArray(maxWeight + 1)

    for (stone in stones) {
        buckets[stone]++
    }

    var biggestWeight = maxWeight
    var currentWeight = 0

    while (biggestWeight > 0) {
        if (buckets[biggestWeight] == 0) {
            biggestWeight--
            continue
        }

        if (currentWeight == 0) {
            buckets[biggestWeight]--
            currentWeight = biggestWeight
        } else {
            buckets[biggestWeight]--
            val diff = currentWeight - biggestWeight
            currentWeight = 0
            if (diff > 0) {
                buckets[diff]++
            }
        }
    }

    return currentWeight
}
