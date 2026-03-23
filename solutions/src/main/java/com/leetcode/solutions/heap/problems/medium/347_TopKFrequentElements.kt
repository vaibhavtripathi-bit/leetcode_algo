package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue

/**
 * 347. Top K Frequent Elements
 * https://leetcode.com/problems/top-k-frequent-elements/
 *
 * Given an integer array nums and an integer k, return the k most frequent elements.
 * You may return the answer in any order.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - k is in the range [1, the number of unique elements in the array].
 * - It is guaranteed that the answer is unique.
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n),
 * where n is the array's size.
 *
 * Companies: Amazon, Facebook, Google, Microsoft, Apple, Uber, Twitter, Netflix
 */

/**
 * Solution 1: Min Heap
 * Use frequency map and min-heap of size k to get top k frequent elements.
 *
 * Time Complexity: O(n log k)
 * Space Complexity: O(n)
 */
fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val frequencyMap = nums.groupingBy { it }.eachCount()

    val minHeap = PriorityQueue<Int>(compareBy { frequencyMap[it] })

    for (num in frequencyMap.keys) {
        minHeap.offer(num)
        if (minHeap.size > k) {
            minHeap.poll()
        }
    }

    return minHeap.toIntArray()
}

/**
 * Solution 2: Max Heap
 * Use max-heap ordered by frequency and extract k elements.
 *
 * Time Complexity: O(n + m log m) where m is unique elements
 * Space Complexity: O(n)
 */
fun topKFrequentMaxHeap(nums: IntArray, k: Int): IntArray {
    val frequencyMap = HashMap<Int, Int>()
    for (num in nums) {
        frequencyMap[num] = frequencyMap.getOrDefault(num, 0) + 1
    }

    val maxHeap = PriorityQueue<Int>(compareByDescending { frequencyMap[it] })
    maxHeap.addAll(frequencyMap.keys)

    return IntArray(k) { maxHeap.poll() }
}

/**
 * Solution 3: Bucket Sort
 * Use frequency as index for buckets. O(n) time complexity.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
fun topKFrequentBucketSort(nums: IntArray, k: Int): IntArray {
    val frequencyMap = HashMap<Int, Int>()
    for (num in nums) {
        frequencyMap[num] = frequencyMap.getOrDefault(num, 0) + 1
    }

    @Suppress("UNCHECKED_CAST")
    val buckets = Array(nums.size + 1) { mutableListOf<Int>() }

    for ((num, freq) in frequencyMap) {
        buckets[freq].add(num)
    }

    val result = mutableListOf<Int>()
    for (i in buckets.size - 1 downTo 0) {
        for (num in buckets[i]) {
            result.add(num)
            if (result.size == k) {
                return result.toIntArray()
            }
        }
    }

    return result.toIntArray()
}

/**
 * Solution 4: QuickSelect on Frequency
 * Use QuickSelect to find kth most frequent without full sorting.
 *
 * Time Complexity: O(n) average case
 * Space Complexity: O(n)
 */
fun topKFrequentQuickSelect(nums: IntArray, k: Int): IntArray {
    val frequencyMap = nums.groupingBy { it }.eachCount()
    val uniqueNums = frequencyMap.keys.toIntArray()

    fun partition(left: Int, right: Int, pivotIdx: Int): Int {
        val pivotFreq = frequencyMap[uniqueNums[pivotIdx]]!!
        uniqueNums[pivotIdx] = uniqueNums[right].also { uniqueNums[right] = uniqueNums[pivotIdx] }

        var storeIdx = left
        for (i in left until right) {
            if (frequencyMap[uniqueNums[i]]!! < pivotFreq) {
                uniqueNums[storeIdx] = uniqueNums[i].also { uniqueNums[i] = uniqueNums[storeIdx] }
                storeIdx++
            }
        }

        uniqueNums[storeIdx] = uniqueNums[right].also { uniqueNums[right] = uniqueNums[storeIdx] }
        return storeIdx
    }

    fun quickSelect(left: Int, right: Int, kSmallest: Int) {
        if (left >= right) return

        val pivotIdx = left + (0..(right - left)).random()
        val newPivotIdx = partition(left, right, pivotIdx)

        when {
            newPivotIdx == kSmallest -> return
            newPivotIdx < kSmallest -> quickSelect(newPivotIdx + 1, right, kSmallest)
            else -> quickSelect(left, newPivotIdx - 1, kSmallest)
        }
    }

    val n = uniqueNums.size
    quickSelect(0, n - 1, n - k)

    return uniqueNums.sliceArray(n - k until n)
}
