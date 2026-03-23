package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 39: Combination Sum
 * https://leetcode.com/problems/combination-sum/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Find all unique combinations that sum to target. Same number can be used multiple times.
 *
 * Example: candidates = [2,3,6,7], target = 7 → [[2,2,3],[7]]
 */

/** Solution 1: Backtracking - Time: O(2^(t/m)), Space: O(t/m) */
class CombinationSum1 {
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        candidates.sort()
        backtrack(candidates, target, 0, mutableListOf(), result)
        return result
    }

    private fun backtrack(
        candidates: IntArray, remaining: Int, start: Int,
        current: MutableList<Int>, result: MutableList<List<Int>>
    ) {
        if (remaining == 0) {
            result.add(current.toList())
            return
        }

        for (i in start until candidates.size) {
            if (candidates[i] > remaining) break

            current.add(candidates[i])
            backtrack(candidates, remaining - candidates[i], i, current, result)
            current.removeLast()
        }
    }
}

/** Solution 2: DP approach - Time: O(n * target), Space: O(n * target) */
class CombinationSum2 {
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val dp = Array(target + 1) { mutableListOf<List<Int>>() }
        dp[0].add(emptyList())

        for (candidate in candidates) {
            for (t in candidate..target) {
                dp[t - candidate].forEach { combo ->
                    dp[t].add(combo + candidate)
                }
            }
        }

        return dp[target]
    }
}
