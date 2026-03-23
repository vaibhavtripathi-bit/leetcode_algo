package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue

/**
 * LeetCode 347: Top K Frequent Elements
 * https://leetcode.com/problems/top-k-frequent-elements/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Return the k most frequent elements. Answer can be in any order.
 *
 * Example: nums = [1,1,1,2,2,3], k = 2 → [1,2]
 */

/** Solution 1: Min-Heap by Frequency - Time: O(n log k), Space: O(n) */
class TopKFrequent1 {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val freqMap = buildFrequencyMap(nums)

        val minHeap = PriorityQueue<Map.Entry<Int, Int>>(compareBy { it.value })

        for (entry in freqMap.entries) {
            minHeap.offer(entry)
            if (minHeap.size > k) minHeap.poll()
        }

        return minHeap.map { it.key }.toIntArray()
    }

    private fun buildFrequencyMap(nums: IntArray): Map<Int, Int> {
        val map = HashMap<Int, Int>()
        nums.forEach { map[it] = map.getOrDefault(it, 0) + 1 }
        return map
    }
}

/** Solution 2: Bucket Sort - Time: O(n), Space: O(n) */
class TopKFrequent2 {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val freqMap = HashMap<Int, Int>()
        nums.forEach { freqMap[it] = freqMap.getOrDefault(it, 0) + 1 }

        val buckets = buildBuckets(freqMap, nums.size)

        return collectTopK(buckets, k)
    }

    private fun buildBuckets(freqMap: Map<Int, Int>, maxSize: Int): Array<MutableList<Int>> {
        val buckets = Array(maxSize + 1) { mutableListOf<Int>() }
        freqMap.forEach { (num, freq) -> buckets[freq].add(num) }
        return buckets
    }

    private fun collectTopK(buckets: Array<MutableList<Int>>, k: Int): IntArray {
        val result = mutableListOf<Int>()

        for (i in buckets.lastIndex downTo 0) {
            result.addAll(buckets[i])
            if (result.size >= k) break
        }

        return result.take(k).toIntArray()
    }
}

/** Solution 3: Sort by frequency - Time: O(n log n), Space: O(n) */
class TopKFrequent3 {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val freqMap = nums.groupingBy { it }.eachCount()
        return freqMap.entries
            .sortedByDescending { it.value }
            .take(k)
            .map { it.key }
            .toIntArray()
    }
}
