package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue

/**
 * 973. K Closest Points to Origin
 * https://leetcode.com/problems/k-closest-points-to-origin/
 *
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane
 * and an integer k, return the k closest points to the origin (0, 0).
 *
 * The distance between two points on the X-Y plane is the Euclidean distance
 * (i.e., √(x1 - x2)^2 + (y1 - y2)^2).
 *
 * You may return the answer in any order. The answer is guaranteed to be unique
 * (except for the order that it is in).
 *
 * Example 1:
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation: The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 *
 * Example 2:
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * Output: [[3,3],[-2,4]]
 *
 * Constraints:
 * - 1 <= k <= points.length <= 10^4
 * - -10^4 <= xi, yi <= 10^4
 *
 * Companies: Facebook, Amazon, Google, Microsoft, Apple, Uber, LinkedIn
 */

/**
 * Solution 1: Max Heap of Size K
 * Maintain a max-heap of size k based on distance.
 * After processing all points, heap contains k closest points.
 *
 * Time Complexity: O(n log k)
 * Space Complexity: O(k)
 */
fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
    fun distanceSquared(point: IntArray): Int = point[0] * point[0] + point[1] * point[1]

    val maxHeap = PriorityQueue<IntArray>(compareByDescending { distanceSquared(it) })

    for (point in points) {
        maxHeap.offer(point)
        if (maxHeap.size > k) {
            maxHeap.poll()
        }
    }

    return maxHeap.toTypedArray()
}

/**
 * Solution 2: Min Heap (Extract K Closest)
 * Add all points to min-heap, then extract k smallest.
 *
 * Time Complexity: O(n log n + k log n)
 * Space Complexity: O(n)
 */
fun kClosestMinHeap(points: Array<IntArray>, k: Int): Array<IntArray> {
    fun distanceSquared(point: IntArray): Int = point[0] * point[0] + point[1] * point[1]

    val minHeap = PriorityQueue<IntArray>(compareBy { distanceSquared(it) })
    points.forEach { minHeap.offer(it) }

    return Array(k) { minHeap.poll() }
}

/**
 * Solution 3: QuickSelect
 * Use partition-based selection for O(n) average time.
 *
 * Time Complexity: O(n) average, O(n^2) worst case
 * Space Complexity: O(1)
 */
fun kClosestQuickSelect(points: Array<IntArray>, k: Int): Array<IntArray> {
    fun distanceSquared(point: IntArray): Int = point[0] * point[0] + point[1] * point[1]

    fun partition(left: Int, right: Int): Int {
        val pivotDist = distanceSquared(points[right])
        var storeIdx = left

        for (i in left until right) {
            if (distanceSquared(points[i]) < pivotDist) {
                points[storeIdx] = points[i].also { points[i] = points[storeIdx] }
                storeIdx++
            }
        }
        points[storeIdx] = points[right].also { points[right] = points[storeIdx] }
        return storeIdx
    }

    var left = 0
    var right = points.size - 1

    while (left < right) {
        val pivotIdx = partition(left, right)
        when {
            pivotIdx == k -> break
            pivotIdx < k -> left = pivotIdx + 1
            else -> right = pivotIdx - 1
        }
    }

    return points.sliceArray(0 until k)
}

/**
 * Solution 4: Sorting Approach
 * Simple sorting by distance and taking first k elements.
 *
 * Time Complexity: O(n log n)
 * Space Complexity: O(1) to O(n) depending on sort implementation
 */
fun kClosestSorting(points: Array<IntArray>, k: Int): Array<IntArray> {
    points.sortBy { it[0] * it[0] + it[1] * it[1] }
    return points.sliceArray(0 until k)
}
