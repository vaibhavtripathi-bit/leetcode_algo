package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue

/**
 * LeetCode 973: K Closest Points to Origin
 * https://leetcode.com/problems/k-closest-points-to-origin/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Facebook
 *
 * Return the k closest points to origin (0,0). Can return in any order.
 * Distance = sqrt(x² + y²), but compare x² + y² to avoid sqrt.
 *
 * Example: points = [[1,3],[-2,2]], k = 1 → [[-2,2]]
 */

/** Solution 1: Max-Heap of Size K - Time: O(n log k), Space: O(k) */
class KClosestPoints1 {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        val maxHeap = PriorityQueue<IntArray> { a, b ->
            squaredDist(b) - squaredDist(a)
        }

        for (point in points) {
            maxHeap.offer(point)
            if (maxHeap.size > k) maxHeap.poll()
        }

        return maxHeap.toTypedArray()
    }

    private fun squaredDist(point: IntArray) = point[0] * point[0] + point[1] * point[1]
}

/** Solution 2: Sort by Distance - Time: O(n log n), Space: O(1) */
class KClosestPoints2 {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        return points.sortedBy { squaredDist(it) }.take(k).toTypedArray()
    }

    private fun squaredDist(point: IntArray) = point[0] * point[0] + point[1] * point[1]
}

/** Solution 3: QuickSelect - Time: O(n) avg, Space: O(1) */
class KClosestPoints3 {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        quickSelect(points, 0, points.lastIndex, k)
        return points.take(k).toTypedArray()
    }

    private fun quickSelect(points: Array<IntArray>, left: Int, right: Int, k: Int) {
        if (left >= right) return

        val pivotIdx = partition(points, left, right)

        when {
            pivotIdx == k - 1 -> return
            pivotIdx < k - 1  -> quickSelect(points, pivotIdx + 1, right, k)
            else               -> quickSelect(points, left, pivotIdx - 1, k)
        }
    }

    private fun partition(points: Array<IntArray>, left: Int, right: Int): Int {
        val pivotDist = squaredDist(points[right])
        var i = left

        for (j in left until right) {
            if (squaredDist(points[j]) <= pivotDist) {
                swap(points, i++, j)
            }
        }

        swap(points, i, right)
        return i
    }

    private fun swap(arr: Array<IntArray>, i: Int, j: Int) {
        val temp = arr[i]; arr[i] = arr[j]; arr[j] = temp
    }

    private fun squaredDist(p: IntArray) = p[0] * p[0] + p[1] * p[1]
}
