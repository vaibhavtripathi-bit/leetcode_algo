package com.leetcode.solutions.heap.problems.hard

import java.util.PriorityQueue
import java.util.Collections
import java.util.TreeMap

/**
 * 295. Find Median from Data Stream
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * The median is the middle value in an ordered integer list. If the size of the list is even,
 * there is no middle value, and the median is the mean of the two middle values.
 *
 * For example, for arr = [2,3,4], the median is 3.
 * For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 *
 * Implement the MedianFinder class:
 * - MedianFinder() initializes the MedianFinder object.
 * - void addNum(int num) adds the integer num from the data stream to the data structure.
 * - double findMedian() returns the median of all elements so far.
 *
 * Example 1:
 * Input: ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 *        [[], [1], [2], [], [3], []]
 * Output: [null, null, null, 1.5, null, 2.0]
 *
 * Constraints:
 * - -10^5 <= num <= 10^5
 * - There will be at least one element in the data structure before calling findMedian.
 * - At most 5 * 10^4 calls will be made to addNum and findMedian.
 *
 * Follow up:
 * - If all integer numbers from the stream are in the range [0, 100], how would you optimize?
 * - If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize?
 *
 * Companies: Amazon, Facebook, Google, Microsoft, Apple, Netflix, Twitter, Uber
 */

/**
 * Solution 1: Two Heaps (Max Heap + Min Heap)
 * Use max-heap for smaller half and min-heap for larger half.
 * Maintain balance: maxHeap.size == minHeap.size or maxHeap.size == minHeap.size + 1
 *
 * Time Complexity: O(log n) for addNum, O(1) for findMedian
 * Space Complexity: O(n)
 */
class MedianFinder() {
    private val maxHeap = PriorityQueue<Int>(Collections.reverseOrder())
    private val minHeap = PriorityQueue<Int>()

    fun addNum(num: Int) {
        maxHeap.offer(num)
        minHeap.offer(maxHeap.poll())

        if (minHeap.size > maxHeap.size) {
            maxHeap.offer(minHeap.poll())
        }
    }

    fun findMedian(): Double {
        return if (maxHeap.size > minHeap.size) {
            maxHeap.peek().toDouble()
        } else {
            (maxHeap.peek() + minHeap.peek()) / 2.0
        }
    }
}

/**
 * Solution 2: Two Heaps with Different Balancing Strategy
 * Always ensure |maxHeap.size - minHeap.size| <= 1
 *
 * Time Complexity: O(log n) for addNum, O(1) for findMedian
 * Space Complexity: O(n)
 */
class MedianFinderV2() {
    private val smallerHalf = PriorityQueue<Int>(Collections.reverseOrder())
    private val largerHalf = PriorityQueue<Int>()

    fun addNum(num: Int) {
        if (smallerHalf.isEmpty() || num <= smallerHalf.peek()) {
            smallerHalf.offer(num)
        } else {
            largerHalf.offer(num)
        }

        if (smallerHalf.size > largerHalf.size + 1) {
            largerHalf.offer(smallerHalf.poll())
        } else if (largerHalf.size > smallerHalf.size) {
            smallerHalf.offer(largerHalf.poll())
        }
    }

    fun findMedian(): Double {
        return if (smallerHalf.size > largerHalf.size) {
            smallerHalf.peek().toDouble()
        } else {
            (smallerHalf.peek() + largerHalf.peek()) / 2.0
        }
    }
}

/**
 * Solution 3: Balanced BST using TreeMap
 * Use TreeMap for O(log n) insertion and efficient median lookup.
 *
 * Time Complexity: O(log n) for addNum, O(log n) for findMedian
 * Space Complexity: O(n)
 */
class MedianFinderBST() {
    private val tree = TreeMap<Int, Int>()
    private var count = 0

    fun addNum(num: Int) {
        tree[num] = tree.getOrDefault(num, 0) + 1
        count++
    }

    fun findMedian(): Double {
        val mid1 = (count + 1) / 2
        val mid2 = (count + 2) / 2

        var cnt = 0
        var first = 0
        var second = 0

        for ((key, freq) in tree) {
            cnt += freq
            if (first == 0 && cnt >= mid1) {
                first = key
            }
            if (cnt >= mid2) {
                second = key
                break
            }
        }

        return (first + second) / 2.0
    }
}

/**
 * Solution 4: Bucket Sort for Constrained Range [0, 100]
 * Optimized for the follow-up question with constrained integer range.
 *
 * Time Complexity: O(1) for addNum, O(100) = O(1) for findMedian
 * Space Complexity: O(101)
 */
class MedianFinderBucket() {
    private val buckets = IntArray(101)
    private var count = 0

    fun addNum(num: Int) {
        val clampedNum = num.coerceIn(0, 100)
        buckets[clampedNum]++
        count++
    }

    fun findMedian(): Double {
        val mid1 = (count + 1) / 2
        val mid2 = (count + 2) / 2

        var cnt = 0
        var first = -1
        var second = -1

        for (i in buckets.indices) {
            cnt += buckets[i]
            if (first == -1 && cnt >= mid1) {
                first = i
            }
            if (cnt >= mid2) {
                second = i
                break
            }
        }

        return (first + second) / 2.0
    }
}
