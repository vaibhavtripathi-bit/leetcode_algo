package com.leetcode.solutions.slidingwindow.problems.hard

import java.util.*

/**
 * 239. Sliding Window Maximum
 * https://leetcode.com/problems/sliding-window-maximum/
 *
 * You are given an array of integers nums, there is a sliding window of size k which is moving
 * from the very left of the array to the very right. You can only see the k numbers in the window.
 * Each time the sliding window moves right by one position.
 * Return the max sliding window.
 *
 * Example 1:
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - 1 <= k <= nums.length
 */
class SlidingWindowMaximum {

    /**
     * Solution 1: Monotonic Deque (Optimal)
     * Maintain a deque of indices in decreasing order of values.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     */
    fun maxSlidingWindowDeque(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()
        if (k == 1) return nums.clone()

        val n = nums.size
        val result = IntArray(n - k + 1)
        val deque = ArrayDeque<Int>()

        for (i in nums.indices) {
            while (deque.isNotEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst()
            }

            while (deque.isNotEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast()
            }

            deque.offerLast(i)

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()]
            }
        }

        return result
    }

    /**
     * Solution 2: Max Heap with Lazy Deletion
     * Use a max heap and remove invalid elements when they become the maximum.
     *
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    fun maxSlidingWindowHeap(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()

        val n = nums.size
        val result = IntArray(n - k + 1)
        val maxHeap = PriorityQueue<Pair<Int, Int>>(compareBy { -it.first })

        for (i in 0 until k) {
            maxHeap.offer(Pair(nums[i], i))
        }
        result[0] = maxHeap.peek().first

        for (i in k until n) {
            maxHeap.offer(Pair(nums[i], i))

            while (maxHeap.peek().second <= i - k) {
                maxHeap.poll()
            }

            result[i - k + 1] = maxHeap.peek().first
        }

        return result
    }

    /**
     * Solution 3: Dynamic Programming with Block Decomposition
     * Split array into blocks, compute left max and right max for each position.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    fun maxSlidingWindowDP(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()
        if (k == 1) return nums.clone()

        val n = nums.size
        val leftMax = IntArray(n)
        val rightMax = IntArray(n)

        for (i in nums.indices) {
            leftMax[i] = if (i % k == 0) nums[i] else maxOf(leftMax[i - 1], nums[i])
        }

        for (i in n - 1 downTo 0) {
            rightMax[i] = if (i == n - 1 || (i + 1) % k == 0) nums[i] else maxOf(rightMax[i + 1], nums[i])
        }

        val result = IntArray(n - k + 1)
        for (i in 0..n - k) {
            result[i] = maxOf(rightMax[i], leftMax[i + k - 1])
        }

        return result
    }

    /**
     * Solution 4: TreeMap (Balanced BST)
     * Maintain a balanced BST for the current window.
     *
     * Time Complexity: O(n log k)
     * Space Complexity: O(k)
     */
    fun maxSlidingWindowTreeMap(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()

        val n = nums.size
        val result = IntArray(n - k + 1)
        val treeMap = TreeMap<Int, Int>()

        for (i in nums.indices) {
            treeMap[nums[i]] = treeMap.getOrDefault(nums[i], 0) + 1

            if (i >= k) {
                val removeVal = nums[i - k]
                val count = treeMap[removeVal]!!
                if (count == 1) {
                    treeMap.remove(removeVal)
                } else {
                    treeMap[removeVal] = count - 1
                }
            }

            if (i >= k - 1) {
                result[i - k + 1] = treeMap.lastKey()
            }
        }

        return result
    }
}
