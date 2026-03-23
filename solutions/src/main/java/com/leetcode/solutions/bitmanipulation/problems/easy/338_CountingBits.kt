package com.leetcode.solutions.bitmanipulation.problems.easy

/**
 * LeetCode 338: Counting Bits
 * https://leetcode.com/problems/counting-bits/
 *
 * Difficulty: Easy
 * Companies: Amazon, Microsoft, Google
 *
 * Return array ans where ans[i] = number of 1s in binary representation of i.
 *
 * Example: n = 5 → [0,1,1,2,1,2]
 */

/** Solution 1: DP with bit shift - Time: O(n), Space: O(1) extra */
class CountBits1 {
    fun countBits(n: Int): IntArray {
        val dp = IntArray(n + 1)
        for (i in 1..n) {
            dp[i] = dp[i shr 1] + (i and 1)
            // i >> 1 removes last bit; (i & 1) is the last bit
        }
        return dp
    }
}

/** Solution 2: DP using lowest set bit removal - Time: O(n), Space: O(1) extra */
class CountBits2 {
    fun countBits(n: Int): IntArray {
        val dp = IntArray(n + 1)
        for (i in 1..n) {
            dp[i] = dp[i and (i - 1)] + 1
            // i & (i-1) removes the lowest set bit
        }
        return dp
    }
}

/** Solution 3: Brute force with bit count - Time: O(n log n) */
class CountBits3 {
    fun countBits(n: Int): IntArray {
        return IntArray(n + 1) { Integer.bitCount(it) }
    }
}
