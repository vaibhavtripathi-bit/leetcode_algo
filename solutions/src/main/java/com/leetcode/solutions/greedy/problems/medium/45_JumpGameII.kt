package com.leetcode.solutions.greedy.problems.medium

/**
 * LeetCode 45: Jump Game II
 * https://leetcode.com/problems/jump-game-ii/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Minimum jumps to reach the last index. Guaranteed reachable.
 *
 * Example: nums = [2,3,1,1,4] → 2 (0→1→4 or 0→1→last)
 */

/** Solution 1: Greedy (BFS-like levels) - Time: O(n), Space: O(1) */
class JumpGameII1 {
    fun jump(nums: IntArray): Int {
        var jumps = 0
        var currentEnd = 0
        var farthest = 0

        for (i in 0 until nums.lastIndex) {
            farthest = maxOf(farthest, i + nums[i])

            if (i == currentEnd) {
                jumps++
                currentEnd = farthest
            }
        }

        return jumps
    }
}

/** Solution 2: Greedy from right - Time: O(n²), Space: O(1) */
class JumpGameII2 {
    fun jump(nums: IntArray): Int {
        var position = nums.lastIndex
        var jumps = 0

        while (position > 0) {
            position = findFarthestJumpTo(nums, position)
            jumps++
        }

        return jumps
    }

    private fun findFarthestJumpTo(nums: IntArray, target: Int): Int {
        for (i in 0 until target) {
            if (i + nums[i] >= target) return i
        }
        return -1
    }
}
