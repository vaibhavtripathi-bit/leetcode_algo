package com.leetcode.solutions.binarysearch.problems.hard

/**
 * LeetCode 4: Median of Two Sorted Arrays
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Find the median of two sorted arrays in O(log(m+n)).
 *
 * Example: nums1 = [1,3], nums2 = [2] → 2.0
 * Example: nums1 = [1,2], nums2 = [3,4] → 2.5
 */

/**
 * Solution 1: Binary Search on Partition - Time: O(log(min(m,n))), Space: O(1)
 *
 * Key idea: Find a partition in both arrays such that:
 * - left half of nums1 + left half of nums2 = total left half
 * - max(left halves) <= min(right halves)
 */
class FindMedian1 {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val (smaller, larger) = ensureSmallerFirst(nums1, nums2)
        return findMedian(smaller, larger)
    }

    private fun ensureSmallerFirst(a: IntArray, b: IntArray) =
        if (a.size <= b.size) a to b else b to a

    private fun findMedian(small: IntArray, large: IntArray): Double {
        val totalLeft = (small.size + large.size + 1) / 2
        var left = 0
        var right = small.size

        while (left <= right) {
            val partSmall = left + (right - left) / 2
            val partLarge = totalLeft - partSmall

            val maxLeftSmall  = if (partSmall == 0)         Int.MIN_VALUE else small[partSmall - 1]
            val minRightSmall = if (partSmall == small.size) Int.MAX_VALUE else small[partSmall]
            val maxLeftLarge  = if (partLarge == 0)         Int.MIN_VALUE else large[partLarge - 1]
            val minRightLarge = if (partLarge == large.size) Int.MAX_VALUE else large[partLarge]

            when {
                maxLeftSmall <= minRightLarge && maxLeftLarge <= minRightSmall ->
                    return computeMedian(small.size + large.size,
                                        maxLeftSmall, maxLeftLarge,
                                        minRightSmall, minRightLarge)
                maxLeftSmall > minRightLarge -> right = partSmall - 1
                else -> left = partSmall + 1
            }
        }

        return 0.0
    }

    private fun computeMedian(
        totalSize: Int,
        maxLeftSmall: Int, maxLeftLarge: Int,
        minRightSmall: Int, minRightLarge: Int
    ): Double {
        val maxLeft = maxOf(maxLeftSmall, maxLeftLarge)
        val minRight = minOf(minRightSmall, minRightLarge)

        return if (totalSize % 2 == 1) maxLeft.toDouble()
               else (maxLeft + minRight) / 2.0
    }
}

/** Solution 2: Merge then Find Median - Time: O(m+n), Space: O(m+n) */
class FindMedian2 {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val merged = mergeSorted(nums1, nums2)
        return getMedian(merged)
    }

    private fun mergeSorted(a: IntArray, b: IntArray): IntArray {
        val result = IntArray(a.size + b.size)
        var i = 0; var j = 0; var k = 0

        while (i < a.size && j < b.size) {
            result[k++] = if (a[i] <= b[j]) a[i++] else b[j++]
        }
        while (i < a.size) result[k++] = a[i++]
        while (j < b.size) result[k++] = b[j++]

        return result
    }

    private fun getMedian(arr: IntArray): Double {
        val n = arr.size
        return if (n % 2 == 1) arr[n / 2].toDouble()
               else (arr[n / 2 - 1] + arr[n / 2]) / 2.0
    }
}

/** Solution 3: Virtual Merge without Extra Space - Time: O(m+n), Space: O(1) */
class FindMedian3 {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val total = nums1.size + nums2.size
        val mid = total / 2

        return if (total % 2 == 1) kthElement(nums1, nums2, mid + 1).toDouble()
               else (kthElement(nums1, nums2, mid) + kthElement(nums1, nums2, mid + 1)) / 2.0
    }

    private fun kthElement(a: IntArray, b: IntArray, k: Int): Int {
        var i = 0; var j = 0; var remaining = k

        while (remaining > 1) {
            val half = remaining / 2
            val ai = (i + half - 1).coerceAtMost(a.size - 1)
            val bj = (j + half - 1).coerceAtMost(b.size - 1)

            if (i <= a.lastIndex && (j > b.lastIndex || a[ai] <= b[bj])) i = ai + 1
            else j = bj + 1

            remaining -= half
        }

        val nextA = if (i < a.size) a[i] else Int.MAX_VALUE
        val nextB = if (j < b.size) b[j] else Int.MAX_VALUE

        return minOf(nextA, nextB)
    }
}
