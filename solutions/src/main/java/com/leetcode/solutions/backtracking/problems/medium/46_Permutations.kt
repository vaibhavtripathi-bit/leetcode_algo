package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 46: Permutations
 * https://leetcode.com/problems/permutations/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Return all possible permutations of a distinct integer array.
 *
 * Example: nums = [1,2,3] → [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 */

/** Solution 1: Backtracking with Visited Array - Time: O(n! * n), Space: O(n) */
class Permutations1 {
    fun permute(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val used = BooleanArray(nums.size)
        backtrack(nums, used, mutableListOf(), result)
        return result
    }

    private fun backtrack(
        nums: IntArray, used: BooleanArray,
        current: MutableList<Int>, result: MutableList<List<Int>>
    ) {
        if (current.size == nums.size) {
            result.add(current.toList())
            return
        }

        for (i in nums.indices) {
            if (used[i]) continue

            used[i] = true
            current.add(nums[i])
            backtrack(nums, used, current, result)
            current.removeLast()
            used[i] = false
        }
    }
}

/** Solution 2: Swap-based Backtracking - Time: O(n! * n), Space: O(n) */
class Permutations2 {
    fun permute(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        backtrack(nums.toMutableList(), 0, result)
        return result
    }

    private fun backtrack(nums: MutableList<Int>, start: Int, result: MutableList<List<Int>>) {
        if (start == nums.size) {
            result.add(nums.toList())
            return
        }

        for (i in start until nums.size) {
            nums.swap(start, i)
            backtrack(nums, start + 1, result)
            nums.swap(start, i)
        }
    }

    private fun MutableList<Int>.swap(i: Int, j: Int) {
        val temp = this[i]; this[i] = this[j]; this[j] = temp
    }
}

/** Solution 3: Using removeAt - Time: O(n! * n), Space: O(n) */
class Permutations3 {
    fun permute(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        generate(nums.toMutableList(), mutableListOf(), result)
        return result
    }

    private fun generate(
        remaining: MutableList<Int>,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        if (remaining.isEmpty()) {
            result.add(current.toList())
            return
        }

        for (i in remaining.indices) {
            val choice = remaining.removeAt(i)
            current.add(choice)
            generate(remaining, current, result)
            current.removeLast()
            remaining.add(i, choice)
        }
    }
}
