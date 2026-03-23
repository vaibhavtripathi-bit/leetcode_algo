package com.leetcode.solutions.arrays.problems.medium

/**
 * LeetCode 128: Longest Consecutive Sequence
 * https://leetcode.com/problems/longest-consecutive-sequence/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Find the length of the longest consecutive elements sequence in O(n).
 *
 * Example: nums = [100,4,200,1,3,2] → 4 ([1,2,3,4])
 */

/** Solution 1: HashSet - Time: O(n), Space: O(n) */
class LongestConsecutive1 {
    fun longestConsecutive(nums: IntArray): Int {
        val numSet = nums.toHashSet()
        var longest = 0

        for (num in numSet) {
            if (isSequenceStart(numSet, num)) {
                val length = countSequenceLength(numSet, num)
                longest = maxOf(longest, length)
            }
        }

        return longest
    }

    private fun isSequenceStart(set: Set<Int>, num: Int) = (num - 1) !in set

    private fun countSequenceLength(set: Set<Int>, start: Int): Int {
        var length = 0
        var current = start
        while (current in set) { length++; current++ }
        return length
    }
}

/** Solution 2: Sort - Time: O(n log n), Space: O(1) */
class LongestConsecutive2 {
    fun longestConsecutive(nums: IntArray): Int {
        if (nums.isEmpty()) return 0

        nums.sort()
        var longest = 1
        var current = 1

        for (i in 1 until nums.size) {
            if (nums[i] == nums[i - 1]) continue
            if (nums[i] == nums[i - 1] + 1) {
                current++
                longest = maxOf(longest, current)
            } else {
                current = 1
            }
        }

        return longest
    }
}

/** Solution 3: Union-Find - Time: O(n α(n)), Space: O(n) */
class LongestConsecutive3 {
    fun longestConsecutive(nums: IntArray): Int {
        if (nums.isEmpty()) return 0

        val parent = HashMap<Int, Int>()
        val size = HashMap<Int, Int>()
        nums.forEach { parent[it] = it; size[it] = 1 }

        fun find(x: Int): Int {
            if (parent[x] != x) parent[x] = find(parent[x]!!)
            return parent[x]!!
        }

        for (num in nums) {
            if (num + 1 in parent) {
                val p1 = find(num); val p2 = find(num + 1)
                if (p1 != p2) {
                    parent[p2] = p1
                    size[p1] = size[p1]!! + size[p2]!!
                }
            }
        }

        return size.values.max()!!
    }
}
