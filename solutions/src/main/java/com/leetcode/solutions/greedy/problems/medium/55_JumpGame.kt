package com.leetcode.solutions.greedy.problems.medium

/**
 * LeetCode 55: Jump Game
 * https://leetcode.com/problems/jump-game/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Can you reach the last index? Each element = max jump from that position.
 *
 * Example: nums = [2,3,1,1,4] → true
 * Example: nums = [3,2,1,0,4] → false
 */

/** Solution 1: Greedy (Track Max Reach) - Time: O(n), Space: O(1) */
class JumpGame1 {
    fun canJump(nums: IntArray): Boolean {
        var maxReach = 0

        for (i in nums.indices) {
            if (i > maxReach) return false
            maxReach = maxOf(maxReach, i + nums[i])
        }

        return true
    }
}

/** Solution 2: Greedy Backwards - Time: O(n), Space: O(1) */
class JumpGame2 {
    fun canJump(nums: IntArray): Boolean {
        var goal = nums.lastIndex

        for (i in nums.lastIndex - 1 downTo 0) {
            if (i + nums[i] >= goal) goal = i
        }

        return goal == 0
    }
}

/** Solution 3: DP - Time: O(n²), Space: O(n) */
class JumpGame3 {
    fun canJump(nums: IntArray): Boolean {
        val canReach = BooleanArray(nums.size)
        canReach[0] = true

        for (i in 1 until nums.size) {
            canReach[i] = (0 until i).any { j -> canReach[j] && j + nums[j] >= i }
        }

        return canReach.last()
    }
}
